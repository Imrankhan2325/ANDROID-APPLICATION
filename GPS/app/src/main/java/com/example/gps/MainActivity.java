package com.example.gps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    TextView tvLatitude, tvLongitude;
    Button btnLocation;

    LocationManager locationManager;

    private static final int LOCATION_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        btnLocation = findViewById(R.id.btnLocation);

        locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        btnLocation.setOnClickListener(v -> checkLocationPermission());
    }

    private void checkLocationPermission() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    LOCATION_PERMISSION_CODE
            );

        } else {

            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {

        boolean gpsEnabled =
                locationManager.isProviderEnabled(
                        LocationManager.GPS_PROVIDER);

        boolean networkEnabled =
                locationManager.isProviderEnabled(
                        LocationManager.NETWORK_PROVIDER);

        if (!gpsEnabled && !networkEnabled) {

            Toast.makeText(
                    this,
                    "Please Turn ON Location",
                    Toast.LENGTH_LONG
            ).show();

            Intent intent =
                    new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

            startActivity(intent);

            return;
        }

        tvLatitude.setText("Latitude: Getting location...");
        tvLongitude.setText("Longitude: Please wait...");

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        if (gpsEnabled) {

            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000,
                    1,
                    locationListener
            );
        }

        if (networkEnabled) {

            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    1000,
                    1,
                    locationListener
            );
        }
    }

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(@NonNull Location location) {

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            tvLatitude.setText("Latitude: " + latitude);
            tvLongitude.setText("Longitude: " + longitude);

            Toast.makeText(
                    MainActivity.this,
                    "Location Found Successfully",
                    Toast.LENGTH_SHORT
            ).show();

            locationManager.removeUpdates(this);
        }
    };

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );

        if (requestCode == LOCATION_PERMISSION_CODE) {

            if (grantResults.length > 0
                    && (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    || (grantResults.length > 1
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED))) {

                getCurrentLocation();

            } else {

                Toast.makeText(
                        this,
                        "Location Permission Denied",
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}
