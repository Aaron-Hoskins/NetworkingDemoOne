package com.examples.coding.networkingdemoone.datasource.remote.retrofit

import com.examples.coding.networkingdemoone.model.User.UserResponce
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RondomUserService {

    @GET("api/")
    fun getRandomUsers(@Query("results") numOfUsers : String)
    : Call<UserResponce>

    @GET("api/")
    fun getRandomUsersByGender(
        @Query("results") numOfUsers : String,
        @Query("gender") gender : String)
            : Call<UserResponce>
}