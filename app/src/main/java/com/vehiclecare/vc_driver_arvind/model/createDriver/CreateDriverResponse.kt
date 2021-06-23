package com.vehiclecare.vc_driver_arvind.model.createDriver

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.vehiclecare.vc_driver_arvind.model.BaseModel

class CreateDriverResponse(@SerializedName("data") @Expose val driverData: DriverData) :
    BaseModel() {
}