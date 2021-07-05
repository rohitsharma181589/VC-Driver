package com.vehiclecare.vc_driver_arvind.activity.callbacks

import com.vehiclecare.vc_driver_arvind.model.getAckoVehicleRide.TripDatum

interface HomeCallback {

    fun tripEnd()
    fun tripEndSuccess()
    fun tripListData(tripLst: MutableList<TripDatum>)
    fun openMapWithAckoLogin()
}