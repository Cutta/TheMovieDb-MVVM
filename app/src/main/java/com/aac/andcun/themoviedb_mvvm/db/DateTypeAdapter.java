package com.aac.andcun.themoviedb_mvvm.db;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by andani on 9/24/2017.
 */

public class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    private final DateFormat dateFormat;

    public DateTypeAdapter() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!(json instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }
        String dateString = json.getAsString();
        if (dateString.isEmpty())
            return null;
        else
            try {
                return dateFormat.parse(dateString);
            } catch (ParseException e) {
                throw new JsonSyntaxException(json.getAsString(), e);
            }
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        synchronized (dateFormat) {
            String dateFormatAsString = dateFormat.format(src);
            return new JsonPrimitive(dateFormatAsString);
        }
    }

}