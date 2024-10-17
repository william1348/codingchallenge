package com.example.codingchallenge.data.repository

import android.util.Log
import com.example.codingchallenge.data.NetworkModule
import com.example.codingchallenge.data.model.HomePageModel
import javax.inject.Inject

class HomePageRepository @Inject constructor (
   // private val apiService: ApiService,
    //private val localDataSource: LocalDataSource
) {

    suspend fun getData(): Result<HomePageModel> {
        val apiService = NetworkModule.apiService
        return try {
            val response = apiService.getHomeData()
            if (response.isSuccessful) {
                response.body()?.let {
                  //  localDataSource.saveData(it)  // Save to local cache
                    return Result.success(it)
                }
            }
            Result.failure(Exception("Error fetching data"))
        } catch (e: Exception) {
            Log.d("@@@", " exception: " + e)
//            // Fallback to local cache if network call fails
//            val localData = localDataSource.getHomeData()
//            if (localData.isNotEmpty()) {
//                Result.success(localData)
//            } else {
//                Result.failure(e)
//            }
            Result.failure(e)
        }
    }
}