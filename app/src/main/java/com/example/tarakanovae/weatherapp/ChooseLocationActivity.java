package com.example.tarakanovae.weatherapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.util.Log;

//that's the main activity
public class ChooseLocationActivity extends AppCompatActivity {

    public static String LOCATION_OF_CHOICE;
    static Context context;

    EditText locationEditText;
    CheckBox checkBox;
    static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_location_activity);

        locationEditText = (EditText) findViewById(R.id.locationEditText);
        context = this;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 225);
        activity = this;

        //for choosing between manual location entering and geocode
        checkBox = (CheckBox) findViewById(R.id.geolocation);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    locationEditText.setEnabled(false);
                } else {
                    locationEditText.setEnabled(true);
                }
            }
        });

        //calling the Activity that gets the forecast
        Button gotLocationButton = (Button) findViewById(R.id.got_location);
        gotLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //getting the location
                if (checkBox.isChecked()){
                    LOCATION_OF_CHOICE=GPS();
                }
                else{
                    LOCATION_OF_CHOICE = locationEditText.getText().toString();
                }

                Intent intent = new Intent(ChooseLocationActivity.this, RetrofitActivity.class);
                startActivity(intent);
            }
        });
    }

    //method for getting the location automatically
    public String GPS(){
        GPSTracker gpsTracker = new GPSTracker(this);
        String GPSLocation = "";
        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            Double lat = gpsTracker.latitude;
            Double lng = gpsTracker.longitude;

            Geocoder gcd = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = null;

            try {
                addresses = gcd.getFromLocation(lat, lng, 1);
                if (addresses.size() > 0)
                {
                    GPSLocation = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                Toast.makeText(context, "Problem defining location", Toast.LENGTH_LONG).show();
            }
        }
        return GPSLocation;
    }
}
