package com.plub.ccptest

import com.plub.ccptest.data.remote.JokeResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @GET("/jokes/random")
    fun getRandomJoke(@Query("category") category: String?) : Call<JokeResponse>

    @GET("jokes/categories")
    fun getJokeCategories(): Call<MutableList<String>>
}