package com.example.yelp

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.size
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.yelp.Adapters.ReservationAdapter
import com.example.yelp.Models.Reservation
import com.example.yelp.Models.ReservationModel
import com.example.yelp.SwipeGestures.SwipeGesture
import com.example.yelp.databinding.ActivityDetailsBinding
import com.example.yelp.databinding.ActivityReservationsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class ReservationsActivity : AppCompatActivity() {
    lateinit var  sharedPrefs: SharedPreferences
    private lateinit var binding: ActivityReservationsBinding
    lateinit var reservationsList: MutableList<Reservation>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

         sharedPrefs = this@ReservationsActivity.getSharedPreferences("MyReservations", 0)
        val reservations: ReservationModel = Gson().fromJson(sharedPrefs.getString("reservations", ""), ReservationModel::class.java)
       val adapter = ReservationAdapter(this@ReservationsActivity, reservations.reservations)
        reservationsList = reservations.reservations
        val swipeGesture = object : SwipeGesture(this@ReservationsActivity){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               when(direction){
                   ItemTouchHelper.LEFT -> {
                    //   reservationsList.removeAt(viewHolder.absoluteAdapterPosition)
                       adapter.deleteItem(viewHolder.absoluteAdapterPosition)
                       if(adapter.itemCount == 0)
                       {
                           binding.tvNoResult.visibility = View.VISIBLE
                       }
                       Snackbar.make(findViewById(android.R.id.content), "Removing Existing Reservation", Snackbar.LENGTH_LONG).show()

                   }


               }

            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        binding.tvNoResult.visibility = View.INVISIBLE

        if(reservationsList.size == 0)
        {
            binding.tvNoResult.visibility = View.VISIBLE
        }
        touchHelper.attachToRecyclerView(binding.rvReservations)
        binding.rvReservations.adapter = adapter

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}