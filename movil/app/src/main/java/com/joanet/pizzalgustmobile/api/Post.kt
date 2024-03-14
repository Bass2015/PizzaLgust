package com.joanet.pizzalgustmobile.api
import com.google.gson.annotations.SerializedName


data class Post(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String
)
