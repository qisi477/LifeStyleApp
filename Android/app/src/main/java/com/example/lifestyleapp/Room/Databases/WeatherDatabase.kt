package com.example.lifestyleapp.Room.Databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lifestyleapp.Room.Daos.WeatherDao
import com.example.lifestyleapp.common.UserDataModel

@Database(entities = arrayOf(UserDataModel::class), version = 1, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase(){
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}