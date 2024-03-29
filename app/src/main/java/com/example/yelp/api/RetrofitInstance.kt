package com.example.yelp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {


        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl("https://business-app-angular-node.wl.r.appspot.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        private val ipconfig by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl("https://ipinfo.io")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(YelpApi::class.java)
        }


        val reviewapi by lazy {
            retrofit.create(YelpReviewsApi::class.java)
        }

        val locaction by lazy {
            ipconfig.create(YelpCurrentLocation::class.java)
        }
    }

}