package com.example.yelp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yelp.Models.DetailModel.Detail
import com.google.gson.Gson


class InfoFragment : Fragment() {

    private var detailData: Detail? = null
    var TAG = "InfoFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            detailData =  Gson().fromJson(it.getString("detailData"), Detail::class.java)
        }
        Log.d(TAG, detailData.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }


}