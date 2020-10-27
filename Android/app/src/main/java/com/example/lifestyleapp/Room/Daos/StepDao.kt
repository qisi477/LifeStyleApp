package com.example.lifestyleapp.Room.Daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lifestyleapp.common.StepDataModel

@Dao
interface StepDao {
    @Query("SELECT * FROM step_table")
    fun getStepsData(): LiveData<List<StepDataModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(step: StepDataModel)

    @Query("DELETE FROM step_table")
    suspend fun deleteAll()
}