package com.vehiclecare.vc_driver_arvind.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
open class BaseModel : Parcelable {
    @SerializedName("method_name")
    @Expose
    var methodName: String? = ""

    @SerializedName("status")
    @Expose
    var status: String? = ""

    @SerializedName("response_code")
    @Expose
    var responseCode: String? = ""

    @SerializedName("message")
    @Expose
    var message: String? = ""
}