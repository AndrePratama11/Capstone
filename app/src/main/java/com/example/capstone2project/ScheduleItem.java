package com.example.capstone2project;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.FirebaseDatabase;

public class ScheduleItem implements Parcelable {
    private String key;
    private String judul;
    private String tanggal;
    private String waktu;
    private Uri soundUri;

    // Konstruktor, setter, getter, dll.

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public ScheduleItem(String judul, String tanggal, String waktu) {
        this.key = null;
        this.judul = judul;
        this.tanggal = tanggal;
        this.waktu = waktu;
    }

    public Uri getSoundUri() {
        return soundUri;
    }

    public void setSoundUri(Uri soundUri) {
        this.soundUri = soundUri;
    }

    public ScheduleItem() {
        // Biarkan kosong atau atur nilai default sesuai kebutuhan
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }



    // Implementasi Parcelable
    protected ScheduleItem(Parcel in) {
        key = in.readString(); // Read key from parcel
        judul = in.readString();
        tanggal = in.readString();
        waktu = in.readString();

    }

    public static final Creator<ScheduleItem> CREATOR = new Creator<ScheduleItem>() {
        @Override
        public ScheduleItem createFromParcel(Parcel in) {
            return new ScheduleItem(in);
        }

        @Override
        public ScheduleItem[] newArray(int size) {
            return new ScheduleItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key); // Write key to parcel
        dest.writeString(judul);
        dest.writeString(tanggal);
        dest.writeString(waktu);

    }

    @Override
    public String toString() {
        return judul;
    }


}
