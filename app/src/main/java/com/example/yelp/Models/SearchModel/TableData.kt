package com.example.yelp.Models.SearchModel

data class TableData(
    val businesses: List<Business>,
    val region: Region,
    val total: Int
)