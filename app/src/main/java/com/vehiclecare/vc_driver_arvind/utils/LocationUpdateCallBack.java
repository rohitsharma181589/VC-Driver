package com.vehiclecare.vc_driver_arvind.utils;

import android.location.Address;
import android.location.Location;



public interface LocationUpdateCallBack {

    void fetchedAddress(Address address);
    void currentLocation(Location location);
    void onError(String msg);
    void markerAddress(Address address);
}
