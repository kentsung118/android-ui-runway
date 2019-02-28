package com.android.ui.kent.demo.udp;

import android.text.TextUtils;

import com.android.ui.kent.common.MathUtils;
import com.android.ui.kent.demo.udp.vo.PonInfo;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Kent Song on 2019/2/21.
 */
public class DeviceManagerTest {

    @Test
    public void formatMac() {
        System.out.println(FormatUtils.formatMac("8C6D50D15BF8"));
        System.out.println(FormatUtils.formatMac("8C6D50D15BF8"));
    }

    @Test
    public void formatIp() {
        System.out.println(FormatUtils.formatIp("C0A89D6F"));
    }


    @Test
    public void getPonInfo() {
        DecimalFormat decimalFormat = new DecimalFormat("#.0");

        String hexWifiMAC = "8C6D50D15BF8";
        System.out.println("hexWifiMAC = " + FormatUtils.formatMac(hexWifiMAC));

//        String hexONUMAC = "474D54433530443135424638";
//        System.out.println("hexONUMAC = " + FormatUtils.formatMac(hexONUMAC));

        //PON
        String hexPonInfo = "02018C6D50D15BF823024800200069001BFF6B";
        System.out.println("------------------------------------------------");
        System.out.println("PonMAC = " + FormatUtils.formatMac(hexPonInfo.substring(4, 16)));
        System.out.println("PonTemperature = " + decimalFormat.format(HexUtils.parseHex4(hexPonInfo.substring(18, 22)) * 0.1) + " 摄氏度");
        System.out.println("PonVoltage = " + decimalFormat.format(HexUtils.parseHex4(hexPonInfo.substring(22, 26)) * 0.1) + " V");
        System.out.println("PonElectricCurrent = " + decimalFormat.format(HexUtils.parseHex4(hexPonInfo.substring(26, 30)) * 0.1) + " V");
        System.out.println("PonReciveOpticPower = " + decimalFormat.format(HexUtils.parseHex4(hexPonInfo.substring(30, 34)) * 0.1) + " dBm");
        System.out.println("PonSendOpticPower= " + decimalFormat.format(HexUtils.parseHex4(hexPonInfo.substring(34, 38)) * 0.1) + " dBm");


        //WAN
        String hex = "000000010000000000000001C0A89D6FFFFFFF80C0A89D010A01010D7272727200000001000000000000000000000000";
        System.out.println("------------------------------------------------");
        System.out.println("WanIp = " + FormatUtils.formatIp(hex.substring(24, 32)));
        System.out.println("WanSubMask = " + FormatUtils.formatIp(hex.substring(32, 40)));
        System.out.println("WanDns = " + FormatUtils.formatIp(hex.substring(40, 48)));
        System.out.println("WanGateway = " + FormatUtils.formatIp(hex.substring(48, 56)));

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
        double ss = new BigDecimal(HexUtils.parseHex4(hexOpticPower) * 0.1).doubleValue();
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


    /**
     * 明文：
     * account：s002、
     * pwd：1234
     **/
    @Test
    public void getPPPoE() {
        String hex = "7330303300000000000000000000000000000000000000000000000000000000000000000000000000000000" +
                "000000000000000000000000000000000000003132333400000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

        System.out.println("account hexString = " + hex.substring(0, 126));
        System.out.println("account String = " + HexUtils.hexStringToString(hex.substring(0, 126)).replace("\0", ""));
        System.out.println("pwd hexString = " + hex.substring(126, 186));
        System.out.println("pwd String = " + HexUtils.hexStringToString(hex.substring(126, 186)).replace("\0", ""));
    }

    @Test
    public void testHexToInt() {
        System.out.println(HexUtils.parseHex4("FF6E"));
        System.out.println(HexUtils.parseHex4("001B"));
    }

    @Test
    public void formatDate() throws ParseException {

        String date = "Sat Dec 22 16:00:48 CST 2018";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date d = sdf.parse(date);
        String formatDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(d);

        System.out.println(formatDate);
    }
}