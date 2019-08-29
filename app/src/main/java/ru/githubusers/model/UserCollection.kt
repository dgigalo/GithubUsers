package ru.githubusers.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserCollection (
    @SerializedName("items")
    @Expose
    val items:ArrayList<UserEntry>?)