package ru.githubusers

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.githubusers.model.UserCollection
import ru.githubusers.model.UserInfo
import ru.githubusers.model.UserRepo

object Api {
    fun retrofit() : Retrofit = Retrofit.Builder()
        .client(OkHttpClient().newBuilder().build())
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val getUsers : GetUserInformation = retrofit().create(GetUserInformation::class.java)

}

interface GetUserInformation{
    @GET("search/users")
    fun getUsers(@Query("q") q:String) : Deferred<Response<UserCollection>>

    @GET("users/{id}")
    fun getUserDetails(@Path("id") id:String) :  Deferred<Response<UserInfo>>

    @GET("users/{id}/repos")
    fun getUserRepos(@Path("id") id:String) :  Deferred<Response<ArrayList<UserRepo>>>
}