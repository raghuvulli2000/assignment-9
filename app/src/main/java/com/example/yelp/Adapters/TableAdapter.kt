package com.example.yelp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yelp.Models.SearchModel.Business
import com.example.yelp.R
import com.example.yelp.databinding.ActivityMainBinding
import com.example.yelp.databinding.BusinessTableItemViewBinding

class TableAdapter(private val context: Context,
                   private val dataset: List<Business>): RecyclerView.Adapter<TableAdapter.TableViewHolder>() {

    class TableViewHolder(val binding: BusinessTableItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
//        val tableListAdapterLayout =  LayoutInflater.from(parent.context)
//            .inflate(R.layout.business_table_item_view, parent, false)
        val binding = BusinessTableItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TableViewHolder(binding)
    }



    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        with(holder){
            with(dataset[position]){
                Glide.with(binding.root).load(this.image_url).into(binding.ivImg)
                binding.tvSno.text = (position + 1).toString()
                binding.tvDistance.text = (this.distance / 1609.9).toString()
                binding.tvRating.text = this.rating.toString()
            }
        }
    }
}


