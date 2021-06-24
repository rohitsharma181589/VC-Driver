package com.vehiclecare.vc_driver_arvind.model.ackoLoginData

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class AckoData : Parcelable {

    @SerializedName("accessToken")
    @Expose
    val accessToken: String = ""
}
