package com.example.yelp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yelp.Models.DetailModel.Detail
import com.example.yelp.Models.ReviewsModel.Reviews
import com.google.gson.Gson


class ReviewsFragment : Fragment() {

    private var reviewsData: Reviews? = null
    var TAG = "Reviews Fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            reviewsData = Gson().fromJson(it.getString("reviewsData"), Reviews::class.java)
        }

        Log.d(TAG, reviewsData.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }


}