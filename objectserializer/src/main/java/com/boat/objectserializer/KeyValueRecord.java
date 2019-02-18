package com.boat.objectserializer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by uchia on 2019/2/13.
 */

public final class KeyValueRecord implements Parcelable{


    private final Parcelable key;
    private final Parcelable value;

    public KeyValueRecord(Parcelable key,Parcelable value){
        this.key = key;
        this.value = value;
    }

    protected KeyValueRecord(Parcel in) {
        key = in.readParcelable(getClass().getClassLoader());
        value = in.readParcelable(getClass().getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(key,0);
        dest.writeParcelable(value,0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<KeyValueRecord> CREATOR = new Creator<KeyValueRecord>() {
        @Override
        public KeyValueRecord createFromParcel(Parcel in) {
            return new KeyValueRecord(in);
        }

        @Override
        public KeyValueRecord[] newArray(int size) {
            return new KeyValueRecord[size];
        }
    };

    public Parcelable getKey() {
        return key;
    }

    public Parcelable getValue() {
        return value;
    }

}
