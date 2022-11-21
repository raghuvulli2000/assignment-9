package com.example.yelp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class splashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        supportActionBar?.hide()
        window?.statusBarColor = Color.parseColor("#ffffff")
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, yelp_splash_screen_1::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}