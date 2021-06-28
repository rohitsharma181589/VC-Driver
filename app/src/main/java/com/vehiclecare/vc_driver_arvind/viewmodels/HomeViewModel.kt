package com.vehiclecare.vc_driver_arvind.viewmodels

import android.icu.util.Calendar
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.vehiclecare.vc_driver_arvind.activity.BaseActivity
import com.vehiclecare.vc_driver_arvind.activity.callbacks.HomeCallback
import com.vehiclecare.vc_driver_arvind.model.BaseModel
import com.vehiclecare.vc_driver_arvind.model.ackoLoginData.AckoLoginResponse
import com.vehiclecare.vc_driver_arvind.utils.AppSharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    var callBack = MutableLiveData<Boolean>()
    var errorMsg = MutableLiveData<String>("")
    var successMsg = MutableLiveData<String>("")
    var startAction = MutableLiveData<Boolean>()
    lateinit var homeCallback: HomeCallback

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

    fun endCurrentTrip(tripId: String) {

        val user_id = AppSharedPreference.getStringValue(AppSharedPreference.USER_ID)
        val token = AppSharedPreference.getStringValue(AppSharedPreference.USER_TOKEN)
        val authToken =
            AppSharedPreference.getStringValue(AppSharedPreference.USER_ACKO_AUTHORIZATION)

        val now = Calendar.getInstance()
        val time = now.time.toInstant()

        if (TextUtils.isEmpty(tripId) || tripId.equals("null")) {
            errorMsg.postValue("Trip Id is wrong")
            return
        }

        if (TextUtils.isEmpty(authToken)) {
            errorMsg.postValue("Can not without acko token")
            return
        }

        if (TextUtils.isEmpty(user_id)) {
            errorMsg.postValue("Session is expired, Please login")
            return
        }

        if (TextUtils.isEmpty(token)) {
            errorMsg.postValue("Session is expired, Please login")
            return
        }

        startAction.postValue(true)


        apiServiceRetorfit.endAckoVehicleRide(
            BaseActivity.AccessCodeFromJNI(),
            user_id!!,
            authToken!!,
            token!!, tripId, time.toString()
        ).enqueue(object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                startAction.postValue(false)

                if (response.body()?.status.equals("1") && response.body()?.responseCode.equals("200")) {
//                    successMsg.postValue("Trip Finished")
                    homeCallback.tripEndSuccess()
                } else if (!TextUtils.isEmpty(response.body()?.message)) {
                    errorMsg.postValue(response.body()?.message)

                } else errorMsg.postValue("Something went wrong, please try again")
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                startAction.postValue(false)
                errorMsg.postValue("Something went wrong, please try again")
            }

        })
    }

}