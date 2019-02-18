package com.boat.objectserializer.bind;

import android.os.Bundle;
import android.os.Parcelable;

import com.boat.objectserializer.$Object$Types;
import com.boat.objectserializer.ObjectSerializer;
import com.boat.objectserializer.ElementRecord;
import com.boat.objectserializer.TypeAdapter;
import com.boat.objectserializer.TypeAdapterFactory;
import com.boat.objectserializer.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by uchia on 2019/2/13.
 */

public class ArrayTypeAdapter<E> extends TypeAdapter<Object> {

    private static final String PREFIX = "1[";

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings({"unchecked", "rawtypes"})
        @Override
        public <T> TypeAdapter<T> create(ObjectSerializer gson, TypeToken<T> typeToken) {
            Type type = typeToken.getType();
            if (!(type instanceof GenericArrayType
                    || type instanceof Class && ((Class<?>) type).isArray())) {
                return null;
            }

            Type componentType = $Object$Types.getArrayComponentType(type);
            TypeAdapter<?> componentTypeAdapter = gson.getAdapter(TypeToken.get(componentType));
            return new ArrayTypeAdapter(
                    gson, componentTypeAdapter, $Object$Types.getRawType(componentType)).nullSafe();
        }
    };

    private final Class<E> componentType;
    private final TypeAdapter<E> componentTypeAdapter;

    public ArrayTypeAdapter(ObjectSerializer context, TypeAdapter<E> componentTypeAdapter, Class<E> componentType) {
        this.componentTypeAdapter =
                new TypeAdapterRuntimeTypeWrapper<E>(context, componentTypeAdapter, componentType);
        this.componentType = componentType;
    }


    @Override
    public void write(Bundle out, String key, Object array) {

        int length = Array.getLength(array);
        if (length <= 0) {
            return;
        }

        Bundle content = new Bundle();

        ElementRecord record = new ElementRecord(
                ElementRecord.ELEMENT_ARRAY,
                false,
                length,
                null);

        Bundle[] elements = new Bundle[length];
        content.putParcelable(ElementRecord.META_DATA_KEY, record);
        for (int i = 0; i < length; i++) {
            Bundle element = new Bundle();
            E value = (E) Array.get(array, i);
            componentTypeAdapter.write(element, PREFIX, value);
            elements[i] = element;
        }
        content.putParcelableArray(key,elements);

        out.putParcelable(key, content);
    }

    @Override
    public Object read(Bundle in, String key) {

        Bundle content = in.getParcelable(key);

        if (content == null){
            return null;
        }
        content.setClassLoader(ElementRecord.class.getClassLoader());
        ElementRecord record = content.getParcelable(ElementRecord.META_DATA_KEY);
        if (record.getElementType() != ElementRecord.ELEMENT_ARRAY){
            return null;
        }

        int count = record.getElementCount();

        Parcelable[] elements = content.getParcelableArray(key);
        List<E> list = new ArrayList<E>(count);
        for (int i = 0; i < count; i++){
            Bundle bundle = (Bundle) elements[i];
            E instance = componentTypeAdapter.read(bundle,PREFIX);
            list.add(instance);
        }

        int size = list.size();
        Object array = Array.newInstance(componentType, size);
        for (int i = 0; i < size; i++) {
            Array.set(array, i, list.get(i));
        }
        return array;
    }

    @Override
    public String getToken() {
        return ArrayTypeAdapter.class.getName();
    }
}
