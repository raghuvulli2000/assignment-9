package com.example.yelp.Adapters

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.yelp.InfoFragment
import com.example.yelp.MapFragment
import com.example.yelp.Models.DetailModel.Detail
import com.example.yelp.Models.ReviewsModel.Reviews
import com.example.yelp.ReviewsFragment
import com.google.gson.Gson

class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private lateinit var detailData: Detail
    private lateinit var reviewData: Reviews

    fun setData(dData: Detail, rData: Reviews){
        this.detailData = dData
        this.reviewData = rData

        Log.d("ViewPage", detailData.toString())
        Log.d("ViewPage", reviewData.toString())

    }
    override fun getItemCount(): Int {
      return 3
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> {

               val infoFragment : InfoFragment = InfoFragment()
               val bundle: Bundle = Bundle()
               Log.d("ViewPage", Gson().toJson(detailData))
               bundle.putString("detailData", Gson().toJson(detailData))
               infoFragment.arguments = bundle
               return infoFragment
           }
           1 -> {
              // MapFragment()
               val mapFragment : MapFragment = MapFragment()
               val bundle: Bundle = Bundle()
               Log.d("ViewPage", Gson().toJson(detailData))
               bundle.putString("detailData", Gson().toJson(detailData))
               mapFragment.arguments = bundle
               return mapFragment
           }
           2 -> {
              // ReviewsFragment()
               val reviewsFragment : ReviewsFragment = ReviewsFragment()
               val bundle: Bundle = Bundle()
               Log.d("ViewPage", Gson().toJson(reviewData))
               bundle.putString("reviewsData", Gson().toJson(reviewData))
               reviewsFragment.arguments = bundle
               return reviewsFragment
           }
           else -> { throw Resources.NotFoundException("Position Not Found")}
       }
    }
}