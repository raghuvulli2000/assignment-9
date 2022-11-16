package com.example.yelp.Models.DetailModel

data class Hour(
    val hours_type: String,
    val is_open_now: Boolean,
    val `open`: List<Open>
)