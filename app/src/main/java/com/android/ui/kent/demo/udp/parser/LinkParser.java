package com.android.ui.kent.demo.udp.parser;

import com.android.ui.kent.common.MathUtils;
import com.android.ui.kent.demo.udp.HexUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import timber.log.Timber;

/**
 * Created by Kent Song on 2019/2/22.
 */
public class LinkParser {

    static String errorStr = "解析失败";
    static DecimalFormat decimalFormat = new DecimalFormat("#.0");


    public static String parseReciveDbm(String hex, int begin, int end) {
        try {
            String hexOpticPower = hex.substring(10, 14);
            double ss = new BigDecimal(HexUtils.parseHex4(hexOpticPower) * 0.1).doubleValue();
            double dbm = new BigDecimal(MathUtils.log(ss, 10)).multiply(new BigDecimal(10)).add(new BigDecimal(-30)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            return dbm + " dbm";
        } catch (Exception e) {
            e.printStackTrace();
            return errorStr;
        }

    }

    public static String parseLinkStatus(String hex, int begin, int end) {

        try {
            String linkStatus = "";
            String hexLinkMillionStatus = hex.substring(18, 20);
            Timber.d("hexLinkMillionStatus = " + hexLinkMillionStatus);
            int retLink = Integer.parseInt(hexLinkMillionStatus, 16);
            Timber.d(Integer.toBinaryString(retLink));
            Timber.d("is 万兆光模块存在 = " + ((retLink & 0x04) > 0));
            Timber.d("is 万兆光信号丢失 = " + ((retLink & 0x02) > 0));
            Timber.d("is 万兆连接正常 = " + ((retLink & 0x01) > 0));
            if ((retLink & 0x01) > 0) {
                linkStatus = "连接正常";
            } else if ((retLink & 0x02) > 0) {
                linkStatus = "光信号丢失";
            } else if ((retLink & 0x04) > 0) {
                linkStatus = "光模块存在";
            }
            return linkStatus;

        } catch (Exception e) {
            e.printStackTrace();
            return errorStr;
        }
    }
}
