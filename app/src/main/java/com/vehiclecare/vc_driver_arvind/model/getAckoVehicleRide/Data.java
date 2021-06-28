package com.vehiclecare.vc_driver_arvind.model.getAckoVehicleRide;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import kotlinx.parcelize.Parcelize;

@Parcelize
public class Data {

    @SerializedName("TripData")
    @Expose
    public List<TripDatum> tripData = null;


    public List<TripDatum> getTripData() {
        return tripData;
    }

    public void setTripData(List<TripDatum> tripData) {
        this.tripData = tripData;
    }
}