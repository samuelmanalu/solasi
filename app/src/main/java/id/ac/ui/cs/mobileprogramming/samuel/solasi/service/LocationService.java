package id.ac.ui.cs.mobileprogramming.samuel.solasi.service;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class LocationService {

    private static final String TAG = "LocationService";

    private FusedLocationProviderClient fusedLocationProviderClient;

    private LocationRequest locationRequest;

    private Context context;

    private Geocoder geocoder;

    private LocationCallback locationCallback;

    public LocationService(Context context) {
        this.locationRequest = LocationRequest.create();
        this.locationRequest.setInterval(5000);
        this.locationRequest.setFastestInterval(5000);
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.context = context;
        this.geocoder = new Geocoder(context);
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult != null) {
                    Log.w(TAG, "LocationResult: " + locationResult.getLastLocation().toString());
                } else {
                    Log.w(TAG, "LocationResult is null");
                }
            }
        };
    }

    public Task<Location> getCurrentLocation() {
        Log.w(TAG, "Get Current Location Function");
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
            return fusedLocationProviderClient.getLastLocation();
        } else {
            return null;
        }
    }

    public void requestLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    public FusedLocationProviderClient getFusedLocationProviderClient() {
        return fusedLocationProviderClient;
    }

    public LocationCallback getLocationCallback() {
        return locationCallback;
    }

    public LocationRequest getLocationRequest() {
        return locationRequest;
    }

    public String geoEncoder(Location location) {
        String result = null;

        Log.w(TAG, "Geo Encoder Function");

        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            result = addresses.get(0).getLocality() + ", " + addresses.get(0).getCountryName();
        } catch (Exception e) {
            Log.w(TAG, "Failed to Encode geo location");
            Log.w(TAG, e);
        }

        return result;
    }
}
