package com.example.yelp.Models.GeoLocationModel

data class GeoLocationResponse(
    val results: List<Result>,
    val status: String
)