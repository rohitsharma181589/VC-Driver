package com.vehiclecare.vc_driver_arvind.model.tripStartData

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
class Data(@SerializedName("TripData")@Expose val tripData: TripData) : Parcelable {
}