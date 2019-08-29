package ru.githubusers.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(app: Application) : AndroidViewModel(app) {

    private val usersRepository:UserRepository
    val allUsers: LiveData<List<UserData>>

    init {
        val userDAO = UserDB.getDB(app).getUserDataDAO()
        usersRepository = UserRepository(userDAO)
        allUsers = usersRepository.qetUserData
    }

    fun insert(list:List<UserData>) = viewModelScope.launch(Dispatchers.IO){
        usersRepository.insert(list)
    }
}