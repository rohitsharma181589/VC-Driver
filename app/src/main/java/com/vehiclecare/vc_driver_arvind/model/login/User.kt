package com.vehiclecare.vc_driver_arvind.model.login

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class User : Parcelable {
    @SerializedName("user_id")
    @Expose
    var userId: String = ""

    @SerializedName("email")
    @Expose
    var email: String = ""

    @SerializedName("phone")
    @Expose
    var phone: String = ""

    @SerializedName("full_name")
    @Expose
    var fullName: String = ""

    @SerializedName("gender")
    @Expose
    var gender: String? = null

    @SerializedName("dob")
    @Expose
    var dob: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("state")
    @Expose
    var state: String? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    @SerializedName("pincode")
    @Expose
    var pincode: String? = null

    @SerializedName("license_number")
    @Expose
    var licenseNumber: String? = null

    @SerializedName("license_image")
    @Expose
    var licenseImage: Any? = null

    @SerializedName("aadhar_number")
    @Expose
    var aadharNumber: String? = null

    @SerializedName("aadhar_image")
    @Expose
    var aadharImage: Any? = null

    @SerializedName("token")
    @Expose
    var token: String = ""
}