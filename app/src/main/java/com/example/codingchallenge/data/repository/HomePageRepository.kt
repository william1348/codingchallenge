package com.example.codingchallenge.data.repository

import com.example.codingchallenge.data.NetworkModule
import com.example.codingchallenge.data.local.LocalDataSource
import com.example.codingchallenge.data.model.HomePageModel
import javax.inject.Inject

class HomePageRepository @Inject constructor (
    private val localDataSource: LocalDataSource
) {

    suspend fun getData(): Result<HomePageModel> {
        val apiService = NetworkModule.apiService
        return try {
            val response = apiService.getHomeData()
            if (response.isSuccessful) {
                response.body()?.let {
                    localDataSource.saveHomeData(it)  // Save to local cache
                    return Result.success(it)
                }
            }
            Result.failure(Exception("Error fetching data"))
        } catch (e: Exception) {
            // Fallback to local cache if network call fails
            val localData = localDataSource.getHomeData()
            return if (localData != null) {
                Result.success(localData)
            } else {
                Result.failure(e)
            }
        }
    }
}