package com.vehiclecare.vc_driver_arvind.viewmodels

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.vehiclecare.vc_driver_arvind.activity.BaseActivity
import com.vehiclecare.vc_driver_arvind.model.createDriver.CreateDriverResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RegistrationViewModel @Inject constructor() : BaseViewModel() {


    var firstName = MutableLiveData<String>("")
    var firstNameError = MutableLiveData<String>("")
    var email = MutableLiveData<String>("")
    var emailError = MutableLiveData<String>("")
    var phone = MutableLiveData<String>("")
    var phoneError = MutableLiveData<String>("")
    var gender = MutableLiveData<String>("Male")
    var genderError = MutableLiveData<String>("")
    var dob = MutableLiveData<String>("")
    var dobError = MutableLiveData<String>("")
    var address = MutableLiveData<String>("")
    var addressError = MutableLiveData<String>("")
    var state = MutableLiveData<String>("")
    var stateError = MutableLiveData<String>("")
    var city = MutableLiveData<String>("")
    var cityError = MutableLiveData<String>("")
    var pinCode = MutableLiveData<String>("")
    var pinCodeError = MutableLiveData<String>("")
    var licenceNo = MutableLiveData<String>("")
    var licenceNoError = MutableLiveData<String>("")
    var aadharNo = MutableLiveData<String>("")
    var aadharNoError = MutableLiveData<String>("")
    var driverApiResponseMutable = MutableLiveData<CreateDriverResponse>()


    fun registerDriver() {

        if (TextUtils.isEmpty(firstName.value)) {
            firstNameError.postValue("This field is required")
            return
        }
        if (TextUtils.isEmpty(email.value)) {
            emailError.postValue("This field is required")
            return
        }
        if (TextUtils.isEmpty(dob.value)) {
            genderError.postValue("This field is required")
            return
        }

        if (TextUtils.isEmpty(address.value)) {
            addressError.postValue("This field is required")
            return
        }

        if (TextUtils.isEmpty(state.value)) {
            stateError.postValue("This field is required")
            return
        }

        if (TextUtils.isEmpty(city.value)) {
            cityError.postValue("This field is required")
            return
        }

        if (TextUtils.isEmpty(licenceNo.value)) {
            licenceNoError.postValue("This field is required")
            return
        }

        if (TextUtils.isEmpty(aadharNo.value)) {
            aadharNoError.postValue("This field is required")
            return
        }

        if (TextUtils.isEmpty(dob.value)) {
            aadharNoError.postValue("This field is required")
            return
        }


        apiServiceRetorfit.createDriver(
            BaseActivity.AccessCodeFromJNI(),
            firstName.value!!,
            email.value!!,
            phone.value!!,
            gender.value!!,
            dob.value!!,
            address.value!!,
            state.value!!,
            city.value!!,
            pinCode.value!!,
            licenceNo.value!!,
            aadharNo.value!!
        ).enqueue(object : Callback<CreateDriverResponse> {
            override fun onResponse(
                call: Call<CreateDriverResponse>,
                response: Response<CreateDriverResponse>
            ) {

                if (response.body()?.status.equals("1") && response.body()?.responseCode.equals("200")) {
                    driverApiResponseMutable.postValue(response.body())
                } else genderError.postValue("Something went wrong")
            }

            override fun onFailure(call: Call<CreateDriverResponse>, t: Throwable) {

                genderError.postValue("Something went wrong")
            }

        })


    }

}