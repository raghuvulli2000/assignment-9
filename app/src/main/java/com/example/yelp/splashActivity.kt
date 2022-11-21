package com.example.yelp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class splashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        supportActionBar?.hide()
       // WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
        window?.statusBarColor = Color.parseColor("#ffffff")
        window?.isStatusBarContrastEnforced = true
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, yelp_splash_screen_1::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}