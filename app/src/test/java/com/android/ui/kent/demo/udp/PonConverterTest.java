package com.android.ui.kent.demo.udp;

import org.junit.Test;

import static org.junit.Assert.*;

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
    public void formatIp(){
        System.out.println(PonConverter.formatIp("C0A89D6F"));
    }
}