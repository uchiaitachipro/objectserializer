package com.boat.objectserializertest;

import android.graphics.Point;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;

import com.boat.objectserializer.ObjectSerializer;
import com.boat.objectserializertest.test.TestClass;
import com.boat.objectserializertest.test.beans.C;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

/**
 *
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
        assertArrayEquals(sourceArray, result);

    }

    @Test
    public void testBooleanArray() {
        boolean[] source = {true, false, true};
        Bundle bundle = cabin.toBundle(source);
        boolean[] result = cabin.fromBundle(bundle, boolean[].class);
        assertArrayEquals(source, result);
    }

    @Test
    public void testBooleanWrapperArray() {
        Boolean[] source = {false, true, false};
        Bundle bundle = cabin.toBundle(source);
        Boolean[] result = cabin.fromBundle(bundle, Boolean[].class);
        assertArrayEquals(source, result);
    }

    @Test
    public void testByteArray() {
        byte[] source = {1, 2, 3, 4, 5, -1, -2, -3};
        Bundle bundle = cabin.toBundle(source);
        byte[] result = cabin.fromBundle(bundle, byte[].class);
        assertArrayEquals(source, result);
    }

    @Test
    public void testByteWrapperArray() {
        Byte[] source = {82, 0, -2, -9};
        Bundle bundle = cabin.toBundle(source);
        Byte[] result = cabin.fromBundle(bundle, Byte[].class);
        assertArrayEquals(source, result);
    }

    @Test
    public void testShortArray() {
        short[] source = {758, 343, 22, -9402, 44};
        Bundle bundle = cabin.toBundle(source);
        short[] result = cabin.fromBundle(bundle, short[].class);
        assertArrayEquals(source, result);
    }

    @Test
    public void testShortWrapperArray() {
        Short[] source = {121, 556, 1222, Short.MAX_VALUE, Short.MIN_VALUE};
        Bundle bundle = cabin.toBundle(source);
        Short[] result = cabin.fromBundle(bundle, Short[].class);
        assertArrayEquals(source, result);
    }


    @Test
    public void testIntArray() {
        int[] source = {1, 2, 3};
        Bundle bundle = cabin.toBundle(source);
        int[] result = cabin.fromBundle(bundle, int[].class);
        assertArrayEquals(source, result);
    }

    @Test
    public void test2DIntArray() {
        int[][] source = {{475, 44, 22}, null, {88, 22}};
        Bundle bundle = cabin.toBundle(source);
        int[][] result = cabin.fromBundle(bundle, int[][].class);
        assertArrayEquals(source, result);
    }


    @Test
    public void testIntWrapperArray() {
        Integer[] source = {3, 4, 5, 6, 12, 333};
        Bundle bundle = cabin.toBundle(source);
        Integer[] result = cabin.fromBundle(bundle, Integer[].class);
        assertArrayEquals(source, result);
    }


    @Test
    public void test3DIntWrapperArray() {
        Integer[][][] source = {
                {{3223, 44, 55}, {882}, {9844, Integer.MIN_VALUE}},
                {{Integer.MAX_VALUE, 88831}, {1111}},
                {{444}, null},
                null,
                {{1, -39339, -44}}
        };
        Bundle bundle = cabin.toBundle(source);
        Integer[][][] result = cabin.fromBundle(bundle, Integer[][][].class);
        assertArrayEquals(source, result);
    }

    @Test
    public void testLongArray() {
        long[] source = {747834, 2323, 44, -23838, -930329, Long.MIN_VALUE, Long.MAX_VALUE};
        Bundle bundle = cabin.toBundle(source);
        long[] result = cabin.fromBundle(bundle, long[].class);
        assertArrayEquals(source, result);
    }

    @Test
    public void testLongWrapperArray() {
        Long[] source = {732287387L, -384347L, 3889289L};
        Bundle bundle = cabin.toBundle(source);
        Long[] result = cabin.fromBundle(bundle, Long[].class);
        assertArrayEquals(source, result);
    }

    @Test
    public void testFloatArray() {
        float[] source = {744.443F, 48.0002F, 38883.111111F, Float.MIN_VALUE, Float.MAX_VALUE};
        Bundle bundle = cabin.toBundle(source);
        float[] result = cabin.fromBundle(bundle, float[].class);
        assertArrayEquals(source, result, Float.MAX_VALUE);
    }

    @Test
    public void testFloatWrapperArray() {
        Float[] source = {6743.33F, -4343.7755F};
        Bundle bundle = cabin.toBundle(source);
        Float[] result = cabin.fromBundle(bundle, Float[].class);
        assertArrayEquals(source, result);
    }

    @Test
    public void testDoubleArray() {
        double[] source = {2332.223, 9494.111, -8883.111, Double.MIN_VALUE, Double.MAX_VALUE};
        Bundle bundle = cabin.toBundle(source);
        double[] result = cabin.fromBundle(bundle, double[].class);
        assertArrayEquals(source, result, Double.MAX_VALUE);
    }

    @Test
    public void testDoubleWrapperArray() {
        Double[] source = {90948.44, -2332.444};
        Bundle bundle = cabin.toBundle(source);
        Double[] result = cabin.fromBundle(bundle, Double[].class);
        assertArrayEquals(source, result);
    }

    @Test
    public void testCharArray() {
        char[] source = {'H', 'e', 'l', 'l', 'o'};
        Bundle bundle = cabin.toBundle(source);
        char[] result = cabin.fromBundle(bundle, char[].class);
        assertArrayEquals(source, result);
    }

    @Test
    public void testCharWrapperArray() {
        Character[] source = {'W', 'o', 'r', 'l', 'd', '!'};
        Bundle bundle = cabin.toBundle(source);
        Character[] result = cabin.fromBundle(bundle, Character[].class);
        assertArrayEquals(source, result);
    }

    @Test
    public void testCharSequenceArray() {
        CharSequence[] source = {"I", "am", "uchia"};
        Bundle bundle = cabin.toBundle(source);
        CharSequence[] result = cabin.fromBundle(bundle, CharSequence[].class);
        assertArrayEquals(source, result);
    }

    @Test
    public void test2DCharSequenceArray() {
        CharSequence[][] source = {{"come", "from", "NARUTO"}};
        Bundle bundle = cabin.toBundle(source);
        CharSequence[][] result = cabin.fromBundle(bundle, CharSequence[][].class);
        assertArrayEquals(source, result);
    }

    @Test
    public void testStringArray() {
        String[] source = {
                "《NARUTO》（日语：NARUTO -ナルト-）现在因为出版社改名为NARUTO火影忍者正式改名为NARUTO通常简称为“火影”",
                "为日本漫画家岸本齐史创作的少年漫画，",
                "讲述忍者世界里围绕着身为对手的漩涡鸣人和宇智波佐助两人，与其他角色的经历。"
        };
        Bundle bundle = cabin.toBundle(source);
        String[] result = cabin.fromBundle(bundle, String[].class);
        assertArrayEquals(source, result);
    }

    @Test
    public void testParcelableArray() {
        TestClass[] source = generateParcelableArray();
        Bundle bundle = cabin.toBundle(source);
        TestClass[] result = cabin.fromBundle(bundle, TestClass[].class);
        assertArrayEquals(source, result);
    }

    private static TestClass[] generateParcelableArray() {
        int count = 1000;
        TestClass[] data = new TestClass[count];
        for (int i = 0; i < count; i++) {
            TestClass d = new TestClass(i + "", count % 2 == 0, new Random().nextInt(), new Random().nextInt());
            int[] array = generateRandomArray(new Random().nextInt(500));
            d.setFillArray(array);
            data[i] = d;
        }
        return data;
    }

    private static int[] generateRandomArray(int count) {
        int[] array = new int[count];
        for (int i = 0; i < count; i++) {
            array[i] = new Random().nextInt();
        }
        return array;
    }
}
