package id.ac.ui.cs.mobileprogramming.samuel.solasi.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.samuel.solasi.R;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.adapter.StatusAdapter;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.StatusModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.viewmodel.StatusViewModel;

public class StatusFragment extends Fragment {

    private static final String TAG = "StatusFragment";

    private StatusViewModel statusViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.status_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.status_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        final StatusAdapter adapter = new StatusAdapter(getActivity().getApplication());
        recyclerView.setAdapter(adapter);

        statusViewModel = ViewModelProviders.of(this).get(StatusViewModel.class);
        statusViewModel.getmStatusModel().observe(getViewLifecycleOwner(), new Observer<List<StatusModel>>() {
            @Override
            public void onChanged(List<StatusModel> statusModels) {
                adapter.setStatusModels(statusModels);
            }
        });
        return view;
    }
}
