package com.boat.objectserializer.bind;

import android.os.Bundle;
import android.os.Parcelable;

import com.boat.objectserializer.ElementRecord;
import com.boat.objectserializer.TypeAdapter;
import com.boat.objectserializer.utils.ArrayUtils;
import com.boat.objectserializer.utils.ClassHelper;

import java.util.ArrayList;

/**
 * Created by uchia on 2019/2/12.
 */

public abstract class AIDLSupportAdapter<T> extends TypeAdapter<T> {

    private static final String KEY = "$_ key";

    @Override
    public void write(Bundle out,String key, T value) {
        if (!ClassHelper.isAIDLSupportType(value)) {
            throw new IllegalArgumentException("current value is not aidl support; value = " + value);
        }

        Object object = value;

        boolean isWrapperType = ArrayUtils.isWrapperArray(value);
        if (isWrapperType) {
            object = ArrayUtils.toPrimitive(value);

            ElementRecord record = new ElementRecord(
                    ElementRecord.ELEMENT_AIDL_SUPPORT,
                    isWrapperType,
                    Integer.MIN_VALUE,
                    null);
            out.putParcelable(ElementRecord.META_DATA_KEY, record);
        }

        ClassHelper.convertAIDLSupportObject(out, key, object);
    }

    private static boolean checkWrapperArray(Bundle in) {
        ElementRecord record = in.getParcelable(ElementRecord.META_DATA_KEY);
        if (record == null || !record.isWrapperType()) {
            return false;
        }
        return true;
    }


    public static final class StringTypeAdapter extends AIDLSupportAdapter<String> {

        @Override
        public String read(Bundle in,String key) {
            return in.getString(key);
        }

        @Override
        public String getToken() {
            return String.class.getName();
        }
    }

    public static final class IntegerTypeAdapter extends AIDLSupportAdapter<Integer> {

        @Override
        public Integer read(Bundle in,String key) {
            return in.getInt(key);
        }

        @Override
        public String getToken() {
            return Integer.class.getName();
        }
    }

    public static final class LongTypeAdapter extends AIDLSupportAdapter<Long> {

        @Override
        public Long read(Bundle in,String key) {
            return in.getLong(key);
        }

        @Override
        public String getToken() {
            return Long.class.getName();
        }
    }

    public static final class DoubleTypeAdapter extends AIDLSupportAdapter<Double> {

        @Override
        public Double read(Bundle in,String key) {
            return in.getDouble(key);
        }

        @Override
        public String getToken() {
            return Double.class.getName();
        }
    }

    public static final class FloatTypeAdapter extends AIDLSupportAdapter<Float> {

        @Override
        public Float read(Bundle in,String key) {
            return in.getFloat(key);
        }

        @Override
        public String getToken() {
            return Float.class.toString();
        }
    }

    public static final class CharacterTypeAdapter extends AIDLSupportAdapter<Character> {

        @Override
        public Character read(Bundle in,String key) {
            return in.getChar(key);
        }

        @Override
        public String getToken() {
            return Character.class.getName();
        }
    }

    public static final class ShortTypeAdapter extends AIDLSupportAdapter<Short> {

        @Override
        public Short read(Bundle in,String key) {
            return in.getShort(key);
        }

        @Override
        public String getToken() {
            return Short.class.getName();
        }
    }

    public static final class BooleanTypeAdapter extends AIDLSupportAdapter<Boolean> {
        @Override
        public Boolean read(Bundle in,String key) {
            return in.getBoolean(key);
        }

        @Override
        public String getToken() {
            return Boolean.class.getName();
        }
    }

    public static final class ByteTypeAdapter extends AIDLSupportAdapter<Byte>{
        @Override
        public Byte read(Bundle in,String key) {
            return in.getByte(key);
        }

        @Override
        public String getToken() {
            return Byte.class.getName();
        }
    }

    public static final class ParcelableTypeAdapter extends AIDLSupportAdapter<Parcelable> {

        @Override
        public Parcelable read(Bundle in,String key) {
            in.setClassLoader(getClass().getClassLoader());
            return in.getParcelable(key);
        }

        @Override
        public String getToken() {
            return Parcelable.class.getName();
        }
    }

    public static final class CharSequenceTypeAdapter extends AIDLSupportAdapter<CharSequence> {

        @Override
        public CharSequence read(Bundle in,String key) {
            return in.getCharSequence(key);
        }

        @Override
        public String getToken() {
            return CharSequence.class.getName();
        }
    }

