package com.boat.objectserializertest.test;

/**
 * Created by uchia on 2019/2/18.
 */

public class TestClass{

    private String a;
    private boolean b;
    private int c;
    private float d;

    private TestClass(){}

    public TestClass(String a,boolean b,int c,float d){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public int hashCode() {
        return a.hashCode() + c * 17 + (int)(d * 33);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj){
            return true;
        }

        if (!(obj instanceof TestClass)){
            return false;
        }

        TestClass that = (TestClass) obj;

        return a.equals(that.a) && b == that.b && c == that.c && d == that.d;
    }
}