package com.boat.objectserializer.bind;

import android.os.Bundle;
import android.text.TextUtils;

import com.boat.objectserializer.ObjectSerializer;
import com.boat.objectserializer.TypeAdapter;
import com.boat.objectserializer.TypeAdapterFactory;
import com.boat.objectserializer.TypeToken;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Adapter for java.sql.Date. Although this class appears stateless, it is not.
 * DateFormat captures its time zone and locale when it is created, which gives
 * this class state. DateFormat isn't thread safe either, so this class has
 * to synchronize its read and write methods.
 */
public final class SqlDateTypeAdapter extends TypeAdapter<Date> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings("unchecked") // we use a runtime check to make sure the 'T's equal
        @Override
        public <T> TypeAdapter<T> create(ObjectSerializer gson, TypeToken<T> typeToken) {
            return typeToken.getRawType() == Date.class
                    ? (TypeAdapter<T>) new SqlDateTypeAdapter().nullSafe() : null;
        }
    };

    private final DateFormat format = new SimpleDateFormat("MMM d, yyyy");

    @Override
    public synchronized Date read(Bundle in, String key) {

        String value = TypeAdapters.STRING.read(in, key);

        if (TextUtils.isEmpty(value)) {
            return null;
        }


        try {
            final long utilDate = format.parse(value).getTime();
            return new Date(utilDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getToken() {
        return SqlDateTypeAdapter.class.getName();
    }

    @Override
    public synchronized void write(Bundle out, String key, Date value) {
        TypeAdapters.STRING.write(out, key, format.format(value));
    }
}
