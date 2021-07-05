package com.vehiclecare.vc_driver_arvind.activity.callbacks

import com.google.android.libraries.places.api.model.Place

interface MapCallback {

    fun tripStarted(s: String)
    fun tripStartedSuccess(s: String)
    fun placesSelection(s: Place)
}