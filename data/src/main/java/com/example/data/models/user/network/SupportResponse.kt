package com.example.data.models.user.network


import com.google.gson.annotations.SerializedName

data class SupportResponse(
    @SerializedName("url")
    val url: String?,
    @SerializedName("text")
    val text: String?
)