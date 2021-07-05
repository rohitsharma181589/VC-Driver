package com.vehiclecare.vc_driver_arvind.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.vehiclecare.vc_driver_arvind.R
import com.vehiclecare.vc_driver_arvind.utils.AppSharedPreference

class SplashActivity : BaseActivity() {

    lateinit var lat: String
    lateinit var long: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_layout)

        val intentData = intent.data
        val data = intentData.toString()
        val schema = intentData?.scheme
        if (schema.equals("geo"))
            parseIntentData(data)

    }

    override fun onStart() {
        super.onStart()
        Handler(Looper.myLooper()!!).postDelayed({ navigateToActivity() }, 1000)
    }

    private fun navigateToActivity() {
        if (AppSharedPreference.getBooleanValue(AppSharedPreference.IS_USER_LOGGED_IN)!!) {
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            if (::lat.isInitialized && ::long.isInitialized) {
                intent.putExtra(AppSharedPreference.GEO_DATA_LAT, lat)
                intent.putExtra(AppSharedPreference.GEO_DATA_LONG, long)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        } else {
            startActivity(Intent(this, LoginActivity::class.java)).also { finish() }
        }
    }


    fun parseIntentData(geo: String) {

        val uriData = Uri.parse(geo)

        val parameter = uriData.query
        lat = parameter?.substring(2, parameter.indexOf(',')).toString()
        long = parameter?.substring(parameter.indexOf(',') + 1, parameter.length).toString()

    }
}