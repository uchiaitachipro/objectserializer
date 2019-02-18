package com.boat.objectserializer;

/**
 * Created by uchia on 2019/2/12.
 */

public interface TypeAdapterFactory {
    /**
     * Returns a type adapter for {@code type}, or null if this factory doesn't
     * support {@code type}.
     */
    <T> TypeAdapter<T> create(ObjectSerializer cabin, TypeToken<T> type);
}
