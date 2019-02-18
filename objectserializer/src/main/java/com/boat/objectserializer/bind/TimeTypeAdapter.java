package com.boat.objectserializer.bind;

import android.os.Bundle;
import android.text.TextUtils;

import com.boat.objectserializer.ObjectSerializer;
import com.boat.objectserializer.TypeAdapter;
import com.boat.objectserializer.TypeAdapterFactory;
import com.boat.objectserializer.TypeToken;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Adapter for Time. Although this class appears stateless, it is not.
 * DateFormat captures its time zone and locale when it is created, which gives
 * this class state. DateFormat isn't thread safe either, so this class has
 * to synchronize its read and write methods.
 */
public final class TimeTypeAdapter extends TypeAdapter<Time> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings("unchecked") // we use a runtime check to make sure the 'T's equal
        @Override
        public <T> TypeAdapter<T> create(ObjectSerializer cabin, TypeToken<T> typeToken) {
            return typeToken.getRawType() == Time.class ? (TypeAdapter<T>) new TimeTypeAdapter().nullSafe() : null;
        }
    };

    private final DateFormat format = new SimpleDateFormat("hh:mm:ss a");

    @Override
    public synchronized Time read(Bundle in, String key) {

        String value = TypeAdapters.STRING.read(in, key);

        if (TextUtils.isEmpty(value)) {
            return null;
        }

        try {
            Date date = format.parse(value);
            return new Time(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized void write(Bundle out, String key, Time value) {
        TypeAdapters.STRING.write(out, key, format.format(value));
    }

    @Override
    public String getToken() {
        return TimeTypeAdapter.class.getName();
    }
}