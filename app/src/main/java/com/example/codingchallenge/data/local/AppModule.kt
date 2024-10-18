package com.example.codingchallenge.data.local

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideMyDao(database: AppDatabase): MyDao {
        return database.myDao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(myDao: MyDao): LocalDataSource {
        return LocalDataSourceImpl(myDao)
    }
}