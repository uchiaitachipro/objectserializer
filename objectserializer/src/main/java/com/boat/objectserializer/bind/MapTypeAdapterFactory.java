package com.boat.objectserializer.bind;

import android.os.Bundle;
import android.os.Parcelable;

import com.boat.objectserializer.$Object$Types;
import com.boat.objectserializer.ObjectSerializer;
import com.boat.objectserializer.ConstructorConstructor;
import com.boat.objectserializer.ElementRecord;
import com.boat.objectserializer.KeyValueRecord;
import com.boat.objectserializer.ObjectConstructor;
import com.boat.objectserializer.TypeAdapter;
import com.boat.objectserializer.TypeAdapterFactory;
import com.boat.objectserializer.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by uchia on 2019/2/13.
 */

public class MapTypeAdapterFactory implements TypeAdapterFactory {

    private static final String PREFIX = "1M";
    private static final String KEY_NAME = "1k";
    private static final String VALUE_NAME = "1v";

    private final ConstructorConstructor constructorConstructor;

    public MapTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    @Override
    public <T> TypeAdapter<T> create(ObjectSerializer cabin, TypeToken<T> typeToken) {
        Type type = typeToken.getType();

        Class<? super T> rawType = typeToken.getRawType();
        if (!Map.class.isAssignableFrom(rawType)) {
            return null;
        }

        Class<?> rawTypeOfSrc = $Object$Types.getRawType(type);
        Type[] keyAndValueTypes = $Object$Types.getMapKeyAndValueTypes(type, rawTypeOfSrc);
        TypeAdapter<?> keyAdapter = cabin.getAdapter(TypeToken.get(keyAndValueTypes[0]));
        TypeAdapter<?> valueAdapter = cabin.getAdapter(TypeToken.get(keyAndValueTypes[1]));
        ObjectConstructor<T> constructor = constructorConstructor.get(typeToken);

        @SuppressWarnings({"unchecked", "rawtypes"})
        // we don't define a type parameter for the key or value types
                TypeAdapter<T> result = new Adapter(cabin, keyAndValueTypes[0], keyAdapter,
                keyAndValueTypes[1], valueAdapter, constructor).nullSafe();
        return result;
    }

    private final class Adapter<K, V> extends TypeAdapter<Map<K, V>> {

        private final TypeAdapter<K> keyTypeAdapter;
        private final TypeAdapter<V> valueTypeAdapter;
        private final ObjectConstructor<? extends Map<K, V>> constructor;

        public Adapter(ObjectSerializer context, Type keyType, TypeAdapter<K> keyTypeAdapter,
                       Type valueType, TypeAdapter<V> valueTypeAdapter,
                       ObjectConstructor<? extends Map<K, V>> constructor) {
            this.keyTypeAdapter =
                    new TypeAdapterRuntimeTypeWrapper<K>(context, keyTypeAdapter, keyType);
            this.valueTypeAdapter =
                    new TypeAdapterRuntimeTypeWrapper<V>(context, valueTypeAdapter, valueType);
            this.constructor = constructor;
        }

        @Override
        public void write(Bundle out, String key, Map<K, V> value) {
            if (value.isEmpty()) {
                return;
            }

            Bundle content = new Bundle();

            int count = value.size();
            KeyValueRecord[] pair = new KeyValueRecord[count];

            ElementRecord record = new ElementRecord(
                    ElementRecord.ELEMENT_MAP,
                    false,
                    count,
                    null);

            content.putParcelable(ElementRecord.META_DATA_KEY, record);

            List<K> keys = new ArrayList<K>(value.size());
            List<V> values = new ArrayList<V>(value.size());
            for (Map.Entry<K, V> entry : value.entrySet()) {
                keys.add(entry.getKey());
                values.add(entry.getValue());
            }

            for (int i = 0; i < count; i++) {
                Bundle elementKey = new Bundle();
                Bundle elementValue = new Bundle();
                keyTypeAdapter.write(elementKey, KEY_NAME, keys.get(i));
                valueTypeAdapter.write(elementValue, VALUE_NAME, values.get(i));
                KeyValueRecord keyValueRecord = new KeyValueRecord(elementKey, elementValue);
                pair[i] = keyValueRecord;
            }
            content.putParcelableArray(PREFIX, pair);
            out.putParcelable(key, content);
        }

        @Override
        public Map<K, V> read(Bundle in, String key) {

            Bundle content = in.getParcelable(key);

            Map<K, V> map = constructor.construct();
            if (content == null) {
                return map;
            }
            content.setClassLoader(ElementRecord.class.getClassLoader());
            ElementRecord record = content.getParcelable(ElementRecord.META_DATA_KEY);

            if (record.getElementType() != ElementRecord.ELEMENT_MAP) {
                return map;
            }

            int count = record.getElementCount();

            Parcelable[] pair = content.getParcelableArray(PREFIX);

            if (pair == null) {
                return map;
            }

            for (int i = 0; i < count; i++) {
                KeyValueRecord keyValueRecord = (KeyValueRecord) pair[i];
                Bundle keyBundle = (Bundle) keyValueRecord.getKey();
                Bundle valueBundle = (Bundle) keyValueRecord.getValue();
                K k = keyTypeAdapter.read(keyBundle, KEY_NAME);
                V v = valueTypeAdapter.read(valueBundle, VALUE_NAME);
                V replaced = map.put(k, v);
                if (replaced != null) {
                    throw new IllegalArgumentException("duplicate key: " + key);
                }
            }

            return map;
        }

        @Override
        public String getToken() {
            return MapTypeAdapterFactory.Adapter.class.getName();
        }
    }
}
