package com.vehiclecare.vc_driver_arvind.activity.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vehiclecare.vc_driver_arvind.R;
import com.vehiclecare.vc_driver_arvind.activity.callbacks.MapCallback;
import com.vehiclecare.vc_driver_arvind.viewmodels.MapViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private final MapViewModel mapViewModel;
    private MapCallback mapCallback;


    public BottomSheetFragment(MapViewModel mapViewModel, MapCallback mapCallback) {
        this.mapViewModel = mapViewModel;
        this.mapCallback = mapCallback;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.trip_details_bottom_sheet_layout, container, false);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ImageView close_button = view.findViewById(R.id.iv_close);
        Button startTripButton = view.findViewById(R.id.btn_start_trip);
        TextInputEditText vehicleNo = view.findViewById(R.id.vehicleNo);
        TextInputLayout til_vehicle_no = view.findViewById(R.id.til_vehicle_no);

        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        RadioGroup radioGroup = view.findViewById(R.id.rg_trip);

//        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
//
//            RadioButton radioButton = group.findViewById(checkedId);
//            if (radioButton != null) {
//                mapViewModel.getTripType().postValue(radioButton.getText().toString());
//            }
//        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb) {
                    mapViewModel.getTripType().postValue(rb.getText().toString().toLowerCase());
                }

            }
        });


        startTripButton.setOnClickListener(v -> {

            if (TextUtils.isEmpty(vehicleNo.getText())) {
                til_vehicle_no.setError("This Field is required");
                return;
            }

//            mapViewModel.getVehicle_plate_number().postValue(Objects.requireNonNull(vehicleNo.getText()).toString());

//            mapViewModel.getClickAction().postValue(true);
            mapCallback.tripStarted(Objects.requireNonNull(vehicleNo.getText()).toString());
        });


        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";

                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                            state = "HIDDEN";
                            break;
                        }
                    }

                    Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }

    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }
}
