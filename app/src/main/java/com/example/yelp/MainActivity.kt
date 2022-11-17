package com.example.yelp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import com.example.yelp.Adapters.TableAdapter
import com.example.yelp.Models.SearchModel.Business
import com.example.yelp.api.RetrofitInstance
import com.example.yelp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val spinnerText = listOf("Default", "Arts and Entertainment","Health and Medical","Hotel and Travel","Food","Professional Services")
    val spinnerValues = listOf<String>("All","arts","health","hotelstravel","food","professional")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, spinnerText)
        binding.spCategory.adapter = adapter

        binding.btnSubmit.setOnClickListener{
            if(CheckFields()){
                Toast.makeText(this, "All Fields are Validated Correct", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Some fields are required", Toast.LENGTH_SHORT).show();
            }
            var recycler_data: List<Business>
            Log.d("Main Activity", "First Click Listener");
         Toast.makeText(this, "Button Clicked", Toast.LENGTH_LONG).show();
            GlobalScope.launch(Dispatchers.IO) {
                val results = RetrofitInstance.api.getSearchResults()
                if(results.isSuccessful){

                    Log.d("Retrofit Call", results.body()!!.businesses.size.toString())
                    withContext(Dispatchers.Main){
                        recycler_data = results.body()!!.businesses!!
                        binding.rvList.adapter = TableAdapter(this@MainActivity,recycler_data)
                    }
                }
                else{
                    Log.d("REtrofit Call", "Falied Check Logs")
                }
            }

        }

        binding.spCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@MainActivity, "You Seleced ${adapterView?.getItemAtPosition(position).toString()} having value ${spinnerValues[position]}", Toast.LENGTH_SHORT).show();
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.ic_calendar -> Toast.makeText(this, "YouClicked Toolbar Reservation Icon", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun CheckFields() : Boolean {
        if(binding.etKeyword!!.length() == 0){
            binding.etKeyword!!.error = "This field is required"
            return false
        }
        if(binding.etDistance!!.length() == 0){
            binding.etDistance!!.error = "This field is required"
            return false
        }
        if(binding.etLocation!!.length() == 0){
            binding.etLocation!!.error = "This field is required"
            return false
        }
        return true
    }

}