package com.boat.objectserializertest.test.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Created by uchia on 2019/2/15.
 */

public class B extends A implements Parcelable {

    private int fourth;
    private double fifth;


    public B(){
        super();
    }

    public B (String a,String b,String c,int forth,double fifth){
        super(a,b,c);
        this.fourth = forth;
        this.fifth = fifth;
    }

    public int getForth() {
        return fourth;
    }

    public void setForth(int fourth) {
        this.fourth = fourth;
    }

    public double getFifth() {
        return fifth;
    }

    public void setFifth(double fifth) {
        this.fifth = fifth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof B))
            return false;
        B b = (B) o;
        return fourth == b.fourth &&
                Double.compare(b.getFifth(), getFifth()) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(fourth, getFifth());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.fourth);
        dest.writeDouble(this.fifth);
    }

    protected B(Parcel in) {
        this.fourth = in.readInt();
        this.fifth = in.readDouble();
    }

    public static final Creator<B> CREATOR = new Creator<B>() {
        @Override
        public B createFromParcel(Parcel source) {
            return new B(source);
        }

        @Override
        public B[] newArray(int size) {
            return new B[size];
        }
    };
}
