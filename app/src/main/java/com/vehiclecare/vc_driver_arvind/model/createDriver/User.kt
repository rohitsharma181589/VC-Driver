package com.vehiclecare.vc_driver_arvind.model.createDriver

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class User : Parcelable {
    @SerializedName("user_id")
    @Expose
    var userId = 0

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("full_name")
    @Expose
    var fullName: String? = null

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

    @SerializedName("aadhar_number")
    @Expose
    var aadharNumber: String? = null

    @SerializedName("token")
    @Expose
    var token: String? = null
}