package ru.githubusers.db


import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDataDAO {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userData: List<UserData>)

    @Query("SELECT * FROM users_table")
    fun GetUserData() : LiveData<List<UserData>>
}