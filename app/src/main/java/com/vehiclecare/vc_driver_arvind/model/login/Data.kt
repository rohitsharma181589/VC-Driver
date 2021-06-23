package com.vehiclecare.vc_driver_arvind.model.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginData {
    @SerializedName("user")
    @Expose
    var user: User? = null
}