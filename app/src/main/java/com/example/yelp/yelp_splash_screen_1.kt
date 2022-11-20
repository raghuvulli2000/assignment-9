package com.example.yelp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.yelp.Models.Reservation
import com.example.yelp.Models.ReservationModel
import com.google.gson.Gson

class yelp_splash_screen_1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yelp_splash_screen1)
        val sharedPreferences: SharedPreferences = applicationContext.getSharedPreferences("MyReservations", 0)
        if(!sharedPreferences.contains("reservations")){
            val reservations  : MutableList<Reservation> = mutableListOf()
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("reservations", Gson().toJson(ReservationModel(reservations)))
            editor.commit()
            Log.d("Shared Pref", Gson().fromJson(sharedPreferences.getString("reservations", ""), ReservationModel::class.java).toString())
        }
        else{
            Log.d("Shared Pref", Gson().fromJson(sharedPreferences.getString("reservations", ""), ReservationModel::class.java).toString())
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}