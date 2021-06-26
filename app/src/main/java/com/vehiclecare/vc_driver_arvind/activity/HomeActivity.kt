package com.vehiclecare.vc_driver_arvind.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.vehiclecare.vc_driver_arvind.R
import com.vehiclecare.vc_driver_arvind.databinding.HomeActivityBinding
import com.vehiclecare.vc_driver_arvind.viewmodels.HomeViewModel
import java.util.*

class HomeActivity : BaseActivity() {

    private lateinit var homeActivityBinding: HomeActivityBinding
    private lateinit var homeViewModel: HomeViewModel

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


        homeActivityBinding.btnStartNewTrip.setOnClickListener {

            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }

}