package com.boat.objectserializertest.test.beans;

import java.util.Objects;

/**
 * Created by uchia on 2019/2/15.
 */

public class C extends A {

    private boolean forth;
    private byte fifth;

    public C(){
        super();
    }

    public C(String a,String b,String c ,boolean forth,byte fifth){
        super(a, b, c);
        this.forth = forth;
        this.fifth = fifth;
    }

    public boolean isForth() {
        return forth;
    }

    public void setForth(boolean forth) {
        this.forth = forth;
    }

    public byte getFifth() {
        return fifth;
    }

    public void setFifth(byte fifth) {
        this.fifth = fifth;
    }

    @Override
    public int hashCode() {
        return first.hashCode()
                + 23 * second.hashCode()
                + 57 * third.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof C)){
            return false;
        }

        if (this == obj){
            return true;
        }

        C that = (C) obj;

        return Objects.equals(first,that.first)
                && Objects.equals(second,that.second)
                && Objects.equals(third,that.third)
                && forth == that.forth
                && fifth == that.fifth;
    }
}
