package com.example.lifestyleapp.Room.Daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lifestyleapp.common.WeatherEntity

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_table")
    fun getWeatherLiveData(): LiveData<List<WeatherEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherEntity: WeatherEntity)

    @Query("DELETE FROM weather_table")
    suspend fun deleteAll()
}