package com.example.tarakanovae.weatherapp;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class GPSTracker extends Service implements LocationListener {

    // name of the class
    private static String TAG = GPSTracker.class.getName();

    private final Context mContext;

    // GPS status
    boolean isGPSEnabled = false;

    // network status
    boolean isNetworkEnabled = false;

    boolean isGPSTrackingEnabled = false;

    Location location;
    double latitude;
    double longitude;

    // number of results that GPSTracker gets for us
    int geocoderMaxResults = 1;

    // min distance for getting update
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // min time before update
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    protected LocationManager locationManager;
    private String provider_info;

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    //getting the location
    public void getLocation() {

        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            //getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            //getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled) {
                this.isGPSTrackingEnabled = true;
                //Log.d(TAG, "Application use GPS Service");
                provider_info = LocationManager.GPS_PROVIDER;

            } else if (isNetworkEnabled) { // if it fails, we try to get our location using network
                this.isGPSTrackingEnabled = true;
                //Log.d(TAG, "Application use Network State to get GPS coordinates");
                provider_info = LocationManager.NETWORK_PROVIDER;

            }

            if (ContextCompat.checkSelfPermission(ChooseLocationActivity.context,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
            }
            /*else {
            }*/

            if (!provider_info.isEmpty()) {
                locationManager.requestLocationUpdates(
                        provider_info,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        this
                );

                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(provider_info);
                    updateGPSCoordinates();
                }
            }
        }
        catch (Exception e)
        {
            Toast.makeText(ChooseLocationActivity.context, "Impossible to connect to LocationManager", Toast.LENGTH_LONG).show();
        }
    }

    //updating coordinates
    public void updateGPSCoordinates() {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    //getting coordinates

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        return longitude;
    }

    public boolean getIsGPSTrackingEnabled() {
        return this.isGPSTrackingEnabled;
    }

    public List<Address> getGeocoderAddress(Context context) {
        if (location != null) {

            Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);

            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, this.geocoderMaxResults);

                return addresses;
            } catch (IOException e) {
                Toast.makeText(context, "Impossible to connect to Geocoder", Toast.LENGTH_LONG).show();
                //Log.e(TAG, "Impossible to connect to Geocoder", e);
            }
        }
        return null;
    }

    public String getAddressLine(Context context) {
        List<Address> addresses = getGeocoderAddress(context);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String addressLine = address.getAddressLine(0);
            return addressLine;

        } else {
            return null;
        }
    }

    /*
    public String getLocality(Context context) {
        List<Address> addresses = getGeocoderAddress(context);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String locality = address.getLocality();
            return locality;
        }
        else {
            return null;
        }
    }*/

    /*public String getCountryName(Context context) {
        List<Address> addresses = getGeocoderAddress(context);
        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String countryName = address.getCountryName();

            return countryName;
        } else {
            return null;
        }
    }*/

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}