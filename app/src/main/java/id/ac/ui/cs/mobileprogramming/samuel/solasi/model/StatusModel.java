package id.ac.ui.cs.mobileprogramming.samuel.solasi.model;

import java.sql.Timestamp;

public class StatusModel {

    private Timestamp createdAt;

    private String description;

    private String uuid;

    private boolean isEdited;

    private boolean isImageExist;

    private String imageUrl;

    private int totalLiked;

    private String relatedStatus;

    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.sql.Timestamp createdAt) {
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
}
