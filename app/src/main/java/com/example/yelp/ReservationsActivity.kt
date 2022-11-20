package com.example.yelp

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.yelp.Adapters.ReservationAdapter
import com.example.yelp.Models.Reservation
import com.example.yelp.Models.ReservationModel
import com.example.yelp.SwipeGestures.SwipeGesture
import com.example.yelp.databinding.ActivityDetailsBinding
import com.example.yelp.databinding.ActivityReservationsBinding
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

                   }


               }

            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
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