    public static final class BooleanArrayTypeAdapter extends AIDLSupportAdapter<boolean[]> {

        @Override
        public boolean[] read(Bundle in,String key) {
            return in.getBooleanArray(key);
        }

        @Override
        public String getToken() {
            return boolean[].class.getName();
        }
    }

    public static final class WrapperBooleanTypeAdapter extends AIDLSupportAdapter<Boolean[]> {

        @Override
        public Boolean[] read(Bundle in,String key) {
            boolean result = AIDLSupportAdapter.checkWrapperArray(in);

            if (!result) {
                return null;
            }
            boolean[] array = in.getBooleanArray(key);
            return ArrayUtils.toObject(array);
        }

        @Override
        public String getToken() {
            return Boolean[].class.getName();
        }
    }

    public static final class IntArrayTypeAdapter extends AIDLSupportAdapter<int[]> {

        @Override
        public int[] read(Bundle in,String key) {
            return in.getIntArray(key);
        }

        @Override
        public String getToken() {
            return int[].class.getName();
        }
    }

    public static final class WrapperIntArrayTypeAdapter extends AIDLSupportAdapter<Integer[]> {

        @Override
        public Integer[] read(Bundle in,String key) {
            boolean result = AIDLSupportAdapter.checkWrapperArray(in);

            if (!result) {
                return null;
            }
            int[] array = in.getIntArray(key);
            return ArrayUtils.toObject(array);
        }

        @Override
        public String getToken() {
            return Integer[].class.getName();
        }
    }

    public static final class CharArrayTypeAdapter extends AIDLSupportAdapter<char[]> {

        @Override
        public char[] read(Bundle in,String key) {
            return in.getCharArray(key);
        }

        @Override
        public String getToken() {
            return char[].class.getName();
        }
    }

    public static final class WrapperCharArrayTypeAdapter extends AIDLSupportAdapter<Character[]> {

        @Override
        public Character[] read(Bundle in,String key) {
            boolean result = AIDLSupportAdapter.checkWrapperArray(in);

            if (!result) {
                return null;
            }
            char[] array = in.getCharArray(key);
            return ArrayUtils.toObject(array);
        }

        @Override
        public String getToken() {
            return Character[].class.getName();
        }
    }

    public static final class ShortArrayTypeAdapter extends AIDLSupportAdapter<short[]> {

        @Override
        public short[] read(Bundle in,String key) {
            return in.getShortArray(key);
        }

        @Override
        public String getToken() {
            return short[].class.getName();
        }
    }

    public static final class WrapperShortArrayTypeAdapter extends AIDLSupportAdapter<Short[]> {

        @Override
        public Short[] read(Bundle in,String key) {
            boolean result = AIDLSupportAdapter.checkWrapperArray(in);

            if (!result) {
                return null;
            }
            short[] array = in.getShortArray(key);
            return ArrayUtils.toObject(array);
        }

        @Override
        public String getToken() {
            return Short[].class.getName();
        }
    }

    public static final class ByteArrayTypeAdapter extends AIDLSupportAdapter<byte[]> {

        @Override
        public byte[] read(Bundle in,String key) {
            return in.getByteArray(key);
        }

        @Override
        public String getToken() {
            return byte[].class.getName();
        }
    }

    public static final class WrapperByteArrayTypeAdapter extends AIDLSupportAdapter<Byte[]> {

        @Override
        public Byte[] read(Bundle in,String key) {
            boolean result = AIDLSupportAdapter.checkWrapperArray(in);

            if (!result) {
                return null;
            }
            byte[] array = in.getByteArray(key);
            return ArrayUtils.toObject(array);
        }

        @Override
        public String getToken() {
            return Byte[].class.getName();
        }
    }

    public static final class LongArrayTypeAdapter extends AIDLSupportAdapter<long[]> {

        @Override
        public long[] read(Bundle in,String key) {
            return in.getLongArray(key);
        }

        @Override
        public String getToken() {
            return long[].class.getName();
        }
    }

    public static final class WrapperLongArrayTypeAdapter extends AIDLSupportAdapter<Long[]> {

        @Override
        public Long[] read(Bundle in,String key) {
            boolean result = AIDLSupportAdapter.checkWrapperArray(in);

            if (!result) {
                return null;
            }
            long[] array = in.getLongArray(key);
            return ArrayUtils.toObject(array);
        }

        @Override
        public String getToken() {
            return Long[].class.getName();
        }
    }

