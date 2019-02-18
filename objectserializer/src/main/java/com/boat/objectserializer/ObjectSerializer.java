package com.boat.objectserializer;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;

import com.boat.objectserializer.bind.ArrayTypeAdapter;
import com.boat.objectserializer.bind.CollectionTypeAdapterFactory;
import com.boat.objectserializer.bind.DateTypeAdapter;
import com.boat.objectserializer.bind.MapTypeAdapterFactory;
import com.boat.objectserializer.bind.ReflectiveTypeAdapterFactory;
import com.boat.objectserializer.bind.SqlDateTypeAdapter;
import com.boat.objectserializer.bind.TimeTypeAdapter;
import com.boat.objectserializer.bind.TypeAdapters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by uchia on 2019/2/12.
 */

public class ObjectSerializer {

    private static final String PREFIX = "1BoatCabin";
    private static final String MAGIC_NUMBER_KEY = "1mn";
    private static final byte NUMBER = 19;

    private static final TypeToken<?> NULL_KEY_SURROGATE = TypeToken.get(Object.class);

    /**
     * This thread local guards against reentrant calls to getAdapter(). In
     * certain object graphs, creating an adapter for a type may recursively
     * require an adapter for the same type! Without intervention, the recursive
     * lookup would stack overflow. We cheat by returning a proxy type adapter.
     * The proxy is wired up once the initial adapter has been created.
     */
    private final ThreadLocal<Map<TypeToken<?>, FutureTypeAdapter<?>>> calls
            = new ThreadLocal<Map<TypeToken<?>, FutureTypeAdapter<?>>>();

    private final ConstructorConstructor constructorConstructor;

    private final Map<TypeToken<?>, TypeAdapter<?>> typeTokenCache = new ConcurrentHashMap<TypeToken<?>, TypeAdapter<?>>();

    final List<TypeAdapterFactory> factories;

    final ArrayMap<String, TypeAdapter<?>> tokenOfTypeAdapter = new ArrayMap<>(64);

    public ObjectSerializer() {
        this(new ArrayMap<Type, InstanceCreator<?>>());
    }

