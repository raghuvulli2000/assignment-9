package com.example.yelp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.resources.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yelp.databinding.BusinessTableItemViewBinding
import com.example.yelp.databinding.ImgViewPagerBinding

class ImageSliderAdapter(
    val images: List<String>
) : RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>() {
    inner class ImageSliderViewHolder(val binding: ImgViewPagerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {

        val binding =   ImgViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageSliderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        with(holder) {
            with(images[position]) {
                com.bumptech.glide.Glide.with(binding.root).load(this).into(binding.ivImg)
            }
        }

    }


}