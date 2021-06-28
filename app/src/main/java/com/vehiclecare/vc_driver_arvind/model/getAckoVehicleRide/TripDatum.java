package com.vehiclecare.vc_driver_arvind.model.getAckoVehicleRide;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import kotlinx.parcelize.Parcelize;


@Parcelize
public class TripDatum {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("trip_category")
    @Expose
    public String tripCategory;
    @SerializedName("license_plate_number")
    @Expose
    public String licensePlateNumber;
    @SerializedName("origin")
    @Expose
    public String origin;
    @SerializedName("destination")
    @Expose
    public String destination;
    @SerializedName("driver_id")
    @Expose
    public String driverId;
    @SerializedName("trip_id")
    @Expose
    public String tripId;
    @SerializedName("end_date")
    @Expose
    public Object endDate;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("created")
    @Expose
    public String created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTripCategory() {
        return tripCategory;
    }

    public void setTripCategory(String tripCategory) {
        this.tripCategory = tripCategory;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}