package com.vehiclecare.vc_driver_arvind.model.createDriver

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DriverData(
    @SerializedName("user") @Expose
    var user: User
)
