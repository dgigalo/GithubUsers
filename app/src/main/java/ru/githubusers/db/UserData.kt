package ru.githubusers.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.githubusers.model.UserRepo
import java.io.Serializable

@Entity(tableName = "users_table")
data class UserData (
    @PrimaryKey
    val userLogin:String,
    val userAvatar:String,
    @TypeConverters(RepoDataConverter::class)
    val data:ArrayList<UserRepo>
): Serializable