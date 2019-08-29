package ru.githubusers.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserInfo(
        @SerializedName("name")
        @Expose
        var name: String)