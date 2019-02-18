package com.boat.objectserializertest.test.beans;

/**
 * Created by uchia on 2019/2/15.
 */

public class B extends A {

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
}
