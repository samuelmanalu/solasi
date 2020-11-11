package id.ac.ui.cs.mobileprogramming.samuel.solasi.service;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class StorageService {

    private FirebaseStorage storage;

    private static String statusImageFolder = "image";

    public StorageService() {
        storage = FirebaseStorage.getInstance();
    }

    public UploadTask storeStatusImageFile(Uri uri) {
        return storage.getReference().child(statusImageFolder).putFile(uri);
    }
}
