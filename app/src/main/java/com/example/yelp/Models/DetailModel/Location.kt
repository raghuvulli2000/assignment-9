package com.example.yelp.Models.DetailModel

data class Location(
    val address1: String,
    val address2: String,
    val address3: Any,
    val city: String,
    val country: String,
    val cross_streets: String,
    val display_address: List<String>,
    val state: String,
    val zip_code: String
)