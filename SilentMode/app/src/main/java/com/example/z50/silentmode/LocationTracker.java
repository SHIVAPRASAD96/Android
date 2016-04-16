package com.example.z50.silentmode;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Z50 on 28-07-2015.
 */
public class LocationTracker implements LocationListener {


    private final String TAG = "LocationTracker";

    private final Context mContext;

    private boolean canGetLocation = false;

    private Location location;
    private double latitude;
    private double longitude;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    protected LocationManager locationManager;

    public LocationTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    /**
     * to get the user's current location
     *
     * @return Location
     */
    public Location getLocation() {
        try {

            String provider = LocationManager.GPS_PROVIDER;

            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            // first checking if GPS is enabled then get the location using GPS provider
            // if GPS not able to get the location or disabled then getting location details using Network provider
            Location gpsLocation = _getLocation(locationManager, provider);

            if(gpsLocation != null && gpsLocation.getLatitude() != 0 && gpsLocation.getLongitude() != 0){
                // gps location captured...
                canGetLocation = true;
                latitude = gpsLocation.getLatitude();
                longitude = gpsLocation.getLongitude();
                location = gpsLocation;
            } else {

                provider = LocationManager.NETWORK_PROVIDER;
                Location networkLocation = _getLocation(locationManager, provider);
                if(networkLocation != null && networkLocation.getLatitude() != 0 && networkLocation.getLongitude() != 0){
                    // network location captured...
                    canGetLocation = true;
                    latitude = networkLocation.getLatitude();
                    longitude = networkLocation.getLongitude();
                    location = networkLocation;
                } else{
                    // capturing best location provider which is available
                    Criteria criteria = new Criteria();
                    provider = locationManager.getBestProvider(criteria, false);
                    locationManager.requestLocationUpdates(
                            provider,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Location location = locationManager.getLastKnownLocation(provider);
                    if(location != null){
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        this.location = location;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * to get the user's current location based on the location provider (GPS provider or Network provider)
     *
     * @return Location
     */
    public Location _getLocation(LocationManager locationManager,String provider){
        Location location = null;
        boolean isProviderEnabled = locationManager.isProviderEnabled(provider);
        Log.d(TAG, "is " + provider + " provider enabled : " + isProviderEnabled);
        if (isProviderEnabled) {
            locationManager.requestLocationUpdates(
                    provider,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            Log.d(TAG, "Tracking location through " + provider);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.d(TAG , provider + " location :: latitude : " + latitude + " longitude : " + longitude);
                }
            }
        }
        return location;
    }

    /**
     * Stops using Location Tracking
     * */
    public void stopUsingLocation() {
        if (locationManager != null) {
            locationManager.removeUpdates(LocationTracker.this);
        }
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check location provider enabled
     *
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }


    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }






}