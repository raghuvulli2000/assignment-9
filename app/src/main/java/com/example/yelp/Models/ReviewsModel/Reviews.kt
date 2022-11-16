package com.example.yelp.Models.ReviewsModel

data class Reviews(
    val possible_languages: List<String>,
    val reviews: List<Review>,
    val total: Int
)