    public static final class FloatArrayTypeAdapter extends AIDLSupportAdapter<float[]> {

        @Override
        public float[] read(Bundle in,String key) {
            return in.getFloatArray(key);
        }

        @Override
        public String getToken() {
            return float[].class.getName();
        }
    }

    public static final class WrapperFloatArrayTypeAdapter extends AIDLSupportAdapter<Float[]> {

        @Override
        public Float[] read(Bundle in,String key) {
            boolean result = AIDLSupportAdapter.checkWrapperArray(in);

            if (!result) {
                return null;
            }
            float[] array = in.getFloatArray(key);
            return ArrayUtils.toObject(array);
        }

        @Override
        public String getToken() {
            return Float[].class.getName();
        }
    }

    public static final class DoubleArrayTypeAdapter extends AIDLSupportAdapter<double[]> {

        @Override
        public double[] read(Bundle in,String key) {
            return in.getDoubleArray(key);
        }

        @Override
        public String getToken() {
            return double[].class.getName();
        }
    }

    public static final class WrapperDoubleArrayTypeAdapter extends AIDLSupportAdapter<Double[]> {

        @Override
        public Double[] read(Bundle in,String key) {
            boolean result = AIDLSupportAdapter.checkWrapperArray(in);

            if (!result) {
                return null;
            }
            double[] array = in.getDoubleArray(key);
            return ArrayUtils.toObject(array);
        }

        @Override
        public String getToken() {
            return Double[].class.getName();
        }
    }

    public static final class StringArrayTypeAdapter extends AIDLSupportAdapter<String[]>{

        @Override
        public String[] read(Bundle in,String key) {
            return in.getStringArray(key);
        }

        @Override
        public String getToken() {
            return String[].class.getName();
        }
    }

    public static final class CharSequenceArrayTypeAdapter extends AIDLSupportAdapter<CharSequence[]>{

        @Override
        public CharSequence[] read(Bundle in, String key) {
            return in.getCharSequenceArray(key);
        }

        @Override
        public String getToken() {
            return CharSequence[].class.getName();
        }
    }

    public static final class ParcelableArrayTypeAdapter extends AIDLSupportAdapter<Parcelable[]> {

        @Override
        public Parcelable[] read(Bundle in,String key) {
            in.setClassLoader(getClass().getClassLoader());
            return in.getParcelableArray(key);
        }

        @Override
        public String getToken() {
            return Parcelable[].class.getName();
        }
    }

    public static final class ListOfIntegerTypeAdapter
            extends AIDLSupportAdapter<ArrayList<Integer>> {

        @Override
        public void write(Bundle out, String key,ArrayList<Integer> value) {
            out.putIntegerArrayList(key, value);
        }

        @Override
        public ArrayList<Integer> read(Bundle in,String key) {
            return in.getIntegerArrayList(key);
        }

        @Override
        public String getToken() {
            return ListOfIntegerTypeAdapter.class.getName();
        }
    }

    public static final class ListOfStringTypeAdapter extends AIDLSupportAdapter<ArrayList<String>>{

        @Override
        public void write(Bundle out,String key, ArrayList<String> value) {
            out.putStringArrayList(key,value);
        }

        @Override
        public ArrayList<String> read(Bundle in,String key) {
            return in.getStringArrayList(key);
        }

        @Override
        public String getToken() {
            return ListOfStringTypeAdapter.class.getName();
        }
    }

    public static final class ListOfCharacterTypeAdapter
            extends AIDLSupportAdapter<ArrayList<CharSequence>>{
        @Override
        public void write(Bundle out,String key, ArrayList<CharSequence> value) {
            out.putCharSequenceArrayList(key,value);
        }

        @Override
        public ArrayList<CharSequence> read(Bundle in,String key) {
            return in.getCharSequenceArrayList(key);
        }

        @Override
        public String getToken() {
            return ListOfCharacterTypeAdapter.class.getName();
        }
    }

    public static final class ListOfParcelableTypeAdapter
            extends AIDLSupportAdapter<ArrayList<Parcelable>>{

        @Override
        public void write(Bundle out,String key, ArrayList<Parcelable> value) {
            out.putParcelableArrayList(key,value);
        }

        @Override
        public ArrayList<Parcelable> read(Bundle in,String key) {
            in.setClassLoader(getClass().getClassLoader());
            return in.getParcelableArrayList(key);
        }

        @Override
        public String getToken() {
            return ListOfParcelableTypeAdapter.class.getName();
        }
    }

}
