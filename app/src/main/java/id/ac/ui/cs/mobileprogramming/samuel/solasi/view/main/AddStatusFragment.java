package id.ac.ui.cs.mobileprogramming.samuel.solasi.view.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;

import org.json.JSONException;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.R;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.AuthService;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.CloudMessagingService;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.LocationService;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.StatusService;

public class AddStatusFragment extends Fragment {

    private static final String TAG = "AddStatusFragment";

    private Button submit;

    private Switch isLocationShared;

    private boolean isPermissionGranted = false;

    private EditText status;

    private StatusService statusService;

    private AuthService authService;

    private ProgressBar progressBar;

    private LocationService locationService;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        locationService = new LocationService(getContext());
        statusService = new StatusService();
        authService = new AuthService();

        View view = inflater.inflate(R.layout.fragment_add_status, container, false);
        submit = view.findViewById(R.id.submit);
        status = view.findViewById(R.id.status);;
        isLocationShared = view.findViewById(R.id.locationSw);
        progressBar = view.findViewById(R.id.loading_add_status);

//        isLocationShared.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isLocationShared.isChecked()) {
//
//                }
//            }
//        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                progressBar.setVisibility(View.VISIBLE);
                Log.w(TAG, "Initial Condition");
                if (isLocationShared.isChecked() && locationService.getCurrentLocation() != null) {
                    Log.w(TAG, "First Condition");
                    locationService.getCurrentLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Log.w(TAG, "Successfully Get Location Condition");
                            saveStatus(locationService.geoEncoder(location));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Failed to Get Location Condition");
                            Log.w(TAG, e);
                        }
                    });
                } else if (locationService.getCurrentLocation() == null) {
                    Log.w(TAG, "Second Condition");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Log.w(TAG, "Second.One Condition");
                        requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 99);
                    }
                    saveStatus(null);
                } else {
                    Log.w(TAG, "Third Condition");
                    saveStatus(null);
                }
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 99:
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isPermissionGranted = true;
                locationService.getCurrentLocation();
            } else {
                isPermissionGranted = false;
                Toast.makeText(getContext(), "Share location has been disabled", Toast.LENGTH_SHORT);
            }
            break;
        }
    }

    public void saveStatus(String location) {
        statusService.saveStatus(status.getText().toString().trim(), authService.getUser(), location).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.status_posted), Toast.LENGTH_SHORT).show();
                try {
                    CloudMessagingService.sendNotification("New Status", authService.getUser().getDisplayName() + " uploaded new status!");
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}