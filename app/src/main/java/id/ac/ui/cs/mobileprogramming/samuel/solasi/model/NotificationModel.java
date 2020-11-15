package id.ac.ui.cs.mobileprogramming.samuel.solasi.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class NotificationModel {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String uidSender;

    private String uidReceiver;

    private String action;

    private Date createdAt;

    private String relatedStatusId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
