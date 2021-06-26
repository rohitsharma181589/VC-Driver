package com.vehiclecare.vc_driver_arvind.viewmodels

import android.icu.util.Calendar
import android.location.Address
import android.location.Location
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.vehiclecare.vc_driver_arvind.activity.BaseActivity
import com.vehiclecare.vc_driver_arvind.model.BaseModel
import com.vehiclecare.vc_driver_arvind.utils.AppSharedPreference
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MapViewModel @Inject constructor() : BaseViewModel() {


    var tripType = MutableLiveData<String>("pickup")
    var vehicle_plate_number = MutableLiveData<String>("")
    var errorMsg = MutableLiveData<String>("")
    var successMsg = MutableLiveData<String>("")
    var clickAction = MutableLiveData<Boolean>()
    var startAction = MutableLiveData<Boolean>()
    lateinit var startLatLong: Location
    lateinit var startAddress: Address
    lateinit var destLatLong: Location
    lateinit var destLatLongAddress: Address

    fun statTrip() {

        val user_id = AppSharedPreference.getStringValue(AppSharedPreference.USER_ID)
        val token = AppSharedPreference.getStringValue(AppSharedPreference.USER_TOKEN)
        val USER_ACKO_AUTHORIZATION =
            AppSharedPreference.getStringValue(AppSharedPreference.USER_ACKO_AUTHORIZATION)


        val now = Calendar.getInstance()
        val time = now.time.toInstant()

        if (TextUtils.isEmpty(USER_ACKO_AUTHORIZATION)) {
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

        if (TextUtils.isEmpty(vehicle_plate_number.value)) {
            errorMsg.postValue("Vehicle Plate Number is required, Please enter again")
            return
        }

        if (!::startAddress.isInitialized ) {
            errorMsg.postValue("Something went wrong in getting locations, please try again")
            return
        }

        if ( !::destLatLongAddress.isInitialized){
            errorMsg.postValue("Please create destination marker location in map, by long pressing")
            return
        }

        val tripOrigin = JSONObject()
        tripOrigin.put("latitude", startAddress.latitude)
        tripOrigin.put("longitude", startAddress.longitude)
        tripOrigin.put("address", startAddress.getAddressLine(0))
        tripOrigin.put("city", startAddress.locality)

        val tripDestination = JSONObject()
        tripDestination.put("latitude", destLatLongAddress.latitude)
        tripDestination.put("longitude", destLatLongAddress.longitude)
        tripDestination.put("address",  destLatLongAddress.getAddressLine(0))
        tripDestination.put("city", destLatLongAddress.locality)


        startAction.postValue(true)

        apiServiceRetorfit.createAckoVehicleRide(
            user_id!!,
            BaseActivity.AccessCodeFromJNI(),
            token!!,
            USER_ACKO_AUTHORIZATION!!,
            time.toString(),
            tripType.value!!,
            vehicle_plate_number.value!!, tripOrigin.toString(), tripDestination.toString()
        ).enqueue(object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                startAction.postValue(false)
                if (response.body()?.status.equals("1") && response.body()?.responseCode.equals("200")) {
                    successMsg.postValue("Trip is started")
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