package com.example.yelp.Models.OptionsModel

data class OptionsResponse(
    val businesses: List<Any>,
    val categories: List<Category>,
    val terms: List<Term>
)