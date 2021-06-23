package com.vehiclecare.vc_driver_arvind.model.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.vehiclecare.vc_driver_arvind.model.BaseModel

class LoginDriverResponse : BaseModel() {
    @SerializedName("data")
    @Expose
    var loginData: LoginData? = null
}