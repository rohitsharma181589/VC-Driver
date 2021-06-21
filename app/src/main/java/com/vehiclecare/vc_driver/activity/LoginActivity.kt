package com.app.arvindVehicleCare.ui.fragments.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.vehiclecare.vc_driver.R
import com.vehiclecare.vc_driver.activity.BaseActivity
import com.vehiclecare.vc_driver.databinding.ActivityLoginBinding
import com.vehiclecare.vc_driver.viewmodels.LoginViewModel
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

    //    var auth = FirebaseAuth.getInstance()
    private val RC_SIGN_IN = 123
    private var verificationId: String? = null
//    private lateinit var phoneAuthCredential: PhoneAuthCredential
//    private lateinit var forceResendingToken: ForceResendingToken


//    @Inject
//    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val model: LoginViewModel by viewModels {
            viewModelFactory
        }

        activityLoginBinding.lifecycleOwner = this
//        activityLoginBinding.setVariable(com.app.arvindVehicleCare.BR.loginViewMdel, model)
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

//                val intent = Intent(this@LoginActivity, LoginViaOtp::class.java)
//                intent.putExtra("number", activityLoginBinding.edNumber.text.toString())
//                startActivity(intent)
            }
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        loginViewModel.loginClickSubject.unsubscribeOn(Schedulers.single())

    }

//    //initializing our callbacks for on verification callback method.
//    //callback method is called on Phone auth provider.
//    private val mCallBack: OnVerificationStateChangedCallbacks =
//        object : OnVerificationStateChangedCallbacks() {
//            //below method is used when OTP is sent from Firebase
//            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
//                super.onCodeSent(s, forceResendingToken)
//                //when we recieve the OTP it contains a unique id wich we are storing in our string which we have already created.
//                verificationId = s
//                this@LoginActivity.forceResendingToken = forceResendingToken
//            }
//
//            //this method is called when user recieve OTP from Firebase.
//            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
//                //below line is used for getting OTP code which is sent in phone auth credentials.
//                val code = phoneAuthCredential.smsCode
//                //checking if the code is null or not.
//                if (code != null) {
//                    //if the code is not null then we are setting that code to our OTP edittext field.
////                    edtOTP.setText(code)
//                    //after setting this code to OTP edittext field we are calling our verifycode method.
//                    verifyCode(code)
//                }
//            }
//
//            //thid method is called when firebase doesnot sends our OTP code due to any error or issue.
//            override fun onVerificationFailed(e: FirebaseException) {
//                //displaying error message with firebase exception.
//                Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
//                Log.e("onVerificationFailed", e.message, e)
//            }
//        }
//
//    private fun signInWithCredential(credential: PhoneAuthCredential) {
//        //inside this method we are checking if the code entered is correct or not.
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
//                if (task.isSuccessful) {
//                    //if the code is correct and the task is succesful we are sending our user to new activity.
////                    val i = Intent(this@LoginActivity, HomeActivity::class.java)
////                    startActivity(i)
////                    finish()
//                    Toast.makeText(this@LoginActivity, "Success.", Toast.LENGTH_LONG)
//                        .show()
//                } else {
//                    //if the code is not correct then we are displaying an error message to the user.
//                    Toast.makeText(this@LoginActivity, task.exception!!.message, Toast.LENGTH_LONG)
//                        .show()
//                    task.exception!!.message?.let { Log.e("Login", it) }
//                }
//            })
//    }
//
//    //below method is use to verify code from Firebase.
//    private fun verifyCode(code: String) {
//        //below line is used for getting getting credentials from our verification id and code.
//        val credential = PhoneAuthProvider.getCredential(verificationId, code)
//        //after getting credential we are calling sign in method.
//        signInWithCredential(credential)
//    }
//
//
//    private fun sendVerificationCode(number: String?) {
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(number) // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(this) // Activity (for callback binding)
//            .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//
//    }
}


