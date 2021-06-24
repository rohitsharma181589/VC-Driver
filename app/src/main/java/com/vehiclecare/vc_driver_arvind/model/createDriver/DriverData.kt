package com.vehiclecare.vc_driver_arvind.model.createDriver

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class DriverData(
    @SerializedName("user") @Expose
    var user: User
) : Parcelable
