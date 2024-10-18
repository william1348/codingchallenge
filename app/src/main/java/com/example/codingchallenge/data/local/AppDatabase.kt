package com.example.codingchallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.codingchallenge.data.model.HomePageModel

@Database(entities = [HomePageModel::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun myDao(): MyDao
}