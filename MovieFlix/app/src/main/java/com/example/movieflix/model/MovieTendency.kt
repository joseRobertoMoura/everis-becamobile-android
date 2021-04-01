package com.example.movieflix.model


import com.google.gson.annotations.SerializedName

data class MovieTendency(
    @SerializedName("results")
    var result: List<Movie>,
    @SerializedName("total_pages")
    var total_pages: String
)