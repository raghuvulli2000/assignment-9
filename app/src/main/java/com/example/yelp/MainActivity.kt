package com.example.yelp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener{
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


    }
}