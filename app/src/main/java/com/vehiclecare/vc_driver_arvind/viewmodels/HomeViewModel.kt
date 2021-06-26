package com.vehiclecare.vc_driver_arvind.viewmodels

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.vehiclecare.vc_driver_arvind.activity.BaseActivity
import com.vehiclecare.vc_driver_arvind.model.ackoLoginData.AckoLoginResponse
import com.vehiclecare.vc_driver_arvind.utils.AppSharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    var callBack = MutableLiveData<Boolean>()

    fun ackoLogin() {
        val phoneNumber = AppSharedPreference.getStringValue(AppSharedPreference.USER_PHONE)
        val user_id = AppSharedPreference.getStringValue(AppSharedPreference.USER_ID)
        val token = AppSharedPreference.getStringValue(AppSharedPreference.USER_TOKEN)

        if (!TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(user_id) && !TextUtils.isEmpty(
                token
            )
        ) {

            callBack.postValue(true)
            apiServiceRetorfit.ackoLogin(
                BaseActivity.AccessCodeFromJNI(),
                phoneNumber!!,
                user_id!!,
                token!!
            ).enqueue(object : Callback<AckoLoginResponse> {
                override fun onResponse(
                    call: Call<AckoLoginResponse>,
                    response: Response<AckoLoginResponse>
                ) {

                    callBack.postValue(false)
                    if (response.body() != null && response.body()!!.status.equals("1") && response.body()!!.data != null && response.body()!!.data?.ackoData != null &&
                        !TextUtils.isEmpty(response.body()?.data?.ackoData?.accessToken)
                    ) {
                        AppSharedPreference.saveStringValue(
                            AppSharedPreference.USER_ACKO_AUTHORIZATION,
                            response.body()?.data?.ackoData?.accessToken!!
                        )
                    }
                }

                override fun onFailure(call: Call<AckoLoginResponse>, t: Throwable) {
                    callBack.postValue(false)
                }

            })
        }
    }

}