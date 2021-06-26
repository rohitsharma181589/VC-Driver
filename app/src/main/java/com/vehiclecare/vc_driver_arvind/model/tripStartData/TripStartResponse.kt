package com.vehiclecare.vc_driver_arvind.model.tripStartData

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.vehiclecare.vc_driver_arvind.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
class TripStartResponse(@SerializedName("data") @Expose val data: Data) : Parcelable, BaseModel() {


}