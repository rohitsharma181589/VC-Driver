package com.vehiclecare.vc_driver_arvind.network

import com.vehiclecare.vc_driver_arvind.model.ackoLoginData.AckoLoginResponse
import com.vehiclecare.vc_driver_arvind.model.createDriver.CreateDriverResponse
import com.vehiclecare.vc_driver_arvind.model.login.LoginDriverResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {


    @POST("/")
    @FormUrlEncoded
    fun driverLogin(
        @Field("access_key") accessKey: String,
        @Field("phone") phoneNumber: String,
        @Field("task") task: String = "driverLogin",
        @Field("state") state: String = "acko_login",
    ): Call<LoginDriverResponse>

    @POST("/")
    @FormUrlEncoded
    fun ackoLogin(
        @Field("access_key") accessKey: String,
        @Field("phone") phoneNumber: String,
        @Field("user_id") user_id: String,
        @Field("token") token: String,
        @Field("task") task: String = "ackoLogin",
        @Field("state") state: String = "ackoPostLogin",
    ): Call<AckoLoginResponse>

    @POST("/")
    @FormUrlEncoded
    fun createDriver(
        @Field("access_key") accessKey: String,
        @Field("full_name") full_name: String,
        @Field("email") email: String,
        @Field("phone") phoneNumber: String,
        @Field("gender") gender: String,
        @Field("dob") dob: String,
        @Field("address") address: String,
        @Field("state_name") state_name: String,
        @Field("city") city: String,
        @Field("pincode") pincode: String,
        @Field("license_number") license_number: String,
        @Field("aadhar_number") aadhar_number: String,
        @Field("task") task: String = "createDriver",
        @Field("state") state: String = "acko_login",
    ): Call<CreateDriverResponse>


}