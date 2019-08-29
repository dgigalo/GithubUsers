package ru.githubusers.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.githubusers.model.UserRepo
import kotlin.collections.ArrayList

class RepoDataConverter {

    companion object{
        @TypeConverter
        @JvmStatic
        fun fromString(value:String?) : ArrayList<UserRepo>?{
            val mapType = object : TypeToken<ArrayList<UserRepo>>() {}.type
            return Gson().fromJson<ArrayList<UserRepo>>(value, mapType)
        }

        @TypeConverter
        @JvmStatic
        fun fromArray(repos:ArrayList<UserRepo>?) : String{
            return Gson().toJson(repos)
        }
    }
}