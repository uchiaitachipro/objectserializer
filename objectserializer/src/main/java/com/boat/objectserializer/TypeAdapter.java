package com.boat.objectserializer;

import android.os.Bundle;
import android.text.TextUtils;

/**
 * Created by uchia on 2019/1/24.
 */

public abstract class TypeAdapter<T> {

    public abstract void write(Bundle out,String key, T value);

    public abstract T read(Bundle in,String key);

    /**
     * represent TypeAdapter by string design for multi process
     * @return
     */
    public abstract String getToken();

    public final TypeAdapter<T> nullSafe() {
        return new TypeAdapter<T>() {
            @Override
            public void write(Bundle out,String key, T value) {
                if (value == null || TextUtils.isEmpty(key) || out == null) {
                    return;
                } else {
                    TypeAdapter.this.write(out,key, value);
                }
            }

            @Override
            public T read(Bundle in,String key) {
                if (in == null || TextUtils.isEmpty(key)) {
                    return null;
                }
                return TypeAdapter.this.read(in,key);
            }

            @Override
            public String getToken() {
                return TypeAdapter.this.getToken();
            }
        };
    }

}
