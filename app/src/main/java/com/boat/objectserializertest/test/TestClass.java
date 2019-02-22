package com.boat.objectserializertest.test;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by uchia on 2019/2/18.
 */

public class TestClass implements Parcelable {

    private String a;
    private boolean b;
    private int c;
    private float d;
    private int[] e;
    private TestClass(){}



    public TestClass(String a,boolean b,int c,float d){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public void setFillArray(int[] e){
        this.e = e;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TestClass))
            return false;
        TestClass testClass = (TestClass) o;
        return b == testClass.b &&
                c == testClass.c &&
                Float.compare(testClass.d, d) == 0 &&
                Objects.equals(a, testClass.a) &&
                Arrays.equals(e, testClass.e);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(a, b, c, d);
        result = 31 * result + Arrays.hashCode(e);
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.a);
        dest.writeByte(this.b ? (byte) 1 : (byte) 0);
        dest.writeInt(this.c);
        dest.writeFloat(this.d);
        dest.writeIntArray(this.e);
    }

    protected TestClass(Parcel in) {
        this.a = in.readString();
        this.b = in.readByte() != 0;
        this.c = in.readInt();
        this.d = in.readFloat();
        this.e = in.createIntArray();
    }

    public static final Creator<TestClass> CREATOR = new Creator<TestClass>() {
        @Override
        public TestClass createFromParcel(Parcel source) {
            return new TestClass(source);
        }

        @Override
        public TestClass[] newArray(int size) {
            return new TestClass[size];
        }
    };
}