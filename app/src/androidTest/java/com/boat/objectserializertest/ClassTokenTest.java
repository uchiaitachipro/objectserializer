package com.boat.objectserializertest;

import android.content.ContextWrapper;
import android.graphics.Point;
import android.support.test.runner.AndroidJUnit4;

import com.boat.objectserializer.$Object$Types;
import com.boat.objectserializer.TypeToken;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;

import static junit.framework.Assert.assertEquals;

/**
 * Created by uchia on 2019/2/18.
 */

@RunWith(AndroidJUnit4.class)
public class ClassTokenTest {


    @Test()
    public void testPrimitiveType() {
        Type type;
        type = $Object$Types.resumeTypeFromToken(int.class.getName());
        assertEquals(int.class, type);
    }

    @Test
    public void testArray(){
        Type type;
        type = $Object$Types.resumeTypeFromToken(int[].class.getName());
        assertEquals(type,int[].class);

        type = $Object$Types.resumeTypeFromToken(Point[].class.getName());
        assertEquals(type,Point[].class);

        type = $Object$Types.resumeTypeFromToken(Class[].class.getName());
        assertEquals(type,Class[].class);

        type = $Object$Types.resumeTypeFromToken(String[].class.getName());
        assertEquals(type,String[].class);
    }

    @Test
    public void testParameterizedType(){
        Type sourceType;
        sourceType = new TypeToken<List<String>>(){}.getType();
        Type resultType = $Object$Types.resumeTypeFromToken(sourceType.toString());
        assertEquals(sourceType,resultType);

        sourceType = new TypeToken<Map<String,? extends ContextWrapper>>(){}.getType();
        resultType = $Object$Types.resumeTypeFromToken(sourceType.toString());
        assertEquals(sourceType,resultType);

        sourceType = new TypeToken<Map<String,? extends List<? super String>>>(){}.getType();
        resultType = $Object$Types.resumeTypeFromToken(sourceType.toString());
        assertEquals(sourceType,resultType);

        sourceType = new TypeToken<List<int[]>>(){}.getType();
        resultType = $Object$Types.resumeTypeFromToken(sourceType.toString());
        assertEquals(sourceType,resultType);
    }
}
