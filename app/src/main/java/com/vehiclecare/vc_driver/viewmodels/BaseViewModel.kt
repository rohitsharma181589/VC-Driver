package com.vehiclecare.vc_driver.viewmodels

import androidx.lifecycle.ViewModel
import com.vehiclecare.vc_driver.network.ApiInterface
import com.vehiclecare.vc_driver.network.RetrofitClient

open class BaseViewModel : ViewModel() {

    val apiServiceRetorfit: ApiInterface = RetrofitClient.instance()

}