package com.somtodev.lifeflow.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class BloodRequest implements Parcelable {

    private String centerName;
    private String centerLocation;


    private String bloodGroup;
    private String packs;
    private String dueDate;

    private String urgent;
    private String status;

    public BloodRequest() {
    }

    public BloodRequest(String centerName, String centerLocation, String bloodGroup, String packs, String dueDate, String urgent, String status) {
        this.centerName = centerName;
        this.centerLocation = centerLocation;
        this.bloodGroup = bloodGroup;
        this.packs = packs;
        this.dueDate = dueDate;
        this.urgent = urgent;
        this.status = status;
    }

    protected BloodRequest(Parcel in) {
        centerName = in.readString();
        centerLocation = in.readString();
        bloodGroup = in.readString();
        packs = in.readString();
        dueDate = in.readString();
        urgent = in.readString();
        status = in.readString();
    }

    public static final Creator<BloodRequest> CREATOR = new Creator<BloodRequest>() {
        @Override
        public BloodRequest createFromParcel(Parcel in) {
            return new BloodRequest(in);
        }

        @Override
        public BloodRequest[] newArray(int size) {
            return new BloodRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(centerName);
        dest.writeString(centerLocation);
        dest.writeString(bloodGroup);
        dest.writeString(packs);
        dest.writeString(dueDate);
        dest.writeString(urgent);
        dest.writeString(status);
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCenterLocation() {
        return centerLocation;
    }

    public void setCenterLocation(String centerLocation) {
        this.centerLocation = centerLocation;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPacks() {
        return packs;
    }

    public void setBloodPacks(String packs) {
        this.packs = packs;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String isUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
