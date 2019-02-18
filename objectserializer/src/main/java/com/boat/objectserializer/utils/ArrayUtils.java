package com.boat.objectserializer.utils;

/**
 * Created by uchia on 2018/12/3.
 */

public class ArrayUtils {

    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];

    public static final short[] EMPTY_SHORT_ARRAY = new short[0];

    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];

    public static final int[] EMPTY_INT_ARRAY = new int[0];

    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];

    public static final long[] EMPTY_LONG_ARRAY = new long[0];

    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];

    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];

    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];

    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];

    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];

    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];

    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];

    public static final char[] EMPTY_CHAR_ARRAY = new char[0];

    public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

    private ArrayUtils(){}

    public static byte[] toPrimitive(final Byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        final byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    public static Byte[] toObject(final byte[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        }
        final Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Byte.valueOf(array[i]);
        }
        return result;
    }

    public static short[] toPrimitive(final Short[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        final short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    public static Short[] toObject(final short[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_SHORT_OBJECT_ARRAY;
        }
        final Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Short.valueOf(array[i]);
        }
        return result;
    }

    public static int[] toPrimitive(final Integer[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        final int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    public static Integer[] toObject(final int[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        }
        final Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.valueOf(array[i]);
        }
        return result;
    }

    public static long[] toPrimitive(final Long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        final long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    public static Long[] toObject(final long[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_LONG_OBJECT_ARRAY;
        }
        final Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Long.valueOf(array[i]);
        }
        return result;
    }

    public static Float[] toObject(final float[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_OBJECT_ARRAY;
        }
        final Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Float.valueOf(array[i]);
        }
        return result;
    }

    public static float[] toPrimitive(final Float[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        final float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    public static double[] toPrimitive(final Double[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        final double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    public static Double[] toObject(final double[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        final Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Double.valueOf(array[i]);
        }
        return result;
    }

    public static char[] toPrimitive(final Character[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        final char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].charValue();
        }
        return result;
    }

    public static Character[] toObject(final char[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CHARACTER_OBJECT_ARRAY;
        }
        final Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Character.valueOf(array[i]);
        }
        return result;
    }

    public static boolean[] toPrimitive(final Boolean[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        final boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].booleanValue();
        }
        return result;
    }

    public static Boolean[] toObject(final boolean[] array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        final Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (array[i] ? Boolean.TRUE : Boolean.FALSE);
        }
        return result;
    }

    public static boolean isWrapperArray(Object object){
        if (object instanceof Byte[] ||
                object instanceof Short[] ||
                object instanceof Integer[] ||
                object instanceof Long[] ||
                object instanceof Float[] ||
                object instanceof Double[] ||
                object instanceof Boolean[] ||
                object instanceof Character[] ){
            return true;
        }
        return false;
    }

    public static Object toPrimitive(Object object){

        if (object instanceof Byte[]){
            return toPrimitive((Byte[]) object);
        } else if (object instanceof Short[]){
            return toPrimitive((Short[]) object);
        } else if (object instanceof Integer[]){
            return toPrimitive((Integer[]) object);
        } else if (object instanceof Long[]){
            return toPrimitive((Long[]) object);
        } else if (object instanceof Float[]){
            return toPrimitive((Float[]) object);
        } else if (object instanceof Double[]){
            return toPrimitive((Double[]) object);
        } else if (object instanceof Boolean[]){
            return toPrimitive((Boolean[]) object);
        } else if (object instanceof Character[]){
            return toPrimitive((Character[]) object);
        }
        return object;
    }

    public static Object toObject(Object object, String type){

        if (object == null){
            return object;
        }

        try {
            Class clazz = Class.forName(type);
            if (Byte[].class.equals(clazz) && object instanceof byte[]){
                return toObject((byte[]) object);
            } else if (Short[].class.equals(clazz) && object instanceof short[]){
                return toObject((short[]) object);
            } else if (Integer[].class.equals(clazz) && object instanceof int[]){
                return toObject((int[]) object);
            } else if (Long[].class.equals(clazz) && object instanceof long[]){
                return toObject((long[]) object);
            } else if (Float[].class.equals(clazz) && object instanceof float[]){
                return toObject((float[]) object);
            } else if (Double[].class.equals(clazz) && object instanceof double[]){
                return toObject((double[]) object);
            } else if (Character[].class.equals(clazz) && object instanceof char[]){
                return toObject((char[]) object);
            } else if (Boolean[].class.equals(clazz) && object instanceof boolean[]){
                return toObject((boolean[]) object);
            }

        } catch (ClassNotFoundException e) {
            return object;
        }
        return object;
    }


}
