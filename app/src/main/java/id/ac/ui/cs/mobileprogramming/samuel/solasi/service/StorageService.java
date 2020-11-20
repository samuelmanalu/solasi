package id.ac.ui.cs.mobileprogramming.samuel.solasi.service;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class StorageService {

    private FirebaseStorage storage;

    private static String statusImageFolder = "profile";

    public StorageService() {
        storage = FirebaseStorage.getInstance();
    }

    public UploadTask storeStatusImageFile(Uri uri, String fileName) {

        return storage.getReference().child(statusImageFolder).child(fileName).putFile(uri);
    }

    public Task<Uri> getDownloadedUrl(String fileName) {
        return storage.getReference().child(statusImageFolder).child(fileName).getDownloadUrl();
    }
}
