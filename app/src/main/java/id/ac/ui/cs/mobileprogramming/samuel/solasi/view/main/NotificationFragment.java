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
import id.ac.ui.cs.mobileprogramming.samuel.solasi.adapter.NotificationAdapter;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.model.NotificationModel;
import id.ac.ui.cs.mobileprogramming.samuel.solasi.viewmodel.NotificationViewModel;

public class NotificationFragment extends Fragment {

    private static final String TAG = "NotificationFragment";

    private NotificationViewModel notificationViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.notification_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.notification_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        final NotificationAdapter adapter = new NotificationAdapter(getActivity().getApplication());
        recyclerView.setAdapter(adapter);

        notificationViewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);
        notificationViewModel.getNotifications().observe(getViewLifecycleOwner(), new Observer<List<NotificationModel>>() {
            @Override
            public void onChanged(List<NotificationModel> notificationModels) {
                adapter.setNotifications(notificationModels);
            }
        });
        return view;
    }
}
