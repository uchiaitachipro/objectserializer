package com.boat.objectserializertest.test.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Created by uchia on 2019/2/15.
 */

public class A implements Parcelable {

    protected String first;
    protected String second;
    protected String third;

    public A(){}

    public A (String a,String b,String c){
        this.first = a;
        this.second = b;
        this.third = c;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof A))
            return false;
        A a = (A) o;
        return Objects.equals(getFirst(), a.getFirst()) &&
                Objects.equals(getSecond(), a.getSecond()) &&
                Objects.equals(getThird(), a.getThird());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getFirst(), getSecond(), getThird());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.first);
        dest.writeString(this.second);
        dest.writeString(this.third);
    }

    protected A(Parcel in) {
        this.first = in.readString();
        this.second = in.readString();
        this.third = in.readString();
    }

    public static final Creator<A> CREATOR = new Creator<A>() {
        @Override
        public A createFromParcel(Parcel source) {
            return new A(source);
        }

        @Override
        public A[] newArray(int size) {
            return new A[size];
        }
    };
}
