package id.ac.ui.cs.mobileprogramming.samuel.solasi.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserModel {

    @PrimaryKey
    @NonNull
    private String uid;

    private String localPhotoUrl;

    private String photoUrl;

    private String displayName;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLocalPhotoUrl() {
        return localPhotoUrl;
    }

    public void setLocalPhotoUrl(String localPhotoUrl) {
        this.localPhotoUrl = localPhotoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
