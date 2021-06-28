package com.vehiclecare.vc_driver_arvind.model.getAckoVehicleRide;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import kotlinx.parcelize.Parcelize;


@Parcelize
public class GetAckoVehicleRideResponse {

    @SerializedName("method_name")
    @Expose
    public String methodName;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("response_code")
    @Expose
    public String responseCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public Data data;


    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}