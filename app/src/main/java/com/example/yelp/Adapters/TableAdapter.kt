package com.example.yelp.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yelp.DetailsActivity
import com.example.yelp.MainActivity
import com.example.yelp.Models.SearchModel.Business
import com.example.yelp.R
import com.example.yelp.api.RetrofitInstance
import com.example.yelp.databinding.ActivityMainBinding
import com.example.yelp.databinding.BusinessTableItemViewBinding
import com.google.gson.Gson
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class TableAdapter(private val context: Context,
                   private val dataset: MutableList<Business>): RecyclerView.Adapter<TableAdapter.TableViewHolder>() {

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
                binding.tvDistance.text = (((this.distance / 1609.9)*100).roundToInt()/100).toString()
                binding.tvRating.text = this.rating.toString()
                binding.tvTitle.text = this.name.toString()
                binding.root.setOnClickListener {
                    Toast.makeText(it.context, "Clicked ${this.name}", Toast.LENGTH_SHORT).show()
                    MainScope().launch {
                        var results = RetrofitInstance.api.getBusinessDetail(dataset[position].id)
                        var reviews = RetrofitInstance.reviewapi.getBusinessDetail(dataset[position].id + "/reviews")
                        if (results.isSuccessful && reviews.isSuccessful) {
                           var objasJson: String = Gson().toJson(results.body())
                            Log.d("Retrofit", objasJson)
                            var intent: Intent = Intent(context, DetailsActivity::class.java)
                            intent.putExtra("detailData", objasJson)
                            intent.putExtra("reviewsData", Gson().toJson(reviews.body()))
                            context.startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}


