package com.android.ui.kent.demo.udp;

import com.android.ui.kent.common.MathUtils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Kent Song on 2019/2/19.
 */
public class PonConverterTest {

    @Test
    public void formatMac() {
        System.out.println(PonConverter.formatMac("8C6D50D15BF8"));
        System.out.println(PonConverter.formatMac("8C6D50D15BF8"));
    }

    @Test
    public void formatIp() {
        System.out.println(PonConverter.formatIp("C0A89D6F"));
    }

    @Test
    public void parseLiveResult() {

        DecimalFormat decimalFormat = new DecimalFormat("#.0");


        String hex = "9AA941223300570100D9";
        String hexLinkStatus = hex.substring(4, 6);
        System.out.println("hexLiveStatus = " + hexLinkStatus);
        int ret = Integer.parseInt(hexLinkStatus, 16);
        System.out.println(Integer.toBinaryString(ret));
        System.out.println("is 百兆连接 = " + ((ret & 0x02) > 0));
        System.out.println("is 千兆连接 = " + ((ret & 0x01) > 0));

        String hexTemperature = hex.substring(6, 10);
        System.out.println("hexTemperature = " + hexTemperature);

        int intTemperature = Integer.parseInt(hexTemperature, 16);
        System.out.println("温度 = " + decimalFormat.format(intTemperature / 256D));

        String hexOpticPower = hex.substring(10, 14);
        System.out.println("hexOpticPower = " + hexOpticPower);
        double ss = new BigDecimal(PonConverter.parseHex4(hexOpticPower) * 0.1).doubleValue();
        System.out.println("dbm = " + new BigDecimal(MathUtils.log(ss, 10)).multiply(new BigDecimal(10)).add(new BigDecimal(-30)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());

        String hexTerminalNum = hex.substring(14, 16);
        System.out.println("hexTerminalNum = " + hexTerminalNum);
        System.out.println("终端数 = " + Integer.parseInt(hexTerminalNum, 16));

        String hexFilterNum = hex.substring(16, 18);
        System.out.println("hexFilterNum = " + hexFilterNum);
        System.out.println("过滤器数 = " + Integer.parseInt(hexFilterNum, 16));

        String hexLinkMillionStatus = hex.substring(18, 20);
        System.out.println("hexLinkMillionStatus = " + hexLinkMillionStatus);
        int retLink = Integer.parseInt(hexLinkMillionStatus, 16);
        System.out.println(Integer.toBinaryString(retLink));
        System.out.println("is 万兆光模块存在 = " + ((retLink & 0x04) > 0));
        System.out.println("is 万兆光信号丢失 = " + ((retLink & 0x02) > 0));
        System.out.println("is 万兆连接正常 = " + ((retLink & 0x01) > 0));
    }


    @Test
    public void formatPPPoe() {
        String id = "a001";
        String pwd = "1234";
        String hexId = StringToHex.stringToHexString(id);
        String hexPwd = StringToHex.stringToHexString(pwd);
        System.out.println(hexId);
        System.out.println(hexPwd);


        byte[] buf = new byte[60];
        System.out.println(StringToHex.bytesToHexString(buf));
        byte[] idBytes = StringToHex.hexStringToByteArray(hexId);
        System.out.println(StringToHex.bytesToHexString(idBytes));

        String hexFullId = StringUtils.rightPad(hexId, 126, "0");
        String hexFullPwd = StringUtils.rightPad(hexPwd, 60, "0");

        System.out.println("hexFullId = " + hexFullId);
        System.out.println("hexFullPwd = " + hexFullPwd);

        PON_Contanst pon = new PON_Contanst();
        byte[] bytes = PON_Contanst.byteMergerAll(pon.data_0x01_PPPoE_Set, StringToHex.hexStringToByteArray(hexFullId), StringToHex.hexStringToByteArray(hexFullPwd));
        System.out.println("byteMergerHex = " + StringToHex.bytesToHexString(bytes));
    }


}