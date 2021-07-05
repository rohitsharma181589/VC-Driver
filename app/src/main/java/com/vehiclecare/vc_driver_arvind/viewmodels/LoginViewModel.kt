package com.vehiclecare.vc_driver_arvind.viewmodels

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class LoginViewModel @Inject constructor(): BaseViewModel() {

    var loginClickSubject: PublishSubject<Boolean>
    var userPhone: MutableLiveData<String>

    init {
        loginClickSubject = PublishSubject.create()
        userPhone = MutableLiveData("")
    }


    fun onContinue() {

        loginClickSubject.onNext(true)

    }

}