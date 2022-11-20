package com.example.yelp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yelp.Models.ReviewsModel.Review
import com.example.yelp.databinding.ReviewItemViewBinding

class ReviewsAdapter(val reviews: List<Review>): RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {

    inner class ReviewsViewHolder(val binding: ReviewItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
       val binding = ReviewItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
            return reviews.size
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
       with(holder){
           with(reviews[position]){
               binding.tvReviewName.text = this.user.name
               binding.tvReviewRating.text = "Rating: ${this.rating}/5"
               binding.tvReviewText.text = this.text
              binding.tvReviewData.text = this.time_created.split(" ")[0]

           }
       }
    }


}