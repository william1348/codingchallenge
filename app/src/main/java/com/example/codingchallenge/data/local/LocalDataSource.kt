package com.example.codingchallenge.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.example.codingchallenge.data.model.HomePageModel
import javax.inject.Inject


@Entity(tableName = "my_data_table")
data class HomePageDataModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String
)

@Dao
interface MyDao {
    @Query("SELECT * FROM my_data_table")
    fun getAllData(): List<HomePageModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<HomePageModel>)
}

class LocalDataSource @Inject constructor(private val dao: MyDao) {
    fun getHomeData() = dao.getAllData()
    fun saveData(data: List<HomePageModel>) = dao.insertAll(data)
}