package com.vehiclecare.vc_driver_arvind.model.ackoLoginData

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
class Data : Parcelable {
    @SerializedName("acko_data")
    @Expose
    var ackoData: AckoData? = null
}