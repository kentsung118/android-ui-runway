package com.android.ui.kent.demo.udp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import timber.log.Timber;

/**
 * Created by Kent Song on 2019/2/18.
 */
public class UdpUtils implements LifecycleObserver {

//    private static final int localPort = 16888;//自己本地的端口
//    private static final int targetPort = 16888;//目标指定的接收端口
//    private static final String targetAddr = "192.168.1.1";//目标IP地址
//    private static final int byteSize = 128;//byte数组大小

    private int localPort;//自己本地的端口
    private int targetPort;//目标指定的接收端口
    private String targetAddr;//目标IP地址
    private static final int byteSize = 128;//byte数组大小

    private DatagramSocket socket = null;

    public UdpUtils(String ip, int port) {

        localPort = port;
        targetPort = port;
        targetAddr = ip;

        try {
            socket = new DatagramSocket(localPort);//若需要制定本地端口发送数据，则在此填入端口号
            socket.setSoTimeout(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用Upd进行发送消息
     *
     * @param data 要发送的数据
     */
    public void UdpSend(byte[] data) {

        try {
            String sendHexString = bytesToHex(data);
            Timber.d("sendHexString = %s", sendHexString);
            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(targetAddr), targetPort);
            socket.send(packet);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过Udp接收数据
     *
     * @return
     */
    public String UdpReceivePON() {
        String receiveStr = null;
        try {
            byte[] buf = new byte[byteSize];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            Timber.d("buf.length = %s", buf.length);
            String bytesHexStr = bytesToHex(buf);
            Timber.d("bytesHexString = %s", bytesHexStr);
            String txLengthHex = bytesHexStr.substring(16, 18);
            Timber.d("txLengthHex = %s", txLengthHex);
            int txLengthInt = Integer.parseInt(txLengthHex, 16);
            Timber.d("txLengthInt = %s", txLengthInt);
            String txHex = bytesHexStr.substring(18, 18 + txLengthInt * 2);
            Timber.d("txHex = %s", txHex);

            receiveStr = txHex;

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveStr;

    }

    /**
     * 通过Udp接收数据
     *
     * @return
     */
    public String UdpReceiveLive() {
        String receiveStr = null;
        try {
            byte[] buf = new byte[byteSize];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            Timber.d("buf.length = %s", buf.length);
            String bytesHexStr = bytesToHex(buf);

            receiveStr = bytesHexStr;

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveStr;

    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void destory() {
        if (socket != null) {
            socket.close();
        }
    }


}
