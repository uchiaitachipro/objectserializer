package com.boat.objectserializer.bind;

import android.os.Bundle;
import android.os.Parcelable;

import com.boat.objectserializer.$Object$Types;
import com.boat.objectserializer.ObjectSerializer;
import com.boat.objectserializer.ConstructorConstructor;
import com.boat.objectserializer.ElementRecord;
import com.boat.objectserializer.ObjectConstructor;
import com.boat.objectserializer.Primitives;
import com.boat.objectserializer.TypeAdapter;
import com.boat.objectserializer.TypeAdapterFactory;
import com.boat.objectserializer.TypeToken;
import com.boat.objectserializer.reflect.ReflectionAccessor;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by uchia on 2019/2/12.
 */

public class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {

    private static final String PREFIX = "1L";

    private final ConstructorConstructor constructorConstructor;
    private final ReflectionAccessor accessor = ReflectionAccessor.getInstance();

    public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    @Override
    public <T> TypeAdapter<T> create(ObjectSerializer cabin, TypeToken<T> type) {
        Class<? super T> raw = type.getRawType();

        if (!Object.class.isAssignableFrom(raw)) {
            return null; // it's a primitive!
        }

        ObjectConstructor<T> constructor = constructorConstructor.get(type);
        return new Adapter<T>(constructor, getBoundFields(cabin, type, raw));
    }

    private Map<String, BoundField> getBoundFields(ObjectSerializer context, TypeToken<?> type, Class<?> raw) {
        Map<String, BoundField> result = new LinkedHashMap<String, BoundField>();
        if (raw.isInterface()) {
            return result;
        }

        Type declaredType = type.getType();
        while (raw != Object.class) {
            Field[] fields = raw.getDeclaredFields();
            for (Field field : fields) {
                boolean serialize = excludeField(field, true);
                boolean deserialize = excludeField(field, false);
                if (!serialize && !deserialize) {
                    continue;
                }
                accessor.makeAccessible(field);
                Type fieldType = $Object$Types.resolve(type.getType(), raw, field.getGenericType());
                List<String> fieldNames = getFieldNames(field);
                BoundField previous = null;
                for (int i = 0, size = fieldNames.size(); i < size; ++i) {
                    String name = fieldNames.get(i);
                    if (i != 0) serialize = false; // only serialize the default name
                    BoundField boundField = createBoundField(context, field, name,
                            TypeToken.get(fieldType), serialize, deserialize);
                    BoundField replaced = result.put(name, boundField);
                    if (previous == null) previous = replaced;
                }
                if (previous != null) {
                    throw new IllegalArgumentException(declaredType
                            + " declares multiple JSON fields named " + previous.name);
                }
            }
            type = TypeToken.get($Object$Types.resolve(type.getType(), raw, raw.getGenericSuperclass()));
            raw = type.getRawType();
        }
        return result;
    }

    private List<String> getFieldNames(Field f) {
        String name = f.getName();
        List<String> list = new ArrayList<>();
        list.add(name);
        return list;
    }

    public boolean excludeField(Field f, boolean serialize) {
        return true;
    }

    private ReflectiveTypeAdapterFactory.BoundField createBoundField(
            final ObjectSerializer context, final Field field, final String name,
            final TypeToken<?> fieldType, boolean serialize, boolean deserialize) {
        final boolean isPrimitive = Primitives.isPrimitive(fieldType.getRawType());
        // special casing primitives here saves ~5% on Android...
        TypeAdapter<?> mapped = context.getAdapter(fieldType);

        final TypeAdapter<?> typeAdapter = mapped;
        return new ReflectiveTypeAdapterFactory.BoundField(name, serialize, deserialize) {
            @SuppressWarnings({"unchecked", "rawtypes"})
            // the type adapter and field type always agree
            @Override
            void write(Bundle out, String key, Object value)
                    throws IllegalAccessException {
                Object fieldValue = field.get(value);
                TypeAdapter t = new TypeAdapterRuntimeTypeWrapper(context, typeAdapter, fieldType.getType());
                t.write(out, field.getName(), fieldValue);
            }

            @Override
            void read(Bundle in, String key, Object value)
                    throws IllegalAccessException {
                Object fieldValue = typeAdapter.read(in, field.getName());
                if (fieldValue != null || !isPrimitive) {
                    field.set(value, fieldValue);
                }
            }

            @Override
            public boolean writeField(Object value) throws IllegalAccessException {
                if (!serialized) return false;
                Object fieldValue = field.get(value);
                return fieldValue != value; // avoid recursion for example for Throwable.cause
            }
        };
    }

    static abstract class BoundField {
        final String name;
        final boolean serialized;
        final boolean deserialized;

        protected BoundField(String name, boolean serialized, boolean deserialized) {
            this.name = name;
            this.serialized = serialized;
            this.deserialized = deserialized;
        }

        abstract boolean writeField(Object value) throws IllegalAccessException;

        abstract void write(Bundle out, String key, Object value) throws IllegalAccessException;

        abstract void read(Bundle in, String key, Object value) throws IllegalAccessException;
    }

    public static final class Adapter<T> extends TypeAdapter<T> {
        private final ObjectConstructor<T> constructor;
        private final Map<String, BoundField> boundFields;

        Adapter(ObjectConstructor<T> constructor, Map<String, BoundField> boundFields) {
            this.constructor = constructor;
            this.boundFields = boundFields;
        }

        @Override
        public T read(Bundle in, String key) {

            T instance = constructor.construct();
            in.setClassLoader(getClass().getClassLoader());
            Bundle content = in.getParcelable(key);

            if (content == null) {
                return instance;
            }
            content.setClassLoader(ElementRecord.class.getClassLoader());
            ElementRecord record = content.getParcelable(ElementRecord.META_DATA_KEY);

            if (record.getElementType() != ElementRecord.ELEMENT_OBJECT) {
                return null;
            }

            String[] fieldNames = record.getElementsKey();

            if (fieldNames == null || fieldNames.length <= 0) {
                return instance;
            }

            Parcelable[] fields = content.getParcelableArray(PREFIX);

            try {
                for (int i = 0; i < fieldNames.length; i++) {
                    String name = fieldNames[i];
                    Bundle field = (Bundle) fields[i];
                    BoundField boundField = boundFields.get(name);
                    if (boundField == null || !boundField.deserialized) {
                        continue;
                    } else {
                        boundField.read(field, boundField.name, instance);
                    }
                }
            } catch (IllegalStateException e) {
                throw new IllegalArgumentException(e);
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
            return instance;
        }



        @Override
        public void write(Bundle out, String key, T value) {

            Bundle content = new Bundle();
            String[] keys = new String[boundFields.size()];
            Bundle[] fields = new Bundle[boundFields.size()];
            try {
                int i = 0;
                for (BoundField boundField : boundFields.values()) {
                    if (boundField.writeField(value)) {
                        Bundle field = new Bundle();
                        boundField.write(field, boundField.name, value);
                        fields[i] = field;
                        keys[i++] = boundField.name;
                    }
                }
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }

            ElementRecord record = new ElementRecord(
                    ElementRecord.ELEMENT_OBJECT,
                    false,
                    boundFields.size(),
                    keys);

            content.putParcelable(ElementRecord.META_DATA_KEY, record);
            content.putParcelableArray(PREFIX, fields);

            out.putParcelable(key, content);
        }

        @Override
        public String getToken() {
            return ReflectiveTypeAdapterFactory.Adapter.class.getName();
        }
    }
}
