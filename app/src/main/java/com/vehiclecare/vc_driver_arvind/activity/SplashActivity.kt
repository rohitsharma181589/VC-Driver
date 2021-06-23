package com.vehiclecare.vc_driver_arvind.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.vehiclecare.vc_driver_arvind.R
import com.vehiclecare.vc_driver_arvind.utils.AppSharedPreference

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_layout)

    }

    override fun onStart() {
        super.onStart()
        Handler(Looper.myLooper()!!).postDelayed({ navigateToActivity() }, 1000)


    }

    private fun navigateToActivity() {
        if (AppSharedPreference.getBooleanValue(AppSharedPreference.IS_USER_LOGGED_IN)!!) {
//            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        } else {
            startActivity(Intent(this, LoginActivity::class.java)).also { finish() }
        }
    }
}