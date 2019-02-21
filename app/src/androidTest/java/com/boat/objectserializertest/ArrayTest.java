package com.boat.objectserializertest;

import android.graphics.Point;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;

import com.boat.objectserializer.ObjectSerializer;
import com.boat.objectserializertest.test.beans.C;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by uchia on 2019/2/18.
 */

@RunWith(AndroidJUnit4.class)
public class ArrayTest {

    ObjectSerializer cabin;

    @Before
    public void init() {
        cabin = new ObjectSerializer();
    }

    @Test
    public void testObjectArray() {

        Object[] sourceArray = new Object[]{
                new Point(45, 66),
                new C("1", "2", "3", false, (byte) 4),
                6
        };

        Bundle bundle = cabin.toBundle(sourceArray);
        Object[] result = cabin.fromBundle(bundle, Object[].class);
        assertArrayEquals(sourceArray,result);

    }

}
