package ru.githubusers.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserEntry(@SerializedName("id")
                 @Expose
                 var id: String,
                     @SerializedName("login")
                 @Expose
                 var login: String?,
                     @SerializedName("avatar_url")
                 @Expose
                 var avatar_url: String?)
