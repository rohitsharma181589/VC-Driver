package com.vehiclecare.vc_driver_arvind.viewmodels

import androidx.lifecycle.MutableLiveData
import com.vehiclecare.vc_driver_arvind.activity.BaseActivity
import com.vehiclecare.vc_driver_arvind.model.BaseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginViaOtpViewModel @Inject constructor() : BaseViewModel() {

    var loginResponse: MutableLiveData<BaseModel> = MutableLiveData()

    fun callLoginApi(phoneNumber: String) {
        apiServiceRetorfit.driverLogin(
            BaseActivity.AccessCodeFromJNI(),
            phoneNumber
        ).enqueue(object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {

                if (response.body() != null) {
                    loginResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {

                loginResponse.postValue(null)
            }

        })
    }

}