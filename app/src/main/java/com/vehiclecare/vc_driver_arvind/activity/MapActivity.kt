package com.vehiclecare.vc_driver_arvind.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.vehiclecare.vc_driver_arvind.BuildConfig
import com.vehiclecare.vc_driver_arvind.R
import com.vehiclecare.vc_driver_arvind.databinding.MapActivityLayoutBinding
import com.vehiclecare.vc_driver_arvind.utils.LocationHelper
import com.vehiclecare.vc_driver_arvind.utils.LocationUpdateCallBack
import com.vehiclecare.vc_driver_arvind.viewmodels.MapViewModel


class MapActivity : BaseActivity(), LocationUpdateCallBack, OnMapReadyCallback,
    GoogleMap.OnCameraMoveStartedListener {

    private lateinit var mapViewModel: MapViewModel
    private lateinit var mapActivityLayoutBinding: MapActivityLayoutBinding
    private lateinit var locationHelper: LocationHelper
    private lateinit var mMap: GoogleMap
    private lateinit var mLocation: Location
    private val zoomLevel = 14f
    private val TAG = MapActivity::class.java.canonicalName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapActivityLayoutBinding =
            DataBindingUtil.setContentView(this, R.layout.map_activity_layout)
        val model: MapViewModel by viewModels {
            viewModelFactory
        }

        mapActivityLayoutBinding.lifecycleOwner = this
        mapActivityLayoutBinding.setVariable(
            com.vehiclecare.vc_driver_arvind.BR.mapVariable,
            model
        )
        mapViewModel = model


        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY);
        }

        val placesClient = Places.createClient(this)

        locationHelper = LocationHelper(this, this)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val placedFragent =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        placedFragent.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        placedFragent.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                mapActivityLayoutBinding.tvPlaces.text = place.name
                Log.i(TAG, "Place: " + place.name + ", " + place.id)
            }

            override fun onError(status: Status) {

                Log.i(TAG, "An error occurred: $status")
            }
        })

        locationHelper.startLocationButtonClick()
        showProgressDialog()

    }


    override fun onStart() {
        super.onStart()


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


    }

    override fun fetchedAddress(address: Address?) {

    }

    @SuppressLint("MissingPermission")
    override fun currentLocation(location: Location?) {
        hideProgressDialog()
        if (location != null) {
            mLocation = location

            if (::mMap.isInitialized) {
                val locations = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locations, zoomLevel))
                mMap.isMyLocationEnabled = true
                mMap.uiSettings.isCompassEnabled = true
                mMap.setOnCameraMoveStartedListener(this)

            }
        }
    }

    override fun onError(msg: String?) {
        hideProgressDialog()
//        if (!TextUtils.isEmpty(msg))
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onCameraMoveStarted(p0: Int) {

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LocationHelper.REQUEST_CHECK_SETTINGS && resultCode == Activity.RESULT_OK) {
            showProgressDialog()
            (locationHelper.startLocationButtonClick())
        }
    }


}