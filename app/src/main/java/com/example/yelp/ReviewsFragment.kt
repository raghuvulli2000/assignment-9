package com.example.yelp

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.yelp.Adapters.ReviewsAdapter
import com.example.yelp.Models.DetailModel.Detail
import com.example.yelp.Models.ReviewsModel.Reviews
import com.example.yelp.databinding.FragmentInfoBinding
import com.example.yelp.databinding.FragmentReviewsBinding
import com.google.gson.Gson


class ReviewsFragment : Fragment() {
    private var __binding: FragmentReviewsBinding? = null
    private val binding get() = __binding
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
        __binding = FragmentReviewsBinding.inflate(layoutInflater, container, false)
        binding?.rvReviews?.adapter = ReviewsAdapter(reviewsData!!.reviews)
        val decor = object  : DividerItemDecoration(binding?.rvReviews?.context, VERTICAL){
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildAdapterPosition(view)

                //  Log.d("Reviews", (state.itemCount - 1).toString())
                if(position == state.itemCount - 1){
                    Log.d("Reviews", position.toString())
                    outRect.setEmpty();
                }

            }
        }
        context?.let {
            ContextCompat.getDrawable(it, R.drawable.reviews_divider)
                ?.let {
                    decor.setDrawable(it)
                }
        }
        binding?.rvReviews?.addItemDecoration(decor)
        return binding?.root
    }


}