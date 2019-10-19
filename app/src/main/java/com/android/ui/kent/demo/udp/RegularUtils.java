package com.android.ui.kent.demo.udp;

import java.util.regex.Pattern;

/**
 * Created by Kent Song on 2019/2/21.
 */
public class RegularUtils {
    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public static boolean validateIPv4(final String ip) {
        return PATTERN.matcher(ip).matches();
    }

}
