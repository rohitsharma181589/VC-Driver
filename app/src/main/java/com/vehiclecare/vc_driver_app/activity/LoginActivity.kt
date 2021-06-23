package com.vehiclecare.vc_driver_app.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.vehiclecare.vc_driver_app.R
import com.vehiclecare.vc_driver_app.databinding.ActivityLoginBinding
import com.vehiclecare.vc_driver_app.viewmodels.LoginViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*


class LoginActivity : BaseActivity()/*, HasAndroidInjector*/ {

    private lateinit var activityLoginBinding: ActivityLoginBinding
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var loginViewModel: LoginViewModel
    private val TAG = LoginActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val model: LoginViewModel by viewModels {
            viewModelFactory
        }

        activityLoginBinding.lifecycleOwner = this
        activityLoginBinding.setVariable(com.vehiclecare.vc_driver_app.BR.loginViewMdel, model)
        loginViewModel = model
        loginViewModel.loginClickSubject.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getFirstObserver())
    }


    private fun getFirstObserver(): Observer<Boolean?> {
        return object : Observer<Boolean?> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, " First onSubscribe : " + d.isDisposed())
            }


            override fun onError(e: Throwable) {

                Log.d(TAG, " First onError : " + e.message)
            }

            override fun onComplete() {

                Log.d(TAG, " First onComplete")
            }

            override fun onNext(t: Boolean?) {
//                startActivity(
//                    Intent(
//                        this@LoginActivity,
//                        RegistrationActivity::class.java
//                    )
//                ).also { finish() }

//                signIn()
//                sendVerificationCode(loginViewModel.userPhone.value)

                if (TextUtils.isEmpty(activityLoginBinding.edNumber.text.toString())) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter your number",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }

                if (activityLoginBinding.edNumber.text.length != 10) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter valid number",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }

                val intent = Intent(this@LoginActivity, LoginViaOtp::class.java)
                intent.putExtra("number", activityLoginBinding.edNumber.text.toString())
                startActivity(intent)
            }
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        loginViewModel.loginClickSubject.unsubscribeOn(Schedulers.single())

    }
}


