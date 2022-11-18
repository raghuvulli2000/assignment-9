package com.example.yelp.api

import com.example.yelp.Models.DetailModel.Detail
import com.example.yelp.Models.GeoLocationModel.GeoLocationResponse
import com.example.yelp.Models.OptionsModel.OptionsResponse
import com.example.yelp.Models.ReviewsModel.Review
import com.example.yelp.Models.ReviewsModel.Reviews
import com.example.yelp.Models.SearchModel.TableData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YelpReviewsApi {



    @GET("businessid")
    suspend fun getBusinessDetail(
        @Query("id")
        id: String = "88kri8FhXy8b3DQ_QjSMmQ"
    ): Response<Reviews>


}