package id.ac.ui.cs.mobileprogramming.samuel.solasi.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity
public class StatusModel {

    @PrimaryKey
    @NonNull
    private String id;

    private Date createdAt;

    private String description;

    private String uuid;

    private boolean isEdited;

    private boolean isImageExist;

    private String imageUrl;

    private int totalLiked;

    private String relatedStatus;

    private String location;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setIsEdited(boolean isEdited) {
        this.isEdited = isEdited;
    }

    public boolean isImageExist() {
        return isImageExist;
    }

    public void setIsImageExist(boolean isImageExist) {
        this.isImageExist = isImageExist;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getTotalLiked() {
        return totalLiked;
    }

    public void setTotalLiked(int totalLiked) {
        this.totalLiked = totalLiked;
    }

    public String getRelatedStatus() {
        return relatedStatus;
    }

    public void setRelatedStatus(String relatedStatus) {
        this.relatedStatus = relatedStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
