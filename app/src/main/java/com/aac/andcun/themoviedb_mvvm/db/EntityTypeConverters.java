package com.aac.andcun.themoviedb_mvvm.db;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.util.StringUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by andani on 20.09.2017.
 */

public class EntityTypeConverters {

    @TypeConverter
    public static List<Integer> stringToIntList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        return StringUtil.splitToIntList(data);
    }

    @TypeConverter
    public static String intListToString(List<Integer> ints) {
        return StringUtil.joinIntoString(ints);
    }

    @TypeConverter
    public static Long dateToLong(Date date) {
        if (date == null)
            return 0L;
        return date.getTime();
    }

    @TypeConverter
    public static Date longToDate(Long time) {
        if (time == 0)
            return null;
        return new Date(time);
    }

}