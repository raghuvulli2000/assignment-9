package com.example.yelp.api

import com.example.yelp.Models.DetailModel.Detail
import com.example.yelp.Models.GeoLocationModel.GeoLocationResponse
import com.example.yelp.Models.IpconfigModel.currlocation
import com.example.yelp.Models.OptionsModel.OptionsResponse
import com.example.yelp.Models.ReviewsModel.Review
import com.example.yelp.Models.ReviewsModel.Reviews
import com.example.yelp.Models.SearchModel.TableData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YelpCurrentLocation {
    //https://ipinfo.io/?token=5c0e5f4b3c703a

    @GET("/")
    suspend fun getCurrentLocation(
        @Query("token")
        token: String = "5c0e5f4b3c703a"
    ): Response<currlocation>


}