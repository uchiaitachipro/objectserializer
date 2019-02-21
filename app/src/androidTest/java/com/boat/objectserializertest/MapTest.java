package com.boat.objectserializertest;

import android.graphics.Point;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;
import android.util.ArrayMap;

import com.boat.objectserializer.ObjectSerializer;
import com.boat.objectserializer.LinkedTreeMap;
import com.boat.objectserializer.TypeToken;
import com.boat.objectserializertest.test.TestClass;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static junit.framework.Assert.assertEquals;

/**
 * Created by uchia on 2019/2/18.
 */

@RunWith(AndroidJUnit4.class)
public class MapTest {

    private ObjectSerializer cabin;

    @Before
    public void init(){
        cabin = new ObjectSerializer();
    }

    @Test
    public  void testSimpleKeyValue(){
        Map<String, String> sourceHashMap = new HashMap<>();
        fillValue(sourceHashMap);
        Bundle bundle = cabin.toBundle(sourceHashMap);
        Map<String, String> resultHashMap = cabin.fromBundle(
                bundle,
                new TypeToken<Map<String, String>>() {
                }.getType());

        assertEquals(sourceHashMap,resultHashMap);


        LinkedHashMap<String, String> sourceLinkedHashMap = new LinkedHashMap<>();
        fillValue(sourceLinkedHashMap);
        bundle = cabin.toBundle(sourceLinkedHashMap);
        LinkedHashMap<String, String> resultLinkedHashMap = cabin.fromBundle(
                bundle,
                new TypeToken<LinkedHashMap<String, String>>() {
                }.getType());

        assertEquals(sourceLinkedHashMap,resultLinkedHashMap);

        ArrayMap<String, String> sourceArrayMap = new ArrayMap<>();
        fillValue(sourceArrayMap);
        bundle = cabin.toBundle(sourceArrayMap);
        ArrayMap<String, String> resultArrayMap = cabin.fromBundle(
                bundle,
                new TypeToken<ArrayMap<String, String>>() {
                }.getType());

        assertEquals(sourceArrayMap,resultArrayMap);

        LinkedTreeMap<String, String> sourceLinkTreeMap = new LinkedTreeMap<>();
        fillValue(sourceLinkTreeMap);
        bundle = cabin.toBundle(sourceLinkedHashMap);
        LinkedTreeMap<String, String> resultLinkTreeMap = cabin.fromBundle(
                bundle,
                new TypeToken<LinkedTreeMap<String, String>>() {
                }.getType());
        assertEquals(sourceLinkTreeMap,resultLinkTreeMap);

        ConcurrentHashMap<String, String> sourceConcurrentHashMap = new ConcurrentHashMap<>();
        fillValue(sourceConcurrentHashMap);
        bundle = cabin.toBundle(sourceConcurrentHashMap);
        ConcurrentHashMap<String, String> resultConcurrentMap = cabin.fromBundle(
                bundle,
                new TypeToken<ConcurrentHashMap<String, String>>() {
                }.getType());

        assertEquals(sourceConcurrentHashMap,resultConcurrentMap);
    }

    @Test
    public void testObjectKeyValue(){
        Map<TestClass, Object> sourceComplexKey = new HashMap<>();

        sourceComplexKey.put(
                new TestClass("sdsd",false,323,23.43344F),
                new Point(32,44));
        sourceComplexKey.put(
                new TestClass("ghhjfdfdf",true,562,0.43F),
                "dsdfvgewdscdvnghfdejskacmxnvhdfjskamxdcnvfhj");

        Type type = new TypeToken<HashMap<TestClass, Object>>(){}.getType();
        Bundle bundle = cabin.toBundle(sourceComplexKey,type);
        Map<TestClass, Object> resultComplexKey = cabin.fromBundle(
                bundle,type);
        assertEquals(sourceComplexKey,resultComplexKey);
    }


    private static void fillValue(Map<String, String> map) {
        map.put("ass", "dsfg");
        map.put("dsds", "dsf");
        map.put("dffd", "sdafkbvkdsjbh lfhbkbhvck");
        map.put("dsyeruyeur", "hjgadhcjhkjasllkj");
    }

}
