package com.example.lifestyleapp.Room.Databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lifestyleapp.Room.Daos.StepDao
import com.example.lifestyleapp.common.StepDataModel

@Database(entities = arrayOf(StepDataModel::class), version = 1, exportSchema = false)
abstract class StepDatabase: RoomDatabase(){
    abstract fun stepDao(): StepDao

    companion object {
        @Volatile
        private var INSTANCE: StepDatabase? = null

        fun getDatabase(context: Context): StepDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StepDatabase::class.java,
                    "step_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}