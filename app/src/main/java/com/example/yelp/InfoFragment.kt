package com.example.yelp

import android.media.Image
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.yelp.Adapters.ImageSliderAdapter
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
     binding!!.rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding!!.rv.adapter = ImageSliderAdapter(detailData!!.photos)
//        for(i in detailData!!.photos.indices) {
//            var scale: Float = context?.resources?.displayMetrics!!.density
//            var imageView: ImageView = ImageView(context)
//            imageView?.layoutParams?.width = (300 * scale + 0.5).toInt()
//            imageView?.layoutParams?.height = LinearLayout.LayoutParams.MATCH_PARENT
//            Glide.with(binding!!.root).load(detailData!!.photos[i]).into(imageView)
//
//         //   imageView.adjustViewBounds = true
//
//           binding?.linearLayout?.addView(imageView, i)
//
//        }
        return binding?.root
    }


}