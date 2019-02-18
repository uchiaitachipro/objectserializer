package com.boat.objectserializertest.test.beans;

/**
 * Created by uchia on 2019/2/15.
 */

public class A {

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

}
