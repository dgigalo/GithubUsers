package ru.githubusers.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserRepo(
        @SerializedName("name")
        @Expose
        var name: String?,
        @SerializedName("description")
        @Expose
        var description: String?,
        @SerializedName("language")
        @Expose
        var language: String?) : Serializable