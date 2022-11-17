package com.example.yelp.api

import com.example.yelp.Models.DetailModel.Detail
import com.example.yelp.Models.GeoLocationModel.GeoLocationResponse
import com.example.yelp.Models.OptionsModel.OptionsResponse
import com.example.yelp.Models.SearchModel.TableData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YelpApi {

    @GET("route")
    suspend fun getSearchResults(
        @Query("latitude")
        latitude: String = "34.0030",
        @Query("longitude")
        longitude: String = "-118.2863",
        @Query("term")
        term: String = "Pizza",
        @Query("radius")
        radius: String = "16093",
        @Query("category")
        category: String = "All",
    ): Response<TableData>

    @GET("businessid")
    suspend fun getBusinessDetail(
        @Query("id")
        id: String = "88kri8FhXy8b3DQ_QjSMmQ"
    ): Response<Detail>

    @GET("options")
    suspend fun getOptions(
        @Query("id")
        id: String = "Pizza"
    ): Response<OptionsResponse>

    @GET("geoloc")
    suspend fun getGeoLocation(
        @Query("loc")
        loc: String = "LA"
    ): Response<GeoLocationResponse>



}