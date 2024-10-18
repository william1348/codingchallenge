package com.example.codingchallenge.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.codingchallenge.data.model.HomePageModel
import javax.inject.Inject


@Dao
interface MyDao {
    @Query("SELECT * FROM home_page_table")
    suspend fun getAllData(): HomePageModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: HomePageModel)
}


interface LocalDataSource {
    suspend fun saveHomeData(homePageModel: HomePageModel)
    suspend fun getHomeData(): HomePageModel?
}


class LocalDataSourceImpl @Inject constructor(
    private val myDao: MyDao
) : LocalDataSource {

    override suspend fun saveHomeData(homePageModel: HomePageModel) {
        myDao.insertAll(homePageModel)
    }

    override suspend fun getHomeData(): HomePageModel? {
        return myDao.getAllData()
    }
}