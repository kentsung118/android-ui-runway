package com.android.ui.kent.demo.udp;

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
public class UdpUtils {

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
     * 使用 Upd 进行发送消息
     *
     * @param data 要发送的数据
     */
    public void UdpSend(byte[] data) {

        try {
            String sendHexString = HexUtils.bytesToHex(data);
            Timber.d("udp/send/ip=%s, port=%s,  hex=%s", targetAddr, targetPort, sendHexString);
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
     * 通过 Udp 接收数据
     *
     * @return
     */
    public String UdpReceivePON() {
        String receiveStr = null;
        try {
            byte[] buf = new byte[byteSize];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            String bytesHexStr = HexUtils.bytesToHex(buf);
            Timber.d("udp/receive/ip=%s, port=%s, bytesLength =%s, hex=%s", targetAddr, targetPort, buf.length, bytesHexStr);
            String txLengthHex = bytesHexStr.substring(16, 18);
            int txLengthInt = Integer.parseInt(txLengthHex, 16);
            String txHex = bytesHexStr.substring(18, 18 + txLengthInt * 2);

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
            String bytesHexStr = HexUtils.bytesToHex(buf);

            receiveStr = bytesHexStr;

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receiveStr;

    }


    public void release() {
        if (socket != null) {
            socket.close();
        }
    }

}
