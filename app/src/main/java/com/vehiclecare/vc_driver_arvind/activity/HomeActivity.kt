package com.vehiclecare.vc_driver_arvind.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.vehiclecare.vc_driver_arvind.R
import com.vehiclecare.vc_driver_arvind.databinding.HomeActivityBinding
import com.vehiclecare.vc_driver_arvind.utils.AppSharedPreference
import com.vehiclecare.vc_driver_arvind.viewmodels.HomeViewModel

class HomeActivity : BaseActivity() {

    private lateinit var homeActivityBinding: HomeActivityBinding
    private lateinit var homeViewModel: HomeViewModel
    var canStartNewTrip = false
    var tripId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.home_activity)
        val model: HomeViewModel by viewModels {
            viewModelFactory
        }

        homeActivityBinding.lifecycleOwner = this
        homeActivityBinding.setVariable(
            com.vehiclecare.vc_driver_arvind.BR.homeVariable,
            model
        )
        homeViewModel = model

    }

    override fun onStart() {
        super.onStart()

        homeViewModel.ackoLogin()

        homeViewModel.callBack.observe(this, {

            if (it)
                showProgressDialog()
            else hideProgressDialog()
        })

        //check btn status
        checkStartBtnState()

        homeActivityBinding.btnStartNewTrip.setOnClickListener {

            val ackoAutho =
                AppSharedPreference.getStringValue(AppSharedPreference.USER_ACKO_AUTHORIZATION)
            if (TextUtils.isEmpty(ackoAutho)) {
                Toast.makeText(this, "Acko login error, please try later", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (canStartNewTrip) {
                val intent = Intent(this, MapActivity::class.java)
                startActivity(intent)
            } else {
                homeViewModel.endCurrentTrip(tripId)
            }
        }


        homeViewModel.startAction.observe(this, {
            if (it)
                showProgressDialog()
            else hideProgressDialog()

        })
        homeViewModel.errorMsg.observe(this, {
            if (!TextUtils.isEmpty(it))
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        homeViewModel.successMsg.observe(this, {
            if (!TextUtils.isEmpty(it)) {
                AppSharedPreference.saveStringValue(AppSharedPreference.TRIP_ID, "")
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                checkStartBtnState()
            }
        })
    }

    fun checkStartBtnState() {

        tripId = AppSharedPreference.getStringValue(AppSharedPreference.TRIP_ID)!!
        if (!TextUtils.isEmpty(tripId) &&  !tripId.equals("null")) {
            homeActivityBinding.btnStartNewTrip.text = "End Current Trip"
            canStartNewTrip = false
        } else {
            homeActivityBinding.btnStartNewTrip.text = "Start New Trip"
            canStartNewTrip = true
        }
    }

}