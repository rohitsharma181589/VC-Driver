package com.vehiclecare.vc_driver_arvind.model.tripStartData

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class TripData(
    @SerializedName("trip_id") @Expose val trip_id: String,
    @SerializedName("policy_id") @Expose val policy_id: String
) :
    Parcelable {


}