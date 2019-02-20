package com.android.ui.kent.demo.udp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kent Song on 2019/2/19.
 */
public class PON_Contanst {

    //TV应用界面命令
    public byte[] data_0x01_PPPoE_Set = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x01}, sPonSuffix);
    public byte[] data_0x81_PPPoE_Get = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x81}, sPonSuffix);
    public byte[] data_0x02_WLAN_Set = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x02}, sPonSuffix);
    public byte[] data_0x82_WLAN_Get = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x82}, sPonSuffix);
    public byte[] data_0x03_admin_Set = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x03}, sPonSuffix);
    public byte[] data_0x83_admin_Get = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x83}, sPonSuffix);
    public byte[] data_0x04_Reset_ONU = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x04}, sPonSuffix);
    public byte[] data_0x05_RestoreWiFi = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x05}, sPonSuffix);
    public byte[] data_0x06_Connected = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x06}, sPonSuffix);
    public byte[] data_0x07_detect = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x07}, sPonSuffix);
    public byte[] data_0x88_Device_Status = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x88}, sPonSuffix);
    public byte[] data_0x89_Optical_Connect_status = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x89}, sPonSuffix);
    public byte[] data_0x0A_LAN_config_set = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x0A}, sPonSuffix);
    public byte[] data_0x8A_LAN_config_get = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x8A}, sPonSuffix);
    public byte[] data_0x8B_User_Host_List_get = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x8B}, sPonSuffix);
    public byte[] data_0x0D_Factorydiag = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x0D}, sPonSuffix);
    public byte[] data_0x8D_USBdiag = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x8D}, sPonSuffix);
    public byte[] data_0x8C_WiFiresetdiag = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x8C}, sPonSuffix);
    public byte[] data_0x8F_IIC_diag = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x8F}, sPonSuffix);
    public byte[] data_0x90_Ping_diag = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x90}, sPonSuffix);
    public byte[] data_0x0E_ONU_MAC_factory_Set = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x0E}, sPonSuffix);
    public byte[] data_0x0F_WIFI_MAC_factory_set = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x0F}, sPonSuffix);
    public byte[] data_0x10_WIFI_RSSI_Get = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x10}, sPonSuffix);
    public byte[] data_0x11_WiFi_set_diag = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x11}, sPonSuffix);
    public byte[] data_0x08_Dhcpconfig_set = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x08}, sPonSuffix);
    public byte[] data_0x8E_DHcpconfig_get = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x8E}, sPonSuffix);
    public byte[] data_0x91_ONU_MAC_get = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x91}, sPonSuffix);
    public byte[] data_0x92_WiFi_MAC_get = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x92}, sPonSuffix);
    public byte[] data_0x1F_STB_Standby_set = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x1F}, sPonSuffix);
    public byte[] data_0x1E_STB_Wake_set = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x1E}, sPonSuffix);

    //手机APK应用界面命令
    public byte[] data_0x31_WAN_Status_Get = byteMergerAll(sPonPrefix, new byte[]{(byte) 0x31}, sPonSuffix);


    private static byte[] sPonPrefix = {
            (byte) 0xaa,
            (byte) 0x12,
            (byte) 0x34,
            (byte) 0xbb,
            (byte) 0x10
    };

    private static byte[] sPonSuffix = {
            (byte) 0x00,
            (byte) 0x00,
            (byte) 0x00
    };

    private static byte[] byteMergerAll(byte[]... values) {
        int length_byte = 0;
        for (int i = 0; i < values.length; i++) {
            length_byte += values[i].length;
        }
        byte[] all_byte = new byte[length_byte];
        int countLength = 0;
        for (int i = 0; i < values.length; i++) {
            byte[] b = values[i];
            System.arraycopy(b, 0, all_byte, countLength, b.length);
            countLength += b.length;
        }
        return all_byte;
    }




}
