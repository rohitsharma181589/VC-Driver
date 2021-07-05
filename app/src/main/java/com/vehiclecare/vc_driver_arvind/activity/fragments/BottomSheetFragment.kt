package com.vehiclecare.vc_driver_arvind.activity.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.vehiclecare.vc_driver_arvind.R
import com.vehiclecare.vc_driver_arvind.activity.callbacks.MapCallback
import com.vehiclecare.vc_driver_arvind.viewmodels.MapViewModel
import java.util.*

class BottomSheetFragment(
    private val mapViewModel: MapViewModel,
    private val mapCallback: MapCallback
) : BottomSheetDialogFragment() {

    private val TAG = BottomSheetFragment::class.java.canonicalName


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.trip_details_bottom_sheet_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val close_button = view.findViewById<ImageView>(R.id.iv_close)
        val tv_places = view.findViewById<TextView>(R.id.tv_places)
        val startTripButton = view.findViewById<Button>(R.id.btn_start_trip)
        val vehicleNo: TextInputEditText = view.findViewById(R.id.vehicleNo)
        val til_vehicle_no: TextInputLayout = view.findViewById(R.id.til_vehicle_no)
        close_button.setOnClickListener { dismiss() }
        val radioGroup = view.findViewById<RadioGroup>(R.id.rg_trip)


        val placedFragent =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        placedFragent.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS,
                Place.Field.ADDRESS_COMPONENTS
            )
        )

//        placedFragent.setTypeFilter(TypeFilter.CITIES);
        placedFragent.setCountries("IND");


        tv_places.text = mapViewModel.destinationString.value


        placedFragent.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                tv_places.text = "Destination: ${place.name}"
                mapViewModel.destinationString.postValue("Destination: ${place.name}")
//                Log.i(TAG, "Place: " + place.name + ", " + place.id)
                mapCallback.placesSelection(place)
            }

            override fun onError(status: Status) {


            }
        })

//        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
//
//            RadioButton radioButton = group.findViewById(checkedId);
//            if (radioButton != null) {
//                mapViewModel.getTripType().postValue(radioButton.getText().toString());
//            }
//        });
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val rb = group.findViewById<View>(checkedId) as RadioButton
            mapViewModel.tripType.postValue(rb.text.toString().lowercase(Locale.getDefault()))
        }
        startTripButton.setOnClickListener { v: View? ->
            if (TextUtils.isEmpty(vehicleNo.text)) {
                til_vehicle_no.error = "This Field is required"
                return@setOnClickListener
            }

//            mapViewModel.getVehicle_plate_number().postValue(Objects.requireNonNull(vehicleNo.getText()).toString());

//            mapViewModel.getClickAction().postValue(true);
            mapCallback.tripStarted(Objects.requireNonNull(vehicleNo.text).toString())
        }
    }

    override fun dismiss() {
        super.dismiss()
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }
}