package com.somtodev.lifeflow.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;

public class BloodRequest implements Parcelable {
    private String centerName;
    private String centerLocation;
    private int bloodPacks;
    private LocalDateTime  dueDate;
    private boolean urgent;
    private String status;

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

    public int getBloodPacks() {
        return bloodPacks;
    }

    public void setBloodPacks(int bloodPacks) {
        this.bloodPacks = bloodPacks;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    protected BloodRequest(Parcel in) {
        centerName = in.readString();
        centerLocation = in.readString();
        bloodPacks = in.readInt();
        urgent = in.readByte() != 0;
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
        dest.writeInt(bloodPacks);
        dest.writeByte((byte) (urgent ? 1 : 0));
        dest.writeString(status);
    }
}
