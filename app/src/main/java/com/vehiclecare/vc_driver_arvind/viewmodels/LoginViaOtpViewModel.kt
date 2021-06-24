package com.vehiclecare.vc_driver_arvind.viewmodels

import androidx.lifecycle.MutableLiveData
import com.vehiclecare.vc_driver_arvind.activity.BaseActivity
import com.vehiclecare.vc_driver_arvind.model.login.LoginDriverResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginViaOtpViewModel @Inject constructor() : BaseViewModel() {

    var loginResponse: MutableLiveData<LoginDriverResponse> = MutableLiveData()

    fun callLoginApi(phoneNumber: String) {
        apiServiceRetorfit.driverLogin(
            BaseActivity.AccessCodeFromJNI(),
            phoneNumber
        ).enqueue(object : Callback<LoginDriverResponse> {
            override fun onResponse(
                call: Call<LoginDriverResponse>,
                response: Response<LoginDriverResponse>
            ) {

                if (response.body() != null) {
                    loginResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<LoginDriverResponse>, t: Throwable) {

                loginResponse.postValue(null)
            }

        })
    }

}