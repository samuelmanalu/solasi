package id.ac.ui.cs.mobileprogramming.samuel.solasi.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.R;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.AuthService;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.service.StatusService;

public class AddStatusFragment extends Fragment {

    private Button submit;

    private EditText status;

    private StatusService statusService;

    private AuthService authService;

    private ProgressBar progressBar;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        statusService = new StatusService();
        authService = new AuthService();

        View view = inflater.inflate(R.layout.fragment_add_status, container, false);
        submit = view.findViewById(R.id.submit);
        status = view.findViewById(R.id.status);
        progressBar = view.findViewById(R.id.loading_add_status);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                progressBar.setVisibility(View.VISIBLE);
                statusService.saveStatus(status.getText().toString().trim(), authService.getUser()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.status_posted), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}