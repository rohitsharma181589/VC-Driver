package com.vehiclecare.vc_driver_app.viewmodels

import androidx.lifecycle.MutableLiveData
import com.vehiclecare.vc_driver_app.activity.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginViaOtpViewModel @Inject constructor() : BaseViewModel() {

    var loginResponse: MutableLiveData<Any> = MutableLiveData()

    fun calllLoginApi(phoneNumber: String) {
        apiServiceRetorfit.loginUser(
            BaseActivity.AccessCodeFromJNI(),
            "userLogin",
            "login",
            phoneNumber
        ).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {

                if (response.body() != null) {
                    loginResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {

                loginResponse.postValue(null)
            }

        })
    }

}