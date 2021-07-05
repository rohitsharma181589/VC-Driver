package com.vehiclecare.vc_driver_arvind.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Location
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.vehiclecare.vc_driver_arvind.BuildConfig
import com.vehiclecare.vc_driver_arvind.R
import com.vehiclecare.vc_driver_arvind.activity.callbacks.MapCallback
import com.vehiclecare.vc_driver_arvind.activity.fragments.BottomSheetFragment
import com.vehiclecare.vc_driver_arvind.activity.fragments.FormFragment
import com.vehiclecare.vc_driver_arvind.databinding.MapActivityLayoutBinding
import com.vehiclecare.vc_driver_arvind.utils.AppSharedPreference
import com.vehiclecare.vc_driver_arvind.utils.LocationHelper
import com.vehiclecare.vc_driver_arvind.utils.LocationUpdateCallBack
import com.vehiclecare.vc_driver_arvind.viewmodels.MapViewModel


class MapActivity : BaseActivity(), LocationUpdateCallBack, OnMapReadyCallback,
    GoogleMap.OnMapLongClickListener,
    GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraIdleListener, MapCallback {

    private val AUTOCOMPLETE_REQUEST_CODE = 1

    private lateinit var mapViewModel: MapViewModel
    private lateinit var mapActivityLayoutBinding: MapActivityLayoutBinding
    private lateinit var locationHelper: LocationHelper
    private lateinit var mMap: GoogleMap
    private lateinit var mLocation: Location
    private val zoomLevel = 14f
    private val TAG = MapActivity::class.java.canonicalName

    lateinit var bottomSheetFragment: BottomSheetFragment
    lateinit var formFragment: FormFragment
    var showForm = false
    var firstTimeshowForm = true
    lateinit var lat: String
    lateinit var long: String
    private var openMapActivity = false


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

        mapViewModel.mapCallback = this


        openMapActivity = intent.hasExtra(AppSharedPreference.GEO_DATA_LONG)

        if (openMapActivity) {
            lat = intent.getStringExtra(AppSharedPreference.GEO_DATA_LAT).toString()
            long = intent.getStringExtra(AppSharedPreference.GEO_DATA_LONG).toString()
        }


//        formFragment= FormFragment()

        mapActivityLayoutBinding.mcvShowForm.setOnClickListener {

            if (::bottomSheetFragment.isInitialized && !bottomSheetFragment.isVisible) {
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            }
        }


        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.MAPS_PLACES_API_KEY_NEW);
        }

        val placesClient = Places.createClient(this)

        locationHelper = LocationHelper(this, this)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)


//        val placedFragent =
//            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
//        placedFragent.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
//
//        placedFragent.setTypeFilter(TypeFilter.CITIES);
//
//
//        placedFragent.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//            override fun onPlaceSelected(place: Place) {
//                mapActivityLayoutBinding.tvPlaces.text = place.name
//                Log.i(TAG, "Place: " + place.name + ", " + place.id)
//            }
//
//            override fun onError(status: Status) {
//
//                Log.i(TAG, "An error occurred: $status")
//            }
//        })

        bottomSheetFragment = BottomSheetFragment(mapViewModel, this)

        locationHelper.startLocationButtonClick()
        showProgressDialog()

    }


    override fun onStart() {
        super.onStart()


//        mapViewModel.clickAction.observe(this, {
//            if (it) {
//                mapViewModel.statTrip()
//            }
//        })
//
//        mapViewModel.tripType.observe(this, {
//
//        })
//
//        mapViewModel.vehicle_plate_number.observe(this, {
//
//        })

        mapViewModel.startAction.observe(this, {
            if (it)
                showProgressDialog()
            else hideProgressDialog()

        })
        mapViewModel.errorMsg.observe(this, {
            if (!TextUtils.isEmpty(it))
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
//        mapViewModel.successMsg.observe(this, {
//            if (!TextUtils.isEmpty(it)) {
//                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//                finish()
//            }
//        })

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapLongClickListener(this)

        if (openMapActivity) {
            val latLng = LatLng(lat.toDouble(), long.toDouble())
            placeMarkerAndGetAddress(latLng)
        }


    }

    override fun fetchedAddress(address: Address?) {
        if (address != null) {
            mapViewModel.startAddress = address
        };
    }

    @SuppressLint("MissingPermission")
    override fun currentLocation(location: Location?) {

        hideProgressDialog()
        if (location != null) {
            mLocation = location
            mapViewModel.startLatLong = location

            if (::mMap.isInitialized) {
                val locations = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locations, zoomLevel))
                mMap.isMyLocationEnabled = true
                mMap.uiSettings.isCompassEnabled = true
                mMap.setOnCameraMoveStartedListener(this)
                mMap.setOnCameraIdleListener(this)

            }


//            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)


        }
    }

    override fun onError(msg: String?) {
        hideProgressDialog()
//        if (!TextUtils.isEmpty(msg))
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun markerAddress(address: Address?) {
        hideProgressDialog()
        if (address != null) {
            mapViewModel.destLatLongAddress = address
        } else mapViewModel.errorMsg.postValue("Please create destination marker location in map, by long pressing on map")
    }

    override fun onCameraMoveStarted(p0: Int) {
//        bottomSheetFragment.dismiss()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LocationHelper.REQUEST_CHECK_SETTINGS && resultCode == Activity.RESULT_OK) {
            showProgressDialog()
            (locationHelper.startLocationButtonClick())
        }
    }

    override fun onMapLongClick(ltlng: LatLng) {

        placeMarkerAndGetAddress(ltlng)
    }

    override fun onCameraIdle() {

        mapActivityLayoutBinding.mcvShowForm.visibility = View.VISIBLE

        if (firstTimeshowForm) {
            firstTimeshowForm = false
            if (::bottomSheetFragment.isInitialized)
                if (!bottomSheetFragment.isVisible) {
                    bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
                }
        }

    }

    override fun tripStarted(s: String) {
        mapViewModel.statTrip(s)
    }

    override fun tripStartedSuccess(s: String) {

        if (!TextUtils.isEmpty(s)) {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun placesSelection(s: Place) {
        if (null != s.latLng) {
            placeMarkerAndGetAddress(s.latLng!!)

            if (::mMap.isInitialized) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(s.latLng, zoomLevel))
            }

        } else mapViewModel.errorMsg.postValue("Please create destination marker location in map, by long pressing on map")
    }


    private fun placeMarkerAndGetAddress(ltlng: LatLng) {

        mapViewModel.destLatLong = Location("")
        mapViewModel.destLatLong.latitude = ltlng.latitude
        mapViewModel.destLatLong.longitude = ltlng.longitude

        if (::mMap.isInitialized) {
            mMap.clear()

            mMap.addMarker(
                MarkerOptions()
                    .position(ltlng)
                    .title("Destination")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )

        }
        showProgressDialog()
        locationHelper.getAddressForLatLong(mapViewModel.destLatLong);
    }


}