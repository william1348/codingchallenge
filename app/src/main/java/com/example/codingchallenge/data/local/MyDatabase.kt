package com.example.codingchallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.codingchallenge.data.model.HomePageModel

@Database(entities = [HomePageModel::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun myDao(): MyDao
}