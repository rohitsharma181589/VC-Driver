package com.vehiclecare.vc_driver_arvind.activity

import `in`.aabhasjindal.otptextview.OTPListener
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.vehiclecare.vc_driver_arvind.R
import com.vehiclecare.vc_driver_arvind.databinding.LoginViaOtpBinding
import com.vehiclecare.vc_driver_arvind.viewmodels.LoginViaOtpViewModel
import java.util.concurrent.TimeUnit


class LoginViaOtp : BaseActivity() {

    lateinit var loginViaOtpBinding: LoginViaOtpBinding
    var auth = FirebaseAuth.getInstance()
    private lateinit var loginViaOtpViewModel: LoginViaOtpViewModel
    private var verificationId: String? = null
    private lateinit var phoneAuthCredential: PhoneAuthCredential
    private lateinit var forceResendingToken: PhoneAuthProvider.ForceResendingToken
    lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViaOtpBinding = DataBindingUtil.setContentView(this, R.layout.login_via_otp)


        val model: LoginViaOtpViewModel by viewModels {
            viewModelFactory
        }
        loginViaOtpViewModel = model
        phoneNumber = intent.getStringExtra("number").toString();

        if (!TextUtils.isEmpty(phoneNumber))
            sendVerificationCode("+91$phoneNumber")
        else {
            Toast.makeText(
                this,
                "Something went wrong, Please restart login process",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onStart() {
        super.onStart()

        loginViaOtpBinding.otpTextView.otpListener = object : OTPListener {

            override fun onInteractionListener() {
            }

            override fun onOTPComplete(otp: String) {

                if (otp.length == 6) {
                    verifyCode(otp)
                }
            }

        }



        loginViaOtpBinding.tvResend.setOnClickListener { resendVerificationCode(phoneNumber) }


        loginViaOtpViewModel.loginResponse.observe(this, {

            if (null != it) {
                if (it.responseCode.equals("602")) {
                    //Send to Register Screen
                    Toast.makeText(this@LoginViaOtp, it.message, Toast.LENGTH_LONG).show()
                } else {
                    if (it.status.equals("1") && it.responseCode.equals("200")) {
                        //Move to Home Screen
                        Toast.makeText(this@LoginViaOtp, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            hideProgressDialog()
        })

    }


    private fun sendVerificationCode(number: String) {
        val options = number.let {
            PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(it) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
                .build()
        }
        PhoneAuthProvider.verifyPhoneNumber(options)

        showProgressDialog()

    }

    //initializing our callbacks for on verification callback method.
    //callback method is called on Phone auth provider.
    private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            //below method is used when OTP is sent from Firebase
            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                //when we recieve the OTP it contains a unique id wich we are storing in our string which we have already created.
                verificationId = s
                this@LoginViaOtp.forceResendingToken = forceResendingToken

                loginViaOtpBinding.tvResend.visibility = View.VISIBLE

                hideProgressDialog()
            }

            //this method is called when user recieve OTP from Firebase.
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                //below line is used for getting OTP code which is sent in phone auth credentials.
                val code = phoneAuthCredential.smsCode
                hideProgressDialog()
                //checking if the code is null or not.
                if (code != null) {
                    //if the code is not null then we are setting that code to our OTP edittext field.
//                    edtOTP.setText(code)
                    //after setting this code to OTP edittext field we are calling our verifycode method.
                    verifyCode(code)
                }
            }

            //thid method is called when firebase doesnot sends our OTP code due to any error or issue.
            override fun onVerificationFailed(e: FirebaseException) {
                hideProgressDialog()
                //displaying error message with firebase exception.
                Toast.makeText(this@LoginViaOtp, e.message, Toast.LENGTH_LONG).show()
                Log.e("onVerificationFailed", e.message, e)

            }
        }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        //inside this method we are checking if the code entered is correct or not.
        auth.signInWithCredential(credential)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                    //if the code is correct and the task is succesful we are sending our user to new activity.
//                    val i = Intent(this@LoginActivity, HomeActivity::class.java)
//                    startActivity(i)
//                    finish()

                    Toast.makeText(this@LoginViaOtp, "Success.", Toast.LENGTH_LONG)
                        .show()
                    loginViaOtpViewModel.callLoginApi(phoneNumber)
//                    startActivity(
//                        Intent(
//                            this@LoginViaOtp,
//                            RegistrationActivity::class.java
//                        )
//                    ).also { finish() }

                    hideProgressDialog()
                } else {
                    hideProgressDialog()
                    //if the code is not correct then we are displaying an error message to the user.
                    Toast.makeText(this@LoginViaOtp, task.exception!!.message, Toast.LENGTH_LONG)
                        .show()
                    task.exception!!.message?.let { Log.e("Login", it) }
                }
            })
    }

    //below method is use to verify code from Firebase.
    private fun verifyCode(code: String) {
        //below line is used for getting getting credentials from our verification id and code.
        if (!TextUtils.isEmpty(verificationId)) {
            val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
            //after getting credential we are calling sign in method.
            showProgressDialog()
            signInWithCredential(credential)
        } else {
            Toast.makeText(
                this,
                "Something went wrong, Please restart login process",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun resendVerificationCode(number: String?) {

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$number") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
            .setForceResendingToken(forceResendingToken)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

        loginViaOtpBinding.tvResend.visibility = View.GONE

        showProgressDialog()

    }
}