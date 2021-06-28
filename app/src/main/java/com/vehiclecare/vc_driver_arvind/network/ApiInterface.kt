package com.vehiclecare.vc_driver_arvind.network

import com.vehiclecare.vc_driver_arvind.model.BaseModel
import com.vehiclecare.vc_driver_arvind.model.ackoLoginData.AckoLoginResponse
import com.vehiclecare.vc_driver_arvind.model.createDriver.CreateDriverResponse
import com.vehiclecare.vc_driver_arvind.model.getAckoVehicleRide.GetAckoVehicleRideResponse
import com.vehiclecare.vc_driver_arvind.model.login.LoginDriverResponse
import com.vehiclecare.vc_driver_arvind.model.tripStartData.TripStartResponse
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


    /**
     * {"latitude":"28.701984","longitude":"77.078873","address":"demo address string","city":"Delhi"}
     */
    @POST("/")
    @FormUrlEncoded
    fun createAckoVehicleRide(
        @Field("user_id") user_id: String,
        @Field("access_key") accessKey: String,
        @Field("token") token: String,
        @Field("authorization_token") authorization_token: String,
        @Field("trip_start_time") trip_start_time: String,
        @Field("trip_category") trip_category: String,
        @Field("vehicle_plate_number") vehicle_plate_number: String,
        @Field("trip_origin") trip_origin: String,
        @Field("trip_destination") trip_destination: String,
        @Field("task") task: String = "createAckoVehicleRide",
        @Field("state") state: String = "ackoPostLogin",
    ): Call<TripStartResponse>


    @POST("/")
    @FormUrlEncoded
    fun endAckoVehicleRide(
        @Field("access_key") accessKey: String,
        @Field("user_id") user_id: String,
        @Field("authorization_token") authorization_token: String,
        @Field("token") token: String,
        @Field("trip_id") trip_id: String,
        @Field("trip_end_date") trip_end_date: String,
        @Field("task") task: String = "endAckoVehicleRide",
        @Field("state") state: String = "ackoPostLogin",
    ): Call<BaseModel>

    @POST("/")
    @FormUrlEncoded
    fun getAckoVehicleRide(
        @Field("access_key") accessKey: String,
        @Field("user_id") user_id: String,
        @Field("token") token: String,
        @Field("task") task: String = "getAckoVehicleRide",
        @Field("state") state: String = "ackoPostLogin",
    ): Call<GetAckoVehicleRideResponse>

}