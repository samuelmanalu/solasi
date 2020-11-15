package id.ac.ui.cs.mobileprogramming.samuel.solasi.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class NotificationModel {

    @PrimaryKey()
    @NonNull
    private String  id;

    private String uidSender;

    private String uidReceiver;

    private boolean isLiked;

    private Date createdAt;

    private String relatedStatusId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUidSender() {
        return uidSender;
    }

    public void setUidSender(String uidSender) {
        this.uidSender = uidSender;
    }

    public String getUidReceiver() {
        return uidReceiver;
    }

    public void setUidReceiver(String uidReceiver) {
        this.uidReceiver = uidReceiver;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getRelatedStatusId() {
        return relatedStatusId;
    }

    public void setRelatedStatusId(String relatedStatusId) {
        this.relatedStatusId = relatedStatusId;
    }
}
