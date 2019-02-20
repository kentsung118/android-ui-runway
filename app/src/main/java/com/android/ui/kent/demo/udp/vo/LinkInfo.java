package com.android.ui.kent.demo.udp.vo;

import com.android.ui.kent.common.MathUtils;
import com.android.ui.kent.demo.udp.PonConverter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import timber.log.Timber;

/**
 * Created by Kent Song on 2019/2/20.
 * 万兆网路信息
 */
public class LinkInfo {

    private String receiveDbm = ""; //接收功率
    private String linkStatus = ""; //连接状态

    private String mHexStr;

    public void parseHex(String mHexStr) {
        Timber.d("hex = %s", mHexStr);
        this.mHexStr = mHexStr;
        parseReciveDbm();
        parseLinkStatus();
    }

    public String getReceiveDbm() {
        return receiveDbm;
    }

    public String getLinkStatus() {
        return linkStatus;
    }

    private void parseReciveDbm() {
        String hexOpticPower = mHexStr.substring(10, 14);
        Timber.d("hexOpticPower = " + hexOpticPower);
        double ss = new BigDecimal(PonConverter.parseHex4(hexOpticPower) * 0.1).doubleValue();
        double dbm = new BigDecimal(MathUtils.log(ss, 10)).multiply(new BigDecimal(10)).add(new BigDecimal(-30)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        Timber.d("dbm = " + dbm);
        receiveDbm = dbm + " dbm";
    }

    private void parseLinkStatus() {
        String hexLinkMillionStatus = mHexStr.substring(18, 20);
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
    }

    private void parseOther() {
        DecimalFormat mFormat = new DecimalFormat("#.0");


        String hexLinkStatus = mHexStr.substring(4, 6);
        System.out.println("hexLiveStatus = " + hexLinkStatus);
        int ret = Integer.parseInt(hexLinkStatus, 16);
        System.out.println(Integer.toBinaryString(ret));
        System.out.println("is 百兆连接 = " + ((ret & 0x02) > 0));
        System.out.println("is 千兆连接 = " + ((ret & 0x01) > 0));

        String hexTemperature = mHexStr.substring(6, 10);
        System.out.println("hexTemperature = " + hexTemperature);

        int intTemperature = Integer.parseInt(hexTemperature, 16);
        System.out.println("温度 = " + mFormat.format(intTemperature / 256D));


        String hexTerminalNum = mHexStr.substring(14, 16);
        System.out.println("hexTerminalNum = " + hexTerminalNum);
        System.out.println("终端数 = " + Integer.parseInt(hexTerminalNum, 16));

        String hexFilterNum = mHexStr.substring(16, 18);
        System.out.println("hexFilterNum = " + hexFilterNum);
        System.out.println("过滤器数 = " + Integer.parseInt(hexFilterNum, 16));


    }


}
