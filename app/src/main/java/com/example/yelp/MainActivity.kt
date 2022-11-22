package com.example.yelp

import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.yelp.Adapters.TableAdapter
import com.example.yelp.Models.OptionsModel.OptionsResponse
import com.example.yelp.Models.SearchModel.Business
import com.example.yelp.api.RetrofitInstance
import com.example.yelp.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val spinnerText = listOf("Default", "Arts and Entertainment","Health and Medical","Hotel and Travel","Food","Professional Services")
    val spinnerValues = listOf<String>("All","arts","health","hotelstravel","food","professional")
    var autoOptions = listOf<String>("All","arts","health","hotelstravel","food","professional")
    var recycler_data: MutableList<Business> = mutableListOf()
    private lateinit var adapter: TableAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.adapter = TableAdapter(this@MainActivity,recycler_data)
        binding.rvList.adapter = this.adapter
        val adapter = ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, spinnerText)
        binding.spCategory.adapter = adapter


        var job: Job? = null
        binding.etKeyword.setOnFocusChangeListener { view, hasFocus ->
            if(!hasFocus){
                job?.cancel()
            }
        }

        binding.btnClear.setOnClickListener {
            binding.etKeyword.text.clear()
            binding.etDistance.text.clear()
            binding.etLocation.text.clear()
            binding.etLocation.visibility = View.VISIBLE
            binding.cbLocation.isChecked = false
            binding.spCategory.setSelection(0)
            binding.tvNoResults.visibility = View.INVISIBLE
            recycler_data.clear()

            this.adapter.notifyDataSetChanged()
        }

        binding.etKeyword.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(400L)
                it?.let {
                    if(it.toString().isNotEmpty()){
                            Log.d("Retrofit Call", it.toString())
                            val result = RetrofitInstance.api.getOptions(it.toString())
                            if(result.isSuccessful){
                                Log.d("Retrofit Call", result.body()!!.toString())
                               autoOptions = getOptions(result.body()!!)
//                                autoComplete.notifyDataSetChanged()
                                val autoComplete: ArrayAdapter<String>  = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, autoOptions)
                                binding.etKeyword.setAdapter(autoComplete)
                                binding.etKeyword.showDropDown()
                            }
                    }
                }
            }
        }

        binding.etKeyword.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("Retrofit", "Auto Options selected")
                job?.cancel();
            }

        }

        binding.btnSubmit.setOnClickListener{
            binding.etLocation.clearFocus()
            binding.etDistance.clearFocus()
            binding.etKeyword.clearFocus()
            binding.btnSubmit.requestFocus()

            if(CheckFields()){
                    if(!binding.cbLocation.isChecked){
                        notChecked()
                    }
                else{
                    checkedBox()
                    }
            }
            else{
                Toast.makeText(this, "Some fields are required", Toast.LENGTH_SHORT).show()
            }


        }

        binding.spCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
              //  Toast.makeText(this@MainActivity, "You Seleced ${adapterView?.getItemAtPosition(position).toString()} having value ${spinnerValues[position]}", Toast.LENGTH_SHORT).show();
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.cbLocation.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                Log.d("Retrofit",binding.cbLocation.isChecked.toString())
                if(binding.cbLocation.isChecked){
                    binding.etLocation.setText("")
                    binding.etLocation.visibility = View.INVISIBLE
                }
                else{
                    binding.etLocation.visibility = View.VISIBLE
                }
            }

        })

    }

    private fun getOptions(body: OptionsResponse): List<String> {
            val options = mutableListOf<String>()
            options += body.categories.map { it.title }
            options += body.terms.map { it.text }
        return options
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.ic_calendar -> {
            //    Toast.makeText(this, "YouClicked Toolbar Reservation Icon", Toast.LENGTH_SHORT).show()
                var intent: Intent = Intent(this@MainActivity, ReservationsActivity::class.java)
                this@MainActivity.startActivity(intent)
            }
        }
        return true
    }
    private fun checkedBox(){
      //  Toast.makeText(this, "All Fields are Validated Correct", Toast.LENGTH_SHORT).show()
       // var recycler_data: List<Business>
        Log.d("Main Activity", "First Click Listener");
      //  Toast.makeText(this, "Button Clicked", Toast.LENGTH_LONG).show();
        val job = MainScope().launch {
            val locRes = RetrofitInstance.locaction.getCurrentLocation()
            val results = RetrofitInstance.api.getSearchResults(locRes.body()!!.loc.split(",")[0],
                locRes.body()!!.loc.split(",")[1],
                binding.etKeyword.text.toString(),
                (binding.etDistance.text.toString().toInt()*1609.09).toInt().toString(),
                spinnerValues[binding.spCategory.selectedItemPosition])

            Log.d("Retrofit", results.toString())
            binding.tvNoResults.visibility = View.INVISIBLE
            if(results.isSuccessful){

                Log.d("Retrofit Call", results.body()!!.businesses.size.toString())

                recycler_data.clear()
                recycler_data.addAll(results.body()!!.businesses as MutableList<Business>)
               // binding.rvList.adapter = TableAdapter(this@MainActivity,recycler_data)
                adapter.notifyDataSetChanged()
                Log.d("Main Activity", adapter.itemCount.toString())
                if(adapter.itemCount == 0){
                    binding?.tvNoResults?.visibility = View.VISIBLE
                }
            }
            else{
                Log.d("Retrofit Call", "Falied Check Logs")
            }
        }
    }
    private fun notChecked(){
       // Toast.makeText(this, "All Fields are Validated Correct", Toast.LENGTH_SHORT).show()
      //  var recycler_data: List<Business>
        Log.d("Main Activity", "First Click Listener");
    //    Toast.makeText(this, "Button Clicked", Toast.LENGTH_LONG).show();
        var job: Job? = null

        job = MainScope().launch {
            val locRes = RetrofitInstance.api.getGeoLocation(binding.etLocation.text.toString())
            binding.tvNoResults.visibility = View.INVISIBLE
            if(locRes.body()?.status.equals("ZERO_RESULTS")){
                binding.tvNoResults.visibility = View.VISIBLE
                recycler_data.clear()
                adapter.notifyDataSetChanged()
                return@launch
            }

            val results = RetrofitInstance.api.getSearchResults(locRes.body()!!.results[0].geometry.location.lat.toString(),
                locRes.body()!!.results[0].geometry.location.lng.toString(),
                binding.etKeyword.text.toString(),
                (binding.etDistance.text.toString().toInt()*1609.09).toInt().toString(),
                spinnerValues[binding.spCategory.selectedItemPosition])
            Log.d("Retrofit", results.toString())

            if(results.isSuccessful){

                Log.d("Retrofit Call", results.body()!!.businesses.size.toString())

                recycler_data.clear()
                recycler_data.addAll(results.body()!!.businesses as MutableList<Business>)
              //  binding.rvList.adapter = TableAdapter(this@MainActivity,recycler_data)
               adapter.notifyDataSetChanged()
                if(adapter.itemCount == 0){
                    binding?.tvNoResults?.visibility = View.VISIBLE
                }

            }
            else{
                Log.d("Retrofit Call", "Falied Check Logs")
            }
        }
    }
    private fun CheckFields() : Boolean {
        if(binding.etKeyword.length() == 0){
            binding.etKeyword.error = "This field is required"
            return false
        }
        if(binding.etDistance.length() == 0){
            binding.etDistance.error = "This field is required"
            return false
        }
        if(binding.etLocation.isVisible &&  binding.etLocation.length() == 0){
            binding.etLocation.error = "This field is required"
            return false
        }

        return true
    }

}