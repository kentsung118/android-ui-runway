package com.android.ui.kent.demo.udp.parser;

import com.android.ui.kent.demo.udp.HexUtils;

/**
 * Created by Kent Song on 2019/2/22.
 */
public class PPPoeParser {

    public static String parseHexString(String hex, int begin, int end) {
        try {
            return HexUtils.hexStringToString(hex.substring(begin, end)).replace("\0", "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
