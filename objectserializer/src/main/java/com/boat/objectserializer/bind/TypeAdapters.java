package com.boat.objectserializer.bind;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;


import com.boat.objectserializer.ObjectSerializer;
import com.boat.objectserializer.ElementRecord;
import com.boat.objectserializer.TypeAdapter;
import com.boat.objectserializer.TypeAdapterFactory;
import com.boat.objectserializer.TypeToken;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

/**
 * Type adapters for basic types.
 */

public class TypeAdapters {

    public static final TypeAdapter<Class> CLASS = new TypeAdapter<Class>() {

        @Override
        public void write(Bundle out, String key, Class value) {
            throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: "
                    + value.getName() + ". Forgot to register a type adapter?");
        }

        @Override
        public Class read(Bundle in, String key) {
            throw new UnsupportedOperationException(
                    "Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }

        @Override
        public String getToken() {
            return null;
        }
    };

    public static final TypeAdapterFactory CLASS_FACTORY = newFactory(Class.class, CLASS);

    public static final TypeAdapter<BitSet> BIT_SET = new TypeAdapter<BitSet>() {

        @Override
        public void write(Bundle out, String key, BitSet value) {

            byte[] array = value.toByteArray();

            if (array == null || array.length == 0) {
                return;
            }

            out.putByteArray(key, array);
            ElementRecord record = new ElementRecord(
                    ElementRecord.ELEMENT_ARRAY,
                    false,
                    array.length,
                    null);
            out.putParcelable(ElementRecord.META_DATA_KEY + key, record);

        }

        @Override
        public BitSet read(Bundle in, String key) {

            ElementRecord record = in.getParcelable(ElementRecord.META_DATA_KEY + key);

            if (record == null) {
                return null;
            }

            if (record.getElementType() != ElementRecord.ELEMENT_ARRAY) {
                throw new IllegalArgumentException("Current Bundle doesn't have BitSet");
            }

            byte[] array = in.getByteArray(key);

            if (array == null) {
                return null;
            }

            BitSet bitset = new BitSet();

            for (int i = 0; i < array.length; i++) {
                bitset.set(array[i]);
            }

            return bitset;
        }

        @Override
        public String getToken() {
            return BitSet.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory BIT_SET_FACTORY = newFactory(BitSet.class, BIT_SET);

    public static final TypeAdapter<Byte> BYTE = new AIDLSupportAdapter.ByteTypeAdapter().nullSafe();

    public static final TypeAdapterFactory BYTE_FACTORY = newFactory(Byte.class,byte.class, BYTE);

    public static final TypeAdapter<Boolean> BOOLEAN = new AIDLSupportAdapter.BooleanTypeAdapter().nullSafe();

    public static final TypeAdapterFactory BOOLEAN_FACTORY = newFactory(Boolean.class, boolean.class, BOOLEAN);

    public static final TypeAdapter<Short> SHORT = new AIDLSupportAdapter.ShortTypeAdapter().nullSafe();

    public static final TypeAdapterFactory SHORT_FACTORY = newFactory(Short.class, short.class, SHORT);

    public static final TypeAdapter<Integer> INT = new AIDLSupportAdapter.IntegerTypeAdapter().nullSafe();

    public static final TypeAdapterFactory INT_FACTORY = newFactory(Integer.class,int.class, INT);

    public static final TypeAdapter<Long> LONG = new AIDLSupportAdapter.LongTypeAdapter().nullSafe();

    public static final TypeAdapterFactory LONG_FACTORY = newFactory(Long.class,long.class, LONG);

    public static final TypeAdapter<Float> FLOAT = new AIDLSupportAdapter.FloatTypeAdapter().nullSafe();

    public static final TypeAdapterFactory FLOAT_FACTORY = newFactory(Float.class,float.class, FLOAT);

    public static final TypeAdapter<Double> DOUBLE = new AIDLSupportAdapter.DoubleTypeAdapter().nullSafe();

    public static final TypeAdapterFactory DOUBLE_FACTORY = newFactory(Double.class,double.class, DOUBLE);

    public static final TypeAdapter<Character> CHAR = new AIDLSupportAdapter.CharacterTypeAdapter().nullSafe();

    public static final TypeAdapterFactory CHAR_FACTORY = newFactory(Character.class,char.class, CHAR);

    public static final TypeAdapter<String> STRING = new AIDLSupportAdapter.StringTypeAdapter().nullSafe();

    public static final TypeAdapterFactory STRING_FACTORY = newFactory(String.class, STRING);

    public static final TypeAdapter<CharSequence> CHAR_SEQUENCE = new AIDLSupportAdapter.CharSequenceTypeAdapter().nullSafe();

    public static final TypeAdapterFactory CHAR_SEQUENCE_FACTORY = newFactory(CharSequence.class, CHAR_SEQUENCE);

    //todo: implements Parcelable classes can use this factory to transport
    public static final TypeAdapter<Parcelable> PARCELABLE = new AIDLSupportAdapter.ParcelableTypeAdapter().nullSafe();

    public static final TypeAdapterFactory PARCELABLE_FACTORY = newTypeHierarchyFactory(Parcelable.class, PARCELABLE);

    public static final TypeAdapter<boolean[]> BOOLEAN_ARRAY = new AIDLSupportAdapter.BooleanArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory BOOLEAN_ARRAY_FACTORY = newFactory(boolean[].class, BOOLEAN_ARRAY);

    public static final TypeAdapter<Boolean[]> WRAPPER_BOOLEAN_ARRAY = new AIDLSupportAdapter.WrapperBooleanTypeAdapter().nullSafe();

    public static final TypeAdapterFactory WRAPPER_BOOLEAN_ARRAY_FACTORY = newFactory(Boolean[].class, WRAPPER_BOOLEAN_ARRAY);

    public static final TypeAdapter<byte[]> BYTE_ARRAY = new AIDLSupportAdapter.ByteArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory BYTE_ARRAY_FACTORY = newFactory(byte[].class, BYTE_ARRAY);

    public static final TypeAdapter<Byte[]> WRAPPER_BYTE_ARRAY = new AIDLSupportAdapter.WrapperByteArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory WRAPPER_BYTE_ARRAY_FACTORY = newFactory(Byte[].class, WRAPPER_BYTE_ARRAY);

    public static final TypeAdapter<short[]> SHORT_ARRAY = new AIDLSupportAdapter.ShortArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory SHORT_ARRAY_FACTORY = newFactory(short[].class, SHORT_ARRAY);

    public static final TypeAdapter<Short[]> WRAPPER_SHORT_ARRAY = new AIDLSupportAdapter.WrapperShortArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory WRAPPER_SHORT_ARRAY_FACTORY = newFactory(Short[].class, WRAPPER_SHORT_ARRAY);

    public static final TypeAdapter<int[]> INT_ARRAY = new AIDLSupportAdapter.IntArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory INT_ARRAY_FACTORY = newFactory(int[].class, INT_ARRAY);

    public static final TypeAdapter<Integer[]> INTEGER_ARRAY = new AIDLSupportAdapter.WrapperIntArrayTypeAdapter().nullSafe();

    public static final TypeAdapter<long[]> LONG_ARRAY = new AIDLSupportAdapter.LongArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory LONG_ARRAY_FACTORY = newFactory(long[].class, LONG_ARRAY);

    public static final TypeAdapter<Long[]> WRAPPER_LONG_ARRAY = new AIDLSupportAdapter.WrapperLongArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory WRAPPER_LONG_ARRAY_FACTORY = newFactory(Long[].class, WRAPPER_LONG_ARRAY);

    public static final TypeAdapterFactory INTEGER_ARRAY_FACTORY = newFactory(Integer[].class, INTEGER_ARRAY);

    public static final TypeAdapter<float[]> FLOAT_ARRAY = new AIDLSupportAdapter.FloatArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory FLOAT_ARRAY_FACTORY = newFactory(float[].class, FLOAT_ARRAY);

    public static final TypeAdapter<Float[]> WRAPPER_FLOAT_ARRAY = new AIDLSupportAdapter.WrapperFloatArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory WRAPPER_FLOAT_ARRAY_FACTORY = newFactory(Float[].class, WRAPPER_FLOAT_ARRAY);

    public static final TypeAdapter<double[]> DOUBLE_ARRAY = new AIDLSupportAdapter.DoubleArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory DOUBLE_ARRAY_FACTORY = newFactory(double[].class, DOUBLE_ARRAY);

    public static final TypeAdapter<Double[]> WRAPPER_DOUBLE_ARRAY = new AIDLSupportAdapter.WrapperDoubleArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory WRAPPER_DOUBLE_ARRAY_FACTORY = newFactory(Double[].class, WRAPPER_DOUBLE_ARRAY);

    public static final TypeAdapter<String[]> STRING_ARRAY = new AIDLSupportAdapter.StringArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory STRING_ARRAY_FACTORY = newFactory(String[].class, STRING_ARRAY);

    public static final TypeAdapter<char[]> CHAR_ARRAY = new AIDLSupportAdapter.CharArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory CHAR_ARRAY_FACTORY = newFactory(char[].class, CHAR_ARRAY);

    public static final TypeAdapter<Character[]> WRAPPER_CHAR_ARRAY = new AIDLSupportAdapter.WrapperCharArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory WRAPPER_CHAR_ARRAY_FACTORY = newFactory(Character[].class, WRAPPER_CHAR_ARRAY);

    public static final TypeAdapter<CharSequence[]> CHAR_SEQUENCE_ARRAY = new AIDLSupportAdapter.CharSequenceArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory CHAR_SEQUENCE_ARRAY_FACTORY = newFactory(CharSequence[].class,CHAR_SEQUENCE_ARRAY);

    public static final TypeAdapter<Parcelable[]> PARCELABLE_ARRAY = new AIDLSupportAdapter.ParcelableArrayTypeAdapter().nullSafe();

    public static final TypeAdapterFactory PARCELABLE_ARRAY_FACTORY = newFactory(Parcelable[].class, PARCELABLE_ARRAY);

    public static final TypeAdapter<AtomicInteger> ATOMIC_INTEGER = new TypeAdapter<AtomicInteger>() {
        @Override
        public void write(Bundle out, String key, AtomicInteger value) {
            INT.write(out, key, value.intValue());
        }

        @Override
        public AtomicInteger read(Bundle in, String key) {
            int value = INT.read(in, key);
            return new AtomicInteger(value);
        }

        @Override
        public String getToken() {
            return AtomicInteger.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory ATOMIC_INTEGER_FACTORY = newFactory(AtomicInteger.class, ATOMIC_INTEGER);

    public static final TypeAdapter<AtomicBoolean> ATOMIC_BOOLEAN = new TypeAdapter<AtomicBoolean>() {
        @Override
        public void write(Bundle out, String key, AtomicBoolean value) {
            BOOLEAN.write(out, key, value.get());
        }

        @Override
        public AtomicBoolean read(Bundle in, String key) {
            boolean result = BOOLEAN.read(in, key);
            return new AtomicBoolean(result);
        }

        @Override
        public String getToken() {
            return AtomicBoolean.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory ATOMIC_BOOLEAN_FACTORY = newFactory(AtomicBoolean.class, ATOMIC_BOOLEAN);

    public static final TypeAdapter<AtomicIntegerArray> ATOMIC_INTEGER_ARRAY = new TypeAdapter<AtomicIntegerArray>() {
        @Override
        public void write(Bundle out, String key, AtomicIntegerArray value) {
            int[] array = new int[value.length()];
            for (int i = 0; i < value.length(); i++) {
                array[i] = value.get(i);
            }
            INT_ARRAY.write(out, key, array);
        }

        @Override
        public AtomicIntegerArray read(Bundle in, String key) {

            int[] array = INT_ARRAY.read(in, key);
            if (array == null) {
                return null;
            }
            AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(array.length);
            for (int i = 0; i < array.length; ++i) {
                atomicIntegerArray.set(i, array[i]);
            }
            return null;
        }

        @Override
        public String getToken() {
            return AtomicBoolean.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory ATOMIC_INTEGER_ARRAY_FACTORY = newFactory(AtomicIntegerArray.class, ATOMIC_INTEGER_ARRAY);

    public static final TypeAdapter<AtomicLong> ATOMIC_LONG = new TypeAdapter<AtomicLong>() {
        @Override
        public void write(Bundle out, String key, AtomicLong value) {
            LONG.write(out,key,value.get());
        }

        @Override
        public AtomicLong read(Bundle in, String key) {
            return new AtomicLong(LONG.read(in,key));
        }

        @Override
        public String getToken() {
            return AtomicLong.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory ATOMIC_LONG_FACTORY = newFactory(AtomicLong.class,ATOMIC_LONG);

    public static final TypeAdapter<AtomicLongArray> ATOMIC_LONG_ARRAY = new TypeAdapter<AtomicLongArray>() {
        @Override
        public void write(Bundle out, String key, AtomicLongArray value) {
            long[] array = new long[value.length()];
            for (int i = 0, length = value.length(); i < length; i++) {
                 array[i] = value.get(i);
            }
            LONG_ARRAY.write(out,key,array);
        }

        @Override
        public AtomicLongArray read(Bundle in, String key) {
            long[] values = LONG_ARRAY.read(in,key);

            if (values == null){
                return null;
            }

            AtomicLongArray array = new AtomicLongArray(values.length);
            for (int i = 0; i < values.length; ++i) {
                array.set(i, values[i]);
            }
            return array;
        }

        @Override
        public String getToken() {
            return AtomicLongArray.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory ATOMIC_LONG_ARRAY_FACTORY = newFactory(AtomicLongArray.class,ATOMIC_LONG_ARRAY);

    public static final TypeAdapter<BigDecimal> BIG_DECIMAL = new TypeAdapter<BigDecimal>() {
        @Override
        public void write(Bundle out, String key, BigDecimal value) {
            STRING.write(out, key, value.toString());
        }

        @Override
        public BigDecimal read(Bundle in, String key) {
            String value = STRING.read(in, key);
            if (TextUtils.isEmpty(value)) {
                return null;
            }
            return new BigDecimal(value);
        }

        @Override
        public String getToken() {
            return BigDecimal.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory BIG_DECIMAL_FACTORY = newFactory(BigDecimal.class, BIG_DECIMAL);

    public static final TypeAdapter<BigInteger> BIG_INTEGER = new TypeAdapter<BigInteger>() {
        @Override
        public void write(Bundle out, String key, BigInteger value) {
            STRING.write(out, key, value.toString());
        }

        @Override
        public BigInteger read(Bundle in, String key) {
            String value = STRING.read(in, key);
            return new BigInteger(value);
        }

        @Override
        public String getToken() {
            return BigInteger.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory BIG_INTEGER_FACTORY = newFactory(BigInteger.class, BIG_INTEGER);

    public static final TypeAdapter<StringBuilder> STRING_BUILDER = new TypeAdapter<StringBuilder>() {
        @Override
        public void write(Bundle out, String key, StringBuilder value) {
            STRING.write(out, key, value.toString());
        }

        @Override
        public StringBuilder read(Bundle in, String key) {
            String value = STRING.read(in, key);
            if (TextUtils.isEmpty(value)) {
                return null;
            }
            return new StringBuilder(value);
        }

        @Override
        public String getToken() {
            return StringBuilder.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory STRING_BUILDER_FACTORY = newFactory(StringBuilder.class, STRING_BUILDER);

    public static final TypeAdapter<StringBuffer> STRING_BUFFER = new TypeAdapter<StringBuffer>() {
        @Override
        public void write(Bundle out, String key, StringBuffer value) {
            STRING.write(out, key, value.toString());
        }

        @Override
        public StringBuffer read(Bundle in, String key) {
            String value = STRING.read(in, key);
            if (TextUtils.isEmpty(value)) {
                return null;
            }
            return new StringBuffer(value);
        }

        @Override
        public String getToken() {
            return StringBuffer.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory STRING_BUFFER_FACTORY = newFactory(StringBuffer.class, STRING_BUFFER);

    public static final TypeAdapter<URL> URL_TYPE_ADAPTER = new TypeAdapter<URL>() {
        @Override
        public void write(Bundle out, String key, URL value) {
            STRING.write(out, key, value.toExternalForm());
        }

        @Override
        public URL read(Bundle in, String key) {
            String value = STRING.read(in, key);
            if (TextUtils.isEmpty(value)) {
                return null;
            }
            try {
                return new URL(value);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public String getToken() {
            return URL.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory URL_FACTORY = newFactory(URL.class, URL_TYPE_ADAPTER);

    public static final TypeAdapter<URI> URI_TYPE_ADAPTER = new TypeAdapter<URI>() {
        @Override
        public void write(Bundle out, String key, URI value) {
            STRING.write(out, key, value.toASCIIString());
        }

        @Override
        public URI read(Bundle in, String key) {
            String value = STRING.read(in, key);
            if (TextUtils.isEmpty(value)) {
                return null;
            }
            try {
                return new URI(value);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public String getToken() {
            return URI.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory URI_FACTORY = newFactory(URI.class, URI_TYPE_ADAPTER);

    public static final TypeAdapter<InetAddress> INET_ADDRESS = new TypeAdapter<InetAddress>() {
        @Override
        public void write(Bundle out, String key, InetAddress value) {
            STRING.write(out, key, value.getHostAddress());
        }

        @Override
        public InetAddress read(Bundle in, String key) {
            String value = STRING.read(in, key);
            if (TextUtils.isEmpty(value)) {
                return null;
            }
            try {
                return InetAddress.getByName(value);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public String getToken() {
            return InetAddress.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory INET_ADDRESS_FACTORY = newFactory(InetAddress.class, INET_ADDRESS);

    public static final TypeAdapter<UUID> UUID_TYPE_ADAPTER = new TypeAdapter<UUID>() {
        @Override
        public void write(Bundle out, String key, UUID value) {
            STRING.write(out, key, value.toString());
        }

        @Override
        public UUID read(Bundle in, String key) {
            String value = STRING.read(in, key);
            if (TextUtils.isEmpty(value)) {
                return null;
            }
            return UUID.fromString(value);
        }

        @Override
        public String getToken() {
            return UUID.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory UUID_FACTORY = newFactory(UUID.class, UUID_TYPE_ADAPTER);

    public static final TypeAdapter<Currency> CURRENCY = new TypeAdapter<Currency>() {
        @Override
        public void write(Bundle out, String key, Currency value) {
            STRING.write(out, key, value.getCurrencyCode());
        }

        @Override
        public Currency read(Bundle in, String key) {
            String value = STRING.read(in, key);
            if (TextUtils.isEmpty(value)) {
                return null;
            }
            return Currency.getInstance(value);
        }

        @Override
        public String getToken() {
            return Currency.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory CURRENCY_FACTORY = newFactory(Currency.class, CURRENCY);

    public static final TypeAdapterFactory TIMESTAMP_FACTORY = new TypeAdapterFactory() {
        @Override
        public <T> TypeAdapter<T> create(ObjectSerializer cabin, TypeToken<T> typeToken) {
            if (typeToken.getRawType() != Timestamp.class) {
                return null;
            }

            final TypeAdapter<Date> dateTypeAdapter = cabin.getAdapter(Date.class);
            return (TypeAdapter<T>) new TypeAdapter<Timestamp>() {
                @Override
                public void write(Bundle out, String key, Timestamp value) {
                    dateTypeAdapter.write(out,key,value);
                }

                @Override
                public Timestamp read(Bundle in, String key) {
                    return new Timestamp(dateTypeAdapter.read(in,key).getTime()) ;
                }

                @Override
                public String getToken() {
                    return Timestamp.class.getName();
                }
            };


        }
    };


    public static final TypeAdapter<Calendar> CALENDAR = new TypeAdapter<Calendar>() {

        private static final String YEAR = "y";
        private static final String MONTH = "mon";
        private static final String DAY_OF_MONTH = "dayOfMonth";
        private static final String HOUR_OF_DAY = "hourOfDay";
        private static final String MINUTE = "min";
        private static final String SECOND = "s";

        @Override
        public void write(Bundle out, String key, Calendar value) {
            Bundle content = new Bundle();
            content.putInt(YEAR, value.get(Calendar.YEAR));
            content.putInt(MONTH, value.get(Calendar.MONTH));
            content.putInt(DAY_OF_MONTH, value.get(Calendar.DAY_OF_MONTH));
            content.putInt(HOUR_OF_DAY, value.get(Calendar.HOUR_OF_DAY));
            content.putInt(MINUTE, value.get(Calendar.MINUTE));
            content.putInt(SECOND, value.get(Calendar.SECOND));
            out.putParcelable(key, content);
        }

        @Override
        public Calendar read(Bundle in, String key) {

            Bundle content = in.getParcelable(key);
            int year = content.getInt(YEAR);
            int month = content.getInt(MONTH);
            int dayOfMonth = content.getInt(DAY_OF_MONTH);
            int hourOfDay = content.getInt(HOUR_OF_DAY);
            int minute = content.getInt(MINUTE);
            int second = content.getInt(SECOND);
            return new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second);
        }

        @Override
        public String getToken() {
            return Calendar.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory CALENDAR_FACTORY = newFactoryForMultipleTypes(Calendar.class, GregorianCalendar.class, CALENDAR);

    public static final TypeAdapter<Locale> LOCALE = new TypeAdapter<Locale>() {
        @Override
        public void write(Bundle out, String key, Locale value) {
            STRING.write(out, key, value.toString());
        }

        @Override
        public Locale read(Bundle in, String key) {

            String locale = STRING.read(in, key);

            if (TextUtils.isEmpty(locale)) {
                return null;
            }

            StringTokenizer tokenizer = new StringTokenizer(locale, "_");
            String language = null;
            String country = null;
            String variant = null;
            if (tokenizer.hasMoreElements()) {
                language = tokenizer.nextToken();
            }
            if (tokenizer.hasMoreElements()) {
                country = tokenizer.nextToken();
            }
            if (tokenizer.hasMoreElements()) {
                variant = tokenizer.nextToken();
            }
            if (country == null && variant == null) {
                return new Locale(language);
            } else if (variant == null) {
                return new Locale(language, country);
            } else {
                return new Locale(language, country, variant);
            }
        }

        @Override
        public String getToken() {
            return Locale.class.getName();
        }
    }.nullSafe();

    public static final TypeAdapterFactory LOCALE_FACTORY = newFactory(Locale.class, LOCALE);

    public static final TypeAdapterFactory ENUM_FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings({"rawtypes", "unchecked"})
        @Override public <T> TypeAdapter<T> create(ObjectSerializer cabin, TypeToken<T> typeToken) {
            Class<? super T> rawType = typeToken.getRawType();
            if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class) {
                return null;
            }
            if (!rawType.isEnum()) {
                rawType = rawType.getSuperclass(); // handle anonymous subclasses
            }
            return (TypeAdapter<T>) new EnumTypeAdapter(rawType);
        }
    };

    private static final class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {

        private final Map<String, T> nameToConstant = new HashMap<String, T>();
        private final Map<T, String> constantToName = new HashMap<T, String>();

        public EnumTypeAdapter(Class<T> classOfT) {
            for (T constant : classOfT.getEnumConstants()) {
                String name = constant.name();
                nameToConstant.put(name, constant);
                constantToName.put(constant, name);
            }
        }

        @Override
        public void write(Bundle out, String key, T value) {
            String valueString = constantToName.get(value);
            STRING.write(out,key,valueString);
        }

        @Override
        public T read(Bundle in, String key) {
            String value = STRING.read(in,key);
            if (TextUtils.isEmpty(value)){
                return null;
            }
            return nameToConstant.get(value);
        }

        @Override
        public String getToken() {
            return Enum.class.getName();
        }
    }

    public static <TT> TypeAdapterFactory newFactory(
            final Class<TT> type, final TypeAdapter<TT> typeAdapter) {
        return new TypeAdapterFactory() {
            @SuppressWarnings("unchecked") // we use a runtime check to make sure the 'T's equal
            @Override
            public <T> TypeAdapter<T> create(ObjectSerializer cabin, TypeToken<T> typeToken) {
                return typeToken.getRawType() == type ? (TypeAdapter<T>) typeAdapter : null;
            }

            @Override
            public String toString() {
                return "Factory[type=" + type.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactory(
            final TypeToken<TT> type, final TypeAdapter<TT> typeAdapter) {
        return new TypeAdapterFactory() {
            @SuppressWarnings("unchecked") // we use a runtime check to make sure the 'T's equal
            @Override public <T> TypeAdapter<T> create(ObjectSerializer objectSerializer, TypeToken<T> typeToken) {
                return typeToken.equals(type) ? (TypeAdapter<T>) typeAdapter : null;
            }
        };
    }


    public static <TT> TypeAdapterFactory newFactory(
            final Class<TT> unboxed, final Class<TT> boxed, final TypeAdapter<? super TT> typeAdapter) {
        return new TypeAdapterFactory() {
            @SuppressWarnings("unchecked") // we use a runtime check to make sure the 'T's equal
            @Override public <T> TypeAdapter<T> create(ObjectSerializer cabin, TypeToken<T> typeToken) {
                Class<? super T> rawType = typeToken.getRawType();
                return (rawType == unboxed || rawType == boxed) ? (TypeAdapter<T>) typeAdapter : null;
            }
            @Override public String toString() {
                return "Factory[type=" + boxed.getName()
                        + "+" + unboxed.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactoryForMultipleTypes(final Class<TT> base,
                                                                     final Class<? extends TT> sub, final TypeAdapter<? super TT> typeAdapter) {
        return new TypeAdapterFactory() {
            @SuppressWarnings("unchecked") // we use a runtime check to make sure the 'T's equal
            @Override
            public <T> TypeAdapter<T> create(ObjectSerializer cabin, TypeToken<T> typeToken) {
                Class<? super T> rawType = typeToken.getRawType();
                return (rawType == base || rawType == sub) ? (TypeAdapter<T>) typeAdapter : null;
            }

            @Override
            public String toString() {
                return "Factory[type=" + base.getName()
                        + "+" + sub.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }

    /**
     * Returns a factory for all subtypes of {@code typeAdapter}. We do a runtime check to confirm
     * that the deserialized type matches the type requested.
     */
    public static <T1> TypeAdapterFactory newTypeHierarchyFactory(
            final Class<T1> clazz, final TypeAdapter<T1> typeAdapter) {
        return new TypeAdapterFactory() {
            @SuppressWarnings("unchecked")
            @Override
            public <T2> TypeAdapter<T2> create(ObjectSerializer ca, TypeToken<T2> typeToken) {
                final Class<? super T2> requestedType = typeToken.getRawType();
                if (!clazz.isAssignableFrom(requestedType)) {
                    return null;
                }
                return (TypeAdapter<T2>) new TypeAdapter<T1>() {
                    @Override
                    public void write(Bundle out, String key, T1 value) {
                        typeAdapter.write(out, key, value);
                    }

                    @Override
                    public T1 read(Bundle in, String key) {
                        T1 result = typeAdapter.read(in, key);
                        if (result != null && !requestedType.isInstance(result)) {
                            throw new IllegalArgumentException("Expected a " + requestedType.getName()
                                    + " but was " + result.getClass().getName());
                        }
                        return result;
                    }

                    @Override
                    public String getToken() {
                        return typeAdapter.getToken();
                    }
                };
            }

            @Override
            public String toString() {
                return "Factory[typeHierarchy=" + clazz.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }



    public static final Map<String,TypeAdapter<?>> getTokenOfAdapterMap(){
        ArrayMap<String,TypeAdapter<?>> map = new ArrayMap<>(64);
        Class clazz = TypeAdapters.class;
        Field[] fields = clazz.getFields();
        if (fields == null){
            return map;
        }

        for (Field field : fields){
            try {
                Object object = field.get(clazz);
                if (object instanceof TypeAdapter){
                    TypeAdapter adapter = (TypeAdapter) object;
                    map.put(adapter.getToken(),adapter);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return map;
    }

}
