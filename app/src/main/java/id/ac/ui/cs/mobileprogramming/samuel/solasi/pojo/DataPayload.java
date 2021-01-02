package id.ac.ui.cs.mobileprogramming.samuel.solasi.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataPayload {

    @SerializedName("to")
    @Expose
    private String to;
    //    @SerializedName("collapse_key")
//    @Expose
//    private String collapseKey;
//    @SerializedName("notification")
//    @Expose
//    private Notification notification;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    //    public String getCollapseKey() {
//        return collapseKey;
//    }
//
//    public void setCollapseKey(String collapseKey) {
//        this.collapseKey = collapseKey;
//    }
//
//    public Notification getNotification() {
//        return notification;
//    }
//
//    public void setNotification(Notification notification) {
//        this.notification = notification;
//    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
