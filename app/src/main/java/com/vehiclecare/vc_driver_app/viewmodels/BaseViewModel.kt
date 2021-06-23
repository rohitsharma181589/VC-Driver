package com.vehiclecare.vc_driver_app.viewmodels

import androidx.lifecycle.ViewModel
import com.vehiclecare.vc_driver_app.network.ApiInterface
import com.vehiclecare.vc_driver_app.network.RetrofitClient

open class BaseViewModel : ViewModel() {

    val apiServiceRetorfit: ApiInterface = RetrofitClient.instance()

}