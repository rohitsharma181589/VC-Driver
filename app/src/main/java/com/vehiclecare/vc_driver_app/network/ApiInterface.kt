package com.vehiclecare.vc_driver_app.network

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {


    @POST("/")
    @FormUrlEncoded
    fun loginUser(
        @Field("access_key") accessKey: String,
        @Field("task") task: String,
        @Field("state") state: String,
        @Field("phone") phoneNumber: String
    ): Call<Any>

    @POST("/")
    @FormUrlEncoded
    fun createUser(
        @Field("access_key") accessKey: String,
        @Field("task") task: String = "createUser",
        @Field("state") state: String = "login",
        @Field("phone") phoneNumber: String,
        @Field("email") email: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
    ): Call<Any>


}