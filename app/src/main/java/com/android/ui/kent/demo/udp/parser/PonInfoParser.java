package com.android.ui.kent.demo.udp.parser;

import com.android.ui.kent.demo.udp.FormatUtils;
import com.android.ui.kent.demo.udp.HexUtils;

import java.text.DecimalFormat;

/**
 * Created by Kent Song on 2019/2/22.
 */
public class PonInfoParser {

    static String errorStr = "解析失败";
    static DecimalFormat decimalFormat = new DecimalFormat("#.0");

    public static String parseWifiMac(String hex) {
        try {
            return FormatUtils.formatMac(hex);
        } catch (Exception e) {
            e.printStackTrace();
            return errorStr;
        }
    }

    public static String parserPonStatus(String hex, int begin, int end) {
        try {
            String statusHex = hex.substring(begin, end);
            int num = Integer.parseInt(statusHex, 16);
            switch (num) {
                case 0:
                    return "未连接";
                case 1:
                    return "已连接";
                case 2:
                    return "已注册";
                default:
                    return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return errorStr;
        }
    }

    public static String parseWifiMac(String hex, int begin, int end) {
        try {
            return FormatUtils.formatMac(hex.substring(begin, end));
        } catch (Exception e) {
            e.printStackTrace();
            return errorStr;
        }
    }

    public static String parseTemperature(String hex, int begin, int end) {
        try {
            return decimalFormat.format(HexUtils.parseHex4(hex.substring(begin, end)) * 0.1) + " 摄氏度";
        } catch (Exception e) {
            e.printStackTrace();
            return errorStr;
        }
    }

    public static String parseVoltage(String hex, int begin, int end) {
        try {
            return decimalFormat.format(HexUtils.parseHex4(hex.substring(begin, end)) * 0.1) + " V";
        } catch (Exception e) {
            e.printStackTrace();
            return errorStr;
        }
    }

    public static String parseElectricCurrent(String hex, int begin, int end) {
        try {
            return decimalFormat.format(HexUtils.parseHex4(hex.substring(begin, end)) * 0.1) + " V";
        } catch (Exception e) {
            e.printStackTrace();
            return errorStr;
        }
    }

    public static String parseReciveOpticPower(String hex, int begin, int end) {
        try {
            return decimalFormat.format(HexUtils.parseHex4(hex.substring(begin, end)) * 0.1) + " dBm";
        } catch (Exception e) {
            e.printStackTrace();
            return errorStr;
        }
    }

    public static String parseSendOpticPower(String hex, int begin, int end) {
        try {
            return decimalFormat.format(HexUtils.parseHex4(hex.substring(begin, end)) * 0.1) + " dBm";
        } catch (Exception e) {
            e.printStackTrace();
            return errorStr;
        }
    }


    public static String parseIp(String hex, int begin, int end) {
        try {
            return FormatUtils.formatIp(hex.substring(begin, end));
        } catch (Exception e) {
            e.printStackTrace();
            return errorStr;
        }
    }

}
