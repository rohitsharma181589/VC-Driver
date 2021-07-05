package com.vehiclecare.vc_driver_arvind.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.vehiclecare.vc_driver_arvind.R
import com.vehiclecare.vc_driver_arvind.activity.adapter.TripAdapter
import com.vehiclecare.vc_driver_arvind.activity.callbacks.HomeCallback
import com.vehiclecare.vc_driver_arvind.databinding.HomeActivityBinding
import com.vehiclecare.vc_driver_arvind.model.getAckoVehicleRide.TripDatum
import com.vehiclecare.vc_driver_arvind.utils.AppSharedPreference
import com.vehiclecare.vc_driver_arvind.viewmodels.HomeViewModel

class HomeActivity : BaseActivity(), HomeCallback {

    private lateinit var homeActivityBinding: HomeActivityBinding
    private lateinit var homeViewModel: HomeViewModel
    var canStartNewTrip = false
    var tripId = ""
    private lateinit var tripAdapter: TripAdapter
    var tripListAdapter = ArrayList<TripDatum>()

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

        homeViewModel.homeCallback = this


        tripAdapter = TripAdapter(tripListAdapter)
        homeActivityBinding.rcvTrips.adapter = tripAdapter
        homeActivityBinding.rcvTrips.layoutManager = LinearLayoutManager(this)


        homeActivityBinding.swipeRefresh.setOnRefreshListener {

            homeViewModel.getAckoVehicleRide()

        }


    }

    override fun onStart() {
        super.onStart()
        homeActivityBinding.edSearch.text.clear()
        homeActivityBinding.edSearch.clearFocus()
        val userName = AppSharedPreference.getStringValue(AppSharedPreference.USER_Name)
        homeActivityBinding.tvHeader.text = "Welcome: $userName"

        homeViewModel.ackoLogin()
        homeViewModel.getAckoVehicleRide()

        homeViewModel.callBack.observe(this, {

            if (it)
                showProgressDialog()
            else hideProgressDialog()
        })

        //check btn status
//        checkStartBtnState()

        homeActivityBinding.btnStartNewTrip.setOnClickListener {

            handleTripEvent()

        }

        homeActivityBinding.tvLogout.setOnClickListener {
            logoutAndClearData()
        }


        homeViewModel.startAction.observe(this, {
            if (it)
                showProgressDialog()
            else hideProgressDialog()

        })
        homeViewModel.errorMsg.observe(this, {

            hideProgressDialog()
            if (!TextUtils.isEmpty(it))
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

            homeActivityBinding.swipeRefresh.isRefreshing = false
        })
//        homeViewModel.successMsg.observe(this, {
//            if (!TextUtils.isEmpty(it)) {
//                AppSharedPreference.saveStringValue(AppSharedPreference.TRIP_ID, "")
//                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//                checkStartBtnState()
//            }
//        })


        homeActivityBinding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                tripAdapter.filter.filter(s.toString());
            }

        })


        homeActivityBinding.ivClearSearch.setOnClickListener {
            homeActivityBinding.edSearch.text.clear()
        }
    }

    fun checkStartBtnState() {

        if (tripListAdapter.size > 0) {
            if (tripListAdapter[0].endDate == null) {
                tripId = tripListAdapter[0].tripId
                homeActivityBinding.btnStartNewTrip.text = "End Current Trip"
                canStartNewTrip = false
            } else {
                homeActivityBinding.btnStartNewTrip.text = "Start New Trip"
                canStartNewTrip = true
                tripId = ""
            }
        } else {
            homeActivityBinding.btnStartNewTrip.text = "Start New Trip"
            canStartNewTrip = true
            tripId = ""
        }

//        tripId = AppSharedPreference.getStringValue(AppSharedPreference.TRIP_ID)!!
//        if (!TextUtils.isEmpty(tripId) && !tripId.equals("null")) {
//            homeActivityBinding.btnStartNewTrip.text = "End Current Trip"
//            canStartNewTrip = false
//        } else {
//            homeActivityBinding.btnStartNewTrip.text = "Start New Trip"
//            canStartNewTrip = true
//        }
    }

    fun handleTripEvent() {
        val ackoAutho =
            AppSharedPreference.getStringValue(AppSharedPreference.USER_ACKO_AUTHORIZATION)
        if (TextUtils.isEmpty(ackoAutho)) {
            Toast.makeText(this, "Acko login error, please try later", Toast.LENGTH_LONG).show()
            return
        }
        if (canStartNewTrip) {
            Toast.makeText(this, "Opening Map", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        } else if (!TextUtils.isEmpty(tripId)) {
            homeViewModel.endCurrentTrip(tripId)
        } else Toast.makeText(this, "No Trip to end", Toast.LENGTH_SHORT).show()
    }

    override fun tripEnd() {

    }

    override fun tripEndSuccess() {
        AppSharedPreference.saveStringValue(AppSharedPreference.TRIP_ID, "")
        Toast.makeText(this, "Trip Ended", Toast.LENGTH_SHORT).show()
//        checkStartBtnState()//todo call get trip list
        showProgressDialog()
        homeViewModel.getAckoVehicleRide()
    }

    override fun tripListData(tripLst: MutableList<TripDatum>) {
        hideProgressDialog()
        tripListAdapter.clear()
        tripListAdapter.addAll(tripLst)
        tripAdapter.notifyDataSetChanged()
        homeActivityBinding.swipeRefresh.isRefreshing = false

        checkStartBtnState()
    }


}