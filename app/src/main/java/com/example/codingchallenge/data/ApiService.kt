package com.example.codingchallenge.data

import com.example.codingchallenge.data.model.HomePageModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("test/home")
    suspend fun getHomeData(): Response<HomePageModel>
}