    public ObjectSerializer(final Map<Type, InstanceCreator<?>> instanceCreators) {

        this.constructorConstructor = new ConstructorConstructor(instanceCreators);

        List<TypeAdapterFactory> factories = new ArrayList<TypeAdapterFactory>();

        // type adapters for basic platform types
        factories.add(TypeAdapters.STRING_FACTORY);
        factories.add(TypeAdapters.BOOLEAN_FACTORY);
        factories.add(TypeAdapters.BYTE_FACTORY);
        factories.add(TypeAdapters.SHORT_FACTORY);
        factories.add(TypeAdapters.INT_FACTORY);
        factories.add(TypeAdapters.LONG_FACTORY);
        factories.add(TypeAdapters.FLOAT_FACTORY);
        factories.add(TypeAdapters.DOUBLE_FACTORY);
        factories.add(TypeAdapters.CHAR_FACTORY);
        factories.add(TypeAdapters.CHAR_SEQUENCE_FACTORY);
        factories.add(TypeAdapters.PARCELABLE_FACTORY);

        factories.add(TypeAdapters.STRING_ARRAY_FACTORY);
        factories.add(TypeAdapters.BOOLEAN_ARRAY_FACTORY);
        factories.add(TypeAdapters.BYTE_ARRAY_FACTORY);
        factories.add(TypeAdapters.SHORT_ARRAY_FACTORY);
        factories.add(TypeAdapters.INT_ARRAY_FACTORY);
        factories.add(TypeAdapters.LONG_ARRAY_FACTORY);
        factories.add(TypeAdapters.FLOAT_ARRAY_FACTORY);
        factories.add(TypeAdapters.DOUBLE_ARRAY_FACTORY);
        factories.add(TypeAdapters.CHAR_ARRAY_FACTORY);
        factories.add(TypeAdapters.CHAR_SEQUENCE_ARRAY_FACTORY);
        factories.add(TypeAdapters.PARCELABLE_ARRAY_FACTORY);

        factories.add(TypeAdapters.WRAPPER_BOOLEAN_ARRAY_FACTORY);
        factories.add(TypeAdapters.WRAPPER_BYTE_ARRAY_FACTORY);
        factories.add(TypeAdapters.WRAPPER_SHORT_ARRAY_FACTORY);
        factories.add(TypeAdapters.INTEGER_ARRAY_FACTORY);
        factories.add(TypeAdapters.WRAPPER_LONG_ARRAY_FACTORY);
        factories.add(TypeAdapters.WRAPPER_FLOAT_ARRAY_FACTORY);
        factories.add(TypeAdapters.WRAPPER_DOUBLE_ARRAY_FACTORY);
        factories.add(TypeAdapters.WRAPPER_CHAR_ARRAY_FACTORY);

        factories.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
        factories.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
        factories.add(TypeAdapters.ATOMIC_LONG_FACTORY);
        factories.add(TypeAdapters.ATOMIC_LONG_ARRAY_FACTORY);
        factories.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
        factories.add(TypeAdapters.STRING_BUILDER_FACTORY);
        factories.add(TypeAdapters.STRING_BUFFER_FACTORY);
        factories.add(TypeAdapters.BIG_DECIMAL_FACTORY);
        factories.add(TypeAdapters.BIG_INTEGER_FACTORY);
        factories.add(TypeAdapters.URL_FACTORY);
        factories.add(TypeAdapters.URI_FACTORY);
        factories.add(TypeAdapters.UUID_FACTORY);
        factories.add(TypeAdapters.CURRENCY_FACTORY);
        factories.add(TypeAdapters.LOCALE_FACTORY);
        factories.add(TypeAdapters.INET_ADDRESS_FACTORY);
        factories.add(TypeAdapters.BIT_SET_FACTORY);
        factories.add(DateTypeAdapter.FACTORY);
        factories.add(TypeAdapters.CALENDAR_FACTORY);
        factories.add(TimeTypeAdapter.FACTORY);
        factories.add(SqlDateTypeAdapter.FACTORY);
        factories.add(TypeAdapters.TIMESTAMP_FACTORY);
        factories.add(ArrayTypeAdapter.FACTORY);
        factories.add(TypeAdapters.CLASS_FACTORY);

        // type adapters for composite and user-defined types
        factories.add(new CollectionTypeAdapterFactory(constructorConstructor));
        factories.add(new MapTypeAdapterFactory(constructorConstructor));
        factories.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor));
        factories.add(TypeAdapters.ENUM_FACTORY);

        this.factories = Collections.unmodifiableList(factories);

        tokenOfTypeAdapter.putAll(TypeAdapters.getTokenOfAdapterMap());
    }

    /**
     * Returns the type adapter for {@code} type.
     *
     * @throws IllegalArgumentException if this GSON cannot serialize and
     *                                  deserialize {@code type}.
     */
    public <T> TypeAdapter<T> getAdapter(Class<T> type) {
        return getAdapter(TypeToken.get(type));
    }

    /**
     * Returns the type adapter for {@code} type.
     *
     * @throws IllegalArgumentException if this GSON cannot serialize and
     *                                  deserialize {@code type}.
     */
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> getAdapter(TypeToken<T> type) {
        TypeAdapter<?> cached = typeTokenCache.get(type == null ? NULL_KEY_SURROGATE : type);
        if (cached != null) {
            return (TypeAdapter<T>) cached;
        }

        Map<TypeToken<?>, FutureTypeAdapter<?>> threadCalls = calls.get();
        boolean requiresThreadLocalCleanup = false;
        if (threadCalls == null) {
            threadCalls = new HashMap<TypeToken<?>, FutureTypeAdapter<?>>();
            calls.set(threadCalls);
            requiresThreadLocalCleanup = true;
        }

        // the key and value type parameters always agree
        FutureTypeAdapter<T> ongoingCall = (FutureTypeAdapter<T>) threadCalls.get(type);
        if (ongoingCall != null) {
            return ongoingCall;
        }

        try {
            FutureTypeAdapter<T> call = new FutureTypeAdapter<T>();
            threadCalls.put(type, call);

            for (TypeAdapterFactory factory : factories) {
                TypeAdapter<T> candidate = factory.create(this, type);
                if (candidate != null) {
                    call.setDelegate(candidate);
                    typeTokenCache.put(type, candidate);
                    return candidate;
                }
            }
            throw new IllegalArgumentException("Boat cabin cannot handle " + type);
        } finally {
            threadCalls.remove(type);

            if (requiresThreadLocalCleanup) {
                calls.remove();
            }
        }
    }

    public TypeAdapter<?> getAdapterByToken(String token) {
        if (TextUtils.isEmpty(token)) {
            return null;
        }

        return tokenOfTypeAdapter.get(token);
    }

    /**
     * this method not support generic params type or generic member in class
     *
     * @param object
     * @return
     */
    public Bundle toBundle(Object object) {
        return toBundle(object, object.getClass());
    }

    public Bundle toBundle(Object object, Type typeOfSrc) {
        Bundle bundle = new Bundle();
        bundle.putByte(MAGIC_NUMBER_KEY, NUMBER);
        TypeAdapter<?> adapter = getAdapter(TypeToken.get(typeOfSrc));
        ((TypeAdapter<Object>) adapter).write(bundle, PREFIX, object);
        return bundle;
    }

    public <T> T fromBundle(Bundle bundle, Class<T> classOfT) {
        Object object = fromBundle(bundle, (Type) classOfT);
        return Primitives.wrap(classOfT).cast(object);
    }

    public <T> T fromBundle(Bundle bundle, Type typeOfT) {
        byte magicNumber = bundle.getByte(MAGIC_NUMBER_KEY);
        if (magicNumber != NUMBER) {
            throw new IllegalArgumentException("this bundle isn't serialize by ObjectSerializer");
        }

        TypeToken<T> typeToken = (TypeToken<T>) TypeToken.get(typeOfT);
        TypeAdapter<T> typeAdapter = getAdapter(typeToken);
        try {
            T object = typeAdapter.read(bundle, PREFIX);
            return object;
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e);
        } catch (AssertionError e) {
            AssertionError error = new AssertionError("AssertionError : " + e.getMessage());
            error.initCause(e);
            throw error;
        }
    }

    public void registerInstanceCreator(Type type, InstanceCreator<?> creator) {
        constructorConstructor.addInstanceCreator(type, creator);
    }

    public void registerTypeAdapter(Type type, TypeAdapter<?> typeAdapter) {
        if (type == null || typeAdapter == null) {
            return;
        }
        factories.add(TypeAdapters.newFactory(TypeToken.get(type), (TypeAdapter) typeAdapter));
        tokenOfTypeAdapter.put(typeAdapter.getToken(), typeAdapter);
    }

    static class FutureTypeAdapter<T> extends TypeAdapter<T> {
        private TypeAdapter<T> delegate;

        public void setDelegate(TypeAdapter<T> typeAdapter) {
            if (delegate != null) {
                throw new AssertionError();
            }
            delegate = typeAdapter;
        }

        @Override
        public void write(Bundle out, String key, T value) {
            if (delegate == null) {
                throw new IllegalStateException();
            }
            delegate.write(out, key, value);
        }

        @Override
        public T read(Bundle in, String key) {
            if (delegate == null) {
                throw new IllegalStateException();
            }
            return delegate.read(in, key);
        }

        @Override
        public String getToken() {
            return delegate.getToken();
        }
    }

}
