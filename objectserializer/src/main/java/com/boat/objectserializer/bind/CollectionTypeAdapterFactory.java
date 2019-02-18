package com.boat.objectserializer.bind;

import android.os.Bundle;
import android.os.Parcelable;

import com.boat.objectserializer.$Object$Types;
import com.boat.objectserializer.ObjectSerializer;
import com.boat.objectserializer.ConstructorConstructor;
import com.boat.objectserializer.ElementRecord;
import com.boat.objectserializer.ObjectConstructor;
import com.boat.objectserializer.TypeAdapter;
import com.boat.objectserializer.TypeAdapterFactory;
import com.boat.objectserializer.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by uchia on 2019/2/13.
 */

public class CollectionTypeAdapterFactory implements TypeAdapterFactory {

    private static final String PREFIX = "1C";

    private final ConstructorConstructor constructorConstructor;

    public CollectionTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    @Override
    public <T> TypeAdapter<T> create(ObjectSerializer cabin, TypeToken<T> typeToken) {
        Type type = typeToken.getType();

        Class<? super T> rawType = typeToken.getRawType();
        if (!Collection.class.isAssignableFrom(rawType)) {
            return null;
        }

        Type elementType = $Object$Types.getCollectionElementType(type, rawType);
        TypeAdapter<?> elementTypeAdapter = cabin.getAdapter(TypeToken.get(elementType));
        ObjectConstructor<T> constructor = constructorConstructor.get(typeToken);

        @SuppressWarnings({"unchecked", "rawtypes"}) // create() doesn't define a type parameter
                TypeAdapter<T> result = new Adapter(cabin, elementType, elementTypeAdapter, constructor).nullSafe();
        return result;
    }

    private static final class Adapter<E> extends TypeAdapter<Collection<E>>{

        private final TypeAdapter<E> elementTypeAdapter;
        private final ObjectConstructor<? extends Collection<E>> constructor;

        public Adapter(ObjectSerializer context, Type elementType,
                       TypeAdapter<E> elementTypeAdapter,
                       ObjectConstructor<? extends Collection<E>> constructor) {
            this.elementTypeAdapter =
                    new TypeAdapterRuntimeTypeWrapper<E>(context, elementTypeAdapter, elementType);
            this.constructor = constructor;
        }

        @Override
        public void write(Bundle out, String key, Collection<E> collection) {

            int length = collection.size();
            if (length <= 0) {
                return;
            }

            Object[] array = collection.toArray();
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
                E value = (E) array[i];
                elementTypeAdapter.write(element, PREFIX, value);
                elements[i] = element;
            }
            content.putParcelableArray(key,elements);

            out.putParcelable(key, content);
        }

        @Override
        public Collection<E> read(Bundle in, String key) {

            Collection<E> collection = constructor.construct();

            Bundle content = in.getParcelable(key);
            if (content == null){
                return collection;
            }
            content.setClassLoader(ElementRecord.class.getClassLoader());
            ElementRecord record = content.getParcelable(ElementRecord.META_DATA_KEY);
            if (record.getElementType() != ElementRecord.ELEMENT_ARRAY){
                return null;
            }

            int count = record.getElementCount();

            Parcelable[] elements = content.getParcelableArray(key);
            for (int i = 0; i < count; i++){
                Bundle bundle = (Bundle) elements[i];
                E instance = elementTypeAdapter.read(bundle,PREFIX);
                collection.add(instance);
            }
            return collection;
        }

        @Override
        public String getToken() {
            return CollectionTypeAdapterFactory.Adapter.class.getName();
        }
    }
}
