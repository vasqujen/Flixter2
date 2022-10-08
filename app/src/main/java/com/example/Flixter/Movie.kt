package com.example.Flixter

import com.google.gson.annotations.SerializedName

class Movie {
    @SerializedName("title")
    var title: String? = null
    @SerializedName("poster_path")
    var movieImageUrl: String ? = null
    @SerializedName("overview")
    var description: String? = null

}