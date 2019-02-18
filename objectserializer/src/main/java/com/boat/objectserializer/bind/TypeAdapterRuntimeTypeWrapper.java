package com.boat.objectserializer.bind;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;

import com.boat.objectserializer.ObjectSerializer;
import com.boat.objectserializer.TypeAdapter;
import com.boat.objectserializer.TypeToken;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public final class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T> {

    private static final String RUN_TIME_KEY = "1r_t_k";
    private static final String RUN_TIME_TYPE = "1r_t_t";

    private final ObjectSerializer context;
    private TypeAdapter<T> delegate;
    private final Type type;
    private ArrayMap<String, TypeAdapter> runtimeAdapters = new ArrayMap<>();

    public TypeAdapterRuntimeTypeWrapper(ObjectSerializer context, TypeAdapter<T> delegate, Type type) {
        this.context = context;
        this.delegate = delegate;
        this.type = type;
    }

    @Override
    public T read(Bundle in, String key) {

        String runtimeKey = in.getString(RUN_TIME_KEY);
        if (!TextUtils.isEmpty(runtimeKey)) {
            TypeAdapter adapter = runtimeAdapters.get(runtimeKey);
            if (adapter != null) {
                return (T) adapter.read(in, key);
            } else {
                // bundle from another process
                adapter = context.getAdapterByToken(runtimeKey);
                if (adapter != null) {
                    return (T) adapter.read(in, key);
                }
            }
        }

        String clazzType = in.getString(RUN_TIME_TYPE);
        if (!TextUtils.isEmpty(clazzType)) {
            TypeAdapter adapter = runtimeAdapters.get(clazzType);
            if (adapter == null) {
                try {
                    Class<?> clazz = Class.forName(clazzType);
                    adapter = context.getAdapter(TypeToken.get(clazz));
                    runtimeAdapters.put(clazzType, adapter);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return (T) adapter.read(in, key);
        }

        return delegate.read(in, key);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void write(Bundle out, String key, T value) {
        // Order of preference for choosing type adapters
        // First preference: a type adapter registered for the runtime type
        // Second preference: a type adapter registered for the declared type
        // Third preference: reflective type adapter for the runtime type (if it is a sub class of the declared type)
        // Fourth preference: reflective type adapter for the declared type

        TypeAdapter chosen = delegate;
        Type runtimeType = getRuntimeTypeIfMoreSpecific(type, value);
        if (runtimeType != type) {
            TypeAdapter runtimeTypeAdapter = context.getAdapter(TypeToken.get(runtimeType));
            if (!(runtimeTypeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter)) {
                // The user registered a type adapter for the runtime type, so we will use that
                chosen = runtimeTypeAdapter;
                out.putString(RUN_TIME_KEY, chosen.getToken());
                runtimeAdapters.put(runtimeType.toString(), chosen);
            } else if (!(delegate instanceof ReflectiveTypeAdapterFactory.Adapter)) {
                // The user registered a type adapter for Base class, so we prefer it over the
                // reflective type adapter for the runtime type
                chosen = delegate;
            } else {
                // Use the type adapter for runtime type
                chosen = runtimeTypeAdapter;
                out.putString(RUN_TIME_TYPE, value.getClass().getName());
                runtimeAdapters.put(chosen.getToken(), chosen);
            }
        }
        chosen.write(out, key, value);
    }

    @Override
    public String getToken() {
        return TypeAdapterRuntimeTypeWrapper.class.getName();
    }

    /**
     * Finds a compatible runtime type if it is more specific
     */
    private Type getRuntimeTypeIfMoreSpecific(Type type, Object value) {
        if (value != null
                && (type == Object.class || type instanceof TypeVariable<?> || type instanceof Class<?>)) {
            type = value.getClass();
        }
        return type;
    }
}