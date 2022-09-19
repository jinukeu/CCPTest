package com.plub.ccptest.data.remote

import com.google.gson.annotations.SerializedName

data class JokeResponse(
    @SerializedName("icon_url") val iconUrl: String,
    val value: String
)