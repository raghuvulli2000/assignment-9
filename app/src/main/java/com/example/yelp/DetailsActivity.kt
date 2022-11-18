package com.example.yelp

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.example.yelp.Adapters.ViewPagerAdapter
import com.example.yelp.Models.DetailModel.Detail
import com.example.yelp.Models.ReviewsModel.Reviews
import com.example.yelp.api.RetrofitInstance
import com.example.yelp.databinding.ActivityDetailsBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {
    var TAG = "Details Activity"
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var detailData: Detail
    private lateinit var reviewsData: Reviews
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(intent.extras != null) {
         //   Toast.makeText(this, intent.getStringExtra("id"), Toast.LENGTH_SHORT).show()

                intent.getStringExtra("detailData")?.let {
                   // var results = RetrofitInstance.api.getBusinessDetail(it)
//                    if(results.isSuccessful){
//                        Log.d(TAG, results.body().toString())
//                    }
                    Log.d(TAG, it)
                    detailData = Gson().fromJson(it, Detail::class.java)
                    Log.d(TAG, detailData.toString())
                }

            intent.getStringExtra("reviewsData")?.let {
                // var results = RetrofitInstance.api.getBusinessDetail(it)
//                    if(results.isSuccessful){
//                        Log.d(TAG, results.body().toString())
//                    }
                Log.d(TAG, it)
                reviewsData = Gson().fromJson(it, Reviews::class.java)
                Log.d(TAG, reviewsData.toString())
            }


        }

        val actionbar = supportActionBar
        actionbar?.title = detailData.name
        actionbar?.setDisplayHomeAsUpEnabled(true)
        val adapter: ViewPagerAdapter  = ViewPagerAdapter(this)
        adapter.setData(detailData, reviewsData)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tbLayout, binding.viewPager){tab, index ->
            tab.text = when(index){
                0 -> {"BUSINESS DETAILS"}
                1 -> {"MAP LOCATION"}
                2 -> {"REVIEWS"}
                else -> {throw Resources.NotFoundException("Position Not Found")}
            }
        }.attach()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}