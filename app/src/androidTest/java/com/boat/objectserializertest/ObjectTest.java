package com.boat.objectserializertest;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;

import com.boat.objectserializer.ObjectSerializer;
import com.boat.objectserializer.InstanceCreator;
import com.boat.objectserializertest.test.beans.D;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;

import static junit.framework.Assert.assertEquals;

/**
 * Created by uchia on 2019/2/18.
 */

@RunWith(AndroidJUnit4.class)
public class ObjectTest {

    private ObjectSerializer cabin;

    @Before
    public void init(){
        cabin = new ObjectSerializer();
        cabin.registerInstanceCreator(D.class, new InstanceCreator<D>() {
            @Override
            public D createInstance(Type type) {
                return new D(0);
            }
        });
    }

    @Test
    public void testCreateD(){
        D source = new D(434);
        Bundle bundle = cabin.toBundle(source);
        D result = cabin.fromBundle(bundle,D.class);
        assertEquals(source.getA(),result.getA());
    }

}
