package com.vehiclecare.vc_driver_arvind.viewmodels

import androidx.lifecycle.ViewModel
import com.vehiclecare.vc_driver_arvind.network.ApiInterface
import com.vehiclecare.vc_driver_arvind.network.RetrofitClient

open class BaseViewModel : ViewModel() {

    val apiServiceRetorfit: ApiInterface = RetrofitClient.instance()

}