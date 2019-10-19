package com.android.ui.kent.demo.udp;


import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Kent Song on 2019/2/21.
 */
public class RegularUtilsTest {

    @Test
    public void validate() {
        Assert.assertFalse(RegularUtils.validateIPv4("aaaaaaa"));
        Assert.assertTrue(RegularUtils.validateIPv4("192.168.1.1"));
        Assert.assertTrue(RegularUtils.validateIPv4("10.1.1.1"));
    }
}