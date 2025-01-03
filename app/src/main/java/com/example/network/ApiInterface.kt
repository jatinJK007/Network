package com.example.network

import com.example.network.Models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @GET("/posts/1")
    suspend fun getUSer():Response<User>

    @POST("/posts")
    suspend fun createPost(
        @Body user: User
    ):Response<User>

    @FormUrlEncoded
    @POST("/posts")
    suspend fun fpostreq(
        @Field ("user id") userId : Int,
        @Field ("title") title : String,
        @Field ("body") body : String,
    ):Response<User>
}