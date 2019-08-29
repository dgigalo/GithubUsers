package ru.githubusers.db


import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class UserRepository(val userEntryDAO: UserDataDAO) {

    @WorkerThread
    suspend fun insert(userData: List<UserData>){
        userEntryDAO.insert(userData)
    }

    val qetUserData: LiveData<List<UserData>> = userEntryDAO.GetUserData()
}