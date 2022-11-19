package com.example.yelp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.contentValuesOf
import com.bumptech.glide.Glide
import com.example.yelp.Models.DetailModel.Detail
import com.example.yelp.databinding.FragmentInfoBinding
import com.example.yelp.databinding.FragmentMapBinding
import com.google.gson.Gson


class InfoFragment : Fragment() {

    private var __binding: FragmentInfoBinding? = null
    private val binding get() = __binding
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
        __binding = FragmentInfoBinding.inflate(layoutInflater, container, false)
        for(i in detailData!!.photos.indices) {
            var imageView: ImageView = ImageView(context)
            Glide.with(binding!!.root).load(detailData!!.photos[i]).into(imageView)
       //     imageView?.layoutParams?.width = LinearLayout.LayoutParams.WRAP_CONTENT
           binding?.linearLayout?.addView(imageView, i)

        }
        return binding?.root
    }


}