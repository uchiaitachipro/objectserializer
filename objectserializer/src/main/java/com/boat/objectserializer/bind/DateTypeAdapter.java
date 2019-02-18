package com.boat.objectserializer.bind;

import android.os.Bundle;
import android.text.TextUtils;


import com.boat.objectserializer.ObjectSerializer;
import com.boat.objectserializer.TypeAdapter;
import com.boat.objectserializer.TypeAdapterFactory;
import com.boat.objectserializer.TypeToken;
import com.boat.objectserializer.utils.ISO8601Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by uchia on 2019/2/13.
 */

public class DateTypeAdapter extends TypeAdapter<Date> {

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings("unchecked") // we use a runtime check to make sure the 'T's equal
        @Override public <T> TypeAdapter<T> create(ObjectSerializer cabin, TypeToken<T> typeToken) {
            return typeToken.getRawType() == Date.class ? (TypeAdapter<T>) new DateTypeAdapter() : null;
        }
    };

    /**
     * List of 1 or more different date formats used for de-serialization attempts.
     * The first of them (default US format) is used for serialization as well.
     */
    private final List<DateFormat> dateFormats = new ArrayList<DateFormat>();

    public DateTypeAdapter() {
        dateFormats.add(DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            dateFormats.add(DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT));
        }
//        if (JavaVersion.isJava9OrLater()) {
//            dateFormats.add(PreJava9DateFormatProvider.getUSDateTimeFormat(DateFormat.DEFAULT, DateFormat.DEFAULT));
//        }
    }

    private synchronized Date deserializeToDate(String date) {
        for (DateFormat dateFormat : dateFormats) {
            try {
                return dateFormat.parse(date);
            } catch (ParseException ignored) {}
        }
        try {
            return ISO8601Utils.parse(date, new ParsePosition(0));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void write(Bundle out, String key, Date value) {
        String dateFormatAsString = dateFormats.get(0).format(value);
        if (TextUtils.isEmpty(dateFormatAsString)){
            return;
        }
        TypeAdapters.STRING.write(out,key,dateFormatAsString);
    }

    @Override
    public Date read(Bundle in, String key) {
        String value = TypeAdapters.STRING.read(in,key);
        if (TextUtils.isEmpty(value)){
            return null;
        }
        return deserializeToDate(value);
    }

    @Override
    public String getToken() {
        return DateTypeAdapter.class.getName();
    }
}
