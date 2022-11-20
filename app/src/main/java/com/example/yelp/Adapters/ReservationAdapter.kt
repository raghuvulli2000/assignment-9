package com.example.yelp.Adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yelp.Models.Reservation
import com.example.yelp.Models.ReservationModel
import com.example.yelp.databinding.ReservationItemViewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class ReservationAdapter(val context: Context, val data: MutableList<Reservation>) : RecyclerView.Adapter<ReservationAdapter.ReservationsViewHolder>(){

    inner class ReservationsViewHolder(val binding: ReservationItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationsViewHolder {
        val binding = ReservationItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun deleteItem(index: Int){
        data.removeAt(index)
        notifyDataSetChanged()
        val sharedpref: SharedPreferences = context.getSharedPreferences("MyReservations", 0)
        var editor: SharedPreferences.Editor = sharedpref.edit()
        editor.putString("reservations", Gson().toJson(ReservationModel(data)))
        editor.commit()

    }

    override fun onBindViewHolder(holder: ReservationsViewHolder, position: Int) {
     with(holder){
        with(data[position]){
            binding.tvNum.text = (position+1).toString()
            binding.tvEmail.text = email
            binding.tvDate.text = date
            binding.tvTime.text = time
            binding.tvRestaurantName.text = name
        }
     }
    }



}