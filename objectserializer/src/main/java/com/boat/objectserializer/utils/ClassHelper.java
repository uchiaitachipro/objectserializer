package com.boat.objectserializer.utils;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by uchia on 2018/10/14.
 */

public class ClassHelper {

    private static final String WRAPPER_TYPE = "1_$_w_t";

    /**
     * Maps a primitive class name to its corresponding abbreviation used in array class names.
     */
    private static final Map<String, Class> abbreviationMap;

    static {
        final Map<String, Class> m = new HashMap<>();
        m.put("int", int.class);
        m.put("boolean", boolean.class);
        m.put("float", float.class);
        m.put("long", long.class);
        m.put("short", short.class);
        m.put("byte", byte.class);
        m.put("double", double.class);
        m.put("char", char.class);
        abbreviationMap = Collections.unmodifiableMap(m);
    }


    private ClassHelper() {
    }

    public static Class forName(String className) {

        if (TextUtils.isEmpty(className)) {
            return null;
        }

        Class clazz = abbreviationMap.get(className);
        if (clazz != null) {
            return clazz;
        }
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isAIDLSupportType(Object object) {
        if (object instanceof String ||
                object instanceof Integer ||
                object instanceof Byte ||
                object instanceof Long ||
                object instanceof Double ||
                object instanceof Float ||
                object instanceof Character ||
                object instanceof CharSequence ||
                object instanceof Short ||
                object instanceof Boolean ||
                object instanceof Parcelable) {
            return true;
        }

        if (object instanceof String[] ||
                object instanceof int[] ||
                object instanceof byte[] ||
                object instanceof long[] ||
                object instanceof double[] ||
                object instanceof float[] ||
                object instanceof char[] ||
                object instanceof CharSequence[] ||
                object instanceof short[] ||
                object instanceof boolean[] ||
                object instanceof Parcelable[]) {
            return true;
        }

        if (ArrayUtils.isWrapperArray(object)) {
            return true;
        }

        if (object instanceof ArrayList) {

            ArrayList list = (ArrayList) object;
            if (list == null || list.isEmpty()) {
                return true;
            }

            Object element = list.get(0);
            if (element instanceof String ||
                    element instanceof Integer ||
                    element instanceof Parcelable ||
                    element instanceof CharSequence) {
                return true;
            }

        }

        return false;
    }

    public static String tryGetWrapperType(Bundle bundle) {
        return bundle.getString(WRAPPER_TYPE);
    }

    public static Bundle convertFrom(String eventTag, Object object) {
        Bundle bundle = new Bundle();
        return convertFrom(bundle, eventTag, object);
    }

    public static Bundle convertAIDLSupportObject(Bundle bundle, String key, Object object) {

        Class className = object.getClass();

        if (className.equals(String.class)) {
            bundle.putString(key, (String) object);
        } else if (className.equals(Integer.class)) {
            bundle.putInt(key, (int) object);
        } else if (className.equals(Byte.class)) {
            bundle.putByte(key, (byte) object);
        } else if (className.equals(Long.class)) {
            bundle.putLong(key, (long) object);
        } else if (className.equals(Double.class)) {
            bundle.putDouble(key, (double) object);
        } else if (className.equals(Float.class)) {
            bundle.putFloat(key, (float) object);
        } else if (className.equals(Character.class)) {
            bundle.putChar(key, (char) object);
        } else if (className.equals(Short.class)) {
            bundle.putShort(key, (short) object);
        } else if (className.equals(Boolean.class)) {
            bundle.putBoolean(key, (boolean) object);
        } else if (object instanceof Parcelable) {
            bundle.putParcelable(key, (Parcelable) object);
        } else if (object instanceof CharSequence) {
            bundle.putCharSequence(key, (CharSequence) object);
        } else if (object instanceof boolean[]) {
            bundle.putBooleanArray(key, (boolean[]) object);
        } else if (object instanceof int[]) {
            bundle.putIntArray(key, (int[]) object);
        } else if (object instanceof char[]) {
            bundle.putCharArray(key, (char[]) object);
        } else if (object instanceof CharSequence[]) {
            bundle.putCharSequenceArray(key, (CharSequence[]) object);
        } else if (object instanceof short[]) {
            bundle.putShortArray(key, (short[]) object);
        } else if (object instanceof byte[]) {
            bundle.putByteArray(key, (byte[]) object);
        } else if (object instanceof long[]) {
            bundle.putLongArray(key, (long[]) object);
        } else if (object instanceof float[]) {
            bundle.putFloatArray(key, (float[]) object);
        } else if (object instanceof double[]) {
            bundle.putDoubleArray(key, (double[]) object);
        } else if (object instanceof String[]) {
            bundle.putStringArray(key, (String[]) object);
        } else if (object instanceof Parcelable[]) {
            bundle.putParcelableArray(key, (Parcelable[]) object);
        } else if (object instanceof ArrayList) {
            ArrayList list = (ArrayList) object;
            if (list != null && !list.isEmpty()) {
                Object element = list.get(0);
                if (element instanceof Integer) {
                    bundle.putIntegerArrayList(key, list);
                } else if (element instanceof String) {
                    bundle.putStringArrayList(key, list);
                } else if (element instanceof Character) {
                    bundle.putCharSequenceArrayList(key, list);
                } else if (element instanceof Parcelable) {
                    bundle.putParcelableArrayList(key, list);
                }
            }
        }
        return bundle;
    }

    public static Bundle convertFrom(Bundle bundle, String eventTag, Object object) {

        if (ArrayUtils.isWrapperArray(object)) {
            bundle.putString(WRAPPER_TYPE, object.getClass().getName());
            object = ArrayUtils.toPrimitive(object);
        }

        if (isAIDLSupportType(object)) {
            convertAIDLSupportObject(bundle, eventTag, object);
        } else {
            throw new IllegalArgumentException("param's type not support");
        }

        return bundle;
    }
}
