package com.example.codingchallenge.data.local

import androidx.room.TypeConverter
import com.example.codingchallenge.data.model.HomePageWrapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    private val gson = Gson()

    @TypeConverter
    fun fromHomePageWrapper(homePageWrapper: HomePageWrapper?): String? {
        return homePageWrapper?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toHomePageWrapper(data: String?): HomePageWrapper? {
        return data?.let {
            val type = object : TypeToken<HomePageWrapper>() {}.type
            gson.fromJson(it, type)
        }
    }
}