package com.android.ui.kent.demo.udp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kent Song on 2019/2/19.
 */
public class UdpUtilsTest {

    @Test
    public void testHexToInt(){
       System.out.println(parseHex4("FF6E"));
       System.out.println(parseHex4("001B"));
    }

    public static short parseHex4(String num) {
        if (num.length() != 4) {
            throw new NumberFormatException("Wrong length: " + num.length() + ", must be 4.");
        }
        int ret = Integer.parseInt(num, 16);
        ret = ((ret & 0x8000) > 0) ? (ret - 0x10000) : (ret);
        return (short) ret;
    }


}