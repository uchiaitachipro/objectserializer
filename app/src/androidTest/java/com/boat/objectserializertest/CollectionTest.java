package com.boat.objectserializertest;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;

import com.boat.objectserializer.ObjectSerializer;
import com.boat.objectserializer.TypeToken;
import com.boat.objectserializertest.test.beans.A;
import com.boat.objectserializertest.test.beans.B;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;

/**
 * @ 创建者 qinf
 */

@RunWith(AndroidJUnit4.class)
public class CollectionTest {

    ObjectSerializer cabin = new ObjectSerializer();

    @Test
    public void testArrayList(){
        List<Integer> source = new ArrayList<>();
        Type arrayListType = new TypeToken<List<Integer>>(){}.getType();
        source.add(7823);
        source.add(389);
        source.add(-8459);
        source.add(Integer.MAX_VALUE);
        source.add(Integer.MIN_VALUE);
        Bundle bundle = cabin.toBundle(source,arrayListType);
        List<Integer> result = cabin.fromBundle(bundle,arrayListType);
        Assert.assertEquals(source,result);

    }


    @Test
    public void testLinkedList(){
        List<B> source = new LinkedList<>();
        source.add(new B("a","b","c",3444,8493.545));
        source.add(new B("d","e","f",8373,0.453234543));
        source.add(new B("g","h","k",2345,-84398.43343));
        List<? extends A> sourceAList = source;
        Type aListType = new TypeToken<LinkedList<? extends A>>(){}.getType();
        Bundle bundle = cabin.toBundle(sourceAList,aListType);
        List<? extends A> resultList = cabin.fromBundle(bundle,aListType);
        Assert.assertEquals(sourceAList,resultList);
    }

    @Test
    public void testQueue(){
        Queue<UUID> sourceQueue = new LinkedList<>();
        sourceQueue.add(UUID.randomUUID());
        sourceQueue.add(UUID.randomUUID());
        sourceQueue.add(UUID.randomUUID());

        Type type = new TypeToken<LinkedList<UUID>>(){}.getType();
        Bundle bundle = cabin.toBundle(sourceQueue,type);
        Queue<UUID> resultQueue = cabin.fromBundle(bundle,type);
        Assert.assertEquals(sourceQueue,resultQueue);
    }

    @Test
    public void testSet(){
        Set<UUID> sourceSet = new HashSet<>();
        sourceSet.add(UUID.randomUUID());
        sourceSet.add(UUID.randomUUID());
        sourceSet.add(UUID.randomUUID());
        Type type = new TypeToken<HashSet<UUID>>(){}.getType();
        Bundle bundle = cabin.toBundle(sourceSet,type);
        Set<UUID> resultSet = cabin.fromBundle(bundle,type);
        Assert.assertEquals(sourceSet,resultSet);
    }


    @Test
    public void testStack(){
        Stack<int[]> sourceStack = new Stack<>();
        sourceStack.add(new int[]{4839,233,444});
        sourceStack.add(new int[]{9039,-43,3434});
        sourceStack.add(new int[]{7483,-384,-4848});
        Type type = new TypeToken<Stack<int[]>>(){}.getType();
        Bundle bundle = cabin.toBundle(sourceStack,type);
        Stack<int[]> resultStack = cabin.fromBundle(bundle,type);
        Assert.assertEquals(sourceStack,resultStack);
    }


}
