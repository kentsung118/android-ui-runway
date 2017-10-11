package com.android.ui.kent.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kent on 16/7/21.
 */
public class BaseUtils {
    //private static final String TAG = BaseUtils.class.getSimpleName();

    public static final String DB_DATE_FORMAT = "yyyy/MM/dd";

    public static String now() {
        return now(DB_DATE_FORMAT);
    }

    public static String now(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }



}
