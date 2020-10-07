package com.example.lifestyleapp.Room.Daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lifestyleapp.common.UserDataModel

@Dao
interface UserDao {
    @Query("SELECT * from user_table LIMIT 1")
    fun getUserLiveData(): LiveData<List<UserDataModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserDataModel)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()
}