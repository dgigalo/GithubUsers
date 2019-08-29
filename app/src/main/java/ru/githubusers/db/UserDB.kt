package ru.githubusers.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UserData::class], version = 1)
@TypeConverters(RepoDataConverter::class)
abstract class UserDB :  RoomDatabase(){
    abstract fun getUserDataDAO() : UserDataDAO

    companion object{
        @Volatile
        private var INSTANCE : UserDB? = null

        fun getDB(context: Context) : UserDB{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDB::class.java,
                    "Users_database"
                ).fallbackToDestructiveMigration() // пересоздание таблицы
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}