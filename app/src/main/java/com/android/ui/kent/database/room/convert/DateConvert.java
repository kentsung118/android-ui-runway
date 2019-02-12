package com.android.ui.kent.database.room.convert;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Kent Song on 2019/2/11.
 */
public class DateConvert {

    @TypeConverter
    public static Date revertDate(long value) {
        return new Date(value);
    }

    @TypeConverter
    public static long converterDate(Date value) {
        return value.getTime();
    }
}
