package com.android.ui.kent.demo.udp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.text.TextUtils;

import com.android.ui.kent.demo.udp.vo.LinkInfo;
import com.android.ui.kent.demo.udp.vo.PonInfo;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Kent Song on 2019/2/19.
 */
public class DeviceManager implements LifecycleObserver {

    private PonInfoListener mPonInfoListener;
    UdpUtils mPonUdp;
    UdpUtils mLiveUdp;
    PON_Contanst mPon = new PON_Contanst();


    public DeviceManager() {
        init();
    }

    private void init() {
        String ip = null;
        try {
            Process process = Runtime.getRuntime().exec("adb shell getprop dhcp.eth0.gateway");
            ip = IOUtils.toString(process.getInputStream(), "utf-8");
            Timber.d("ip = %s", ip);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(ip)) {
            throw new IllegalStateException("IP : getprop dhcp.eth0.gateway is null");
        }

        String thirdNum = ip.split("\\.")[2];
        mPonUdp = new UdpUtils("192.168." + thirdNum + ".1", 16888);
        mLiveUdp = new UdpUtils("192.168." + thirdNum + ".211", 65530);
    }

    public void requestPonInfo(PonInfoListener listener) {
        mPonInfoListener = listener;

        DecimalFormat decimalFormat = new DecimalFormat("#.0");

        int time = 200;
        PonInfo ponInfo = new PonInfo();
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .doOnNext(s -> {
                    mPonUdp.UdpSend(mPon.data_0x92_WiFi_MAC_get);
                    String hex = mPonUdp.UdpReceivePON();
                    Timber.d("hex = %s", hex);
                    if (!TextUtils.isEmpty(hex)) {
                        ponInfo.setWifyMac(PonConverter.formatMac(hex));
                    }
                })
                .delay(time, TimeUnit.MILLISECONDS)
                .doOnNext(s -> {
                    mPonUdp.UdpSend(mPon.data_0x91_ONU_MAC_get);
                    String hex = mPonUdp.UdpReceivePON();
                    ponInfo.setOnuMac(hex);
                })
                .delay(time, TimeUnit.MILLISECONDS)
                .doOnNext(s -> {
                    mPonUdp.UdpSend(mPon.data_0x89_Optical_Connect_status);
                    String hex = mPonUdp.UdpReceivePON();
                    if (!TextUtils.isEmpty(hex)) {
                        ponInfo.setPon(PonInfo.PON.newBuilder()
                                .setMac(PonConverter.formatMac(hex.substring(4, 16)))
                                .setTemperature(decimalFormat.format(PonConverter.parseHex4(hex.substring(18, 22)) * 0.1) + " 摄氏度")
                                .setVoltage(decimalFormat.format(PonConverter.parseHex4(hex.substring(22, 26)) * 0.1) + " V")
                                .setElectricCurrent(decimalFormat.format(PonConverter.parseHex4(hex.substring(26, 30)) * 0.1) + " V")
                                .setReciveOpticPower(decimalFormat.format(PonConverter.parseHex4(hex.substring(30, 34)) * 0.1) + " dBm")
                                .setSendOpticPower(decimalFormat.format(PonConverter.parseHex4(hex.substring(34, 38)) * 0.1) + " dBm")
                                .build());
                    }
                })
                .delay(time, TimeUnit.MILLISECONDS)
                .doOnNext(s -> {
                    mPonUdp.UdpSend(mPon.data_0x31_WAN_Status_Get);
                    String hex = mPonUdp.UdpReceivePON();
                    if (!TextUtils.isEmpty(hex)) {
                        ponInfo.setWan(PonInfo.WAN.newBuilder()
                                .setIp(PonConverter.formatIp(hex.substring(24, 32)))
                                .setSubMask(PonConverter.formatIp(hex.substring(32, 40)))
                                .setDns(PonConverter.formatIp(hex.substring(40, 48)))
                                .setGateway(PonConverter.formatIp(hex.substring(48, 56)))
                                .build());
                    }

                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> mPonInfoListener.onDataReceived(ponInfo))
                .doOnError(throwable -> mPonInfoListener.onError(throwable))
                .subscribe();

    }

    public void setPPPoe(String id, String pwd) {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MILLISECONDS)
                .doOnNext(s -> {
                    mPonUdp.UdpSend(mPon.data_0x81_PPPoE_Get);
                    String hex = mPonUdp.UdpReceivePON();
                    Timber.d("first PPPoE hex = " + hex);
                })
                .doOnNext(s -> {

                    String hexId = StringToHex.stringToHexString(id);
                    String hexPwd = StringToHex.stringToHexString(pwd);
                    String hexFullId = StringUtils.rightPad(hexId, 126, "0");
                    String hexFullPwd = StringUtils.rightPad(hexPwd, 60, "0");

                    Timber.d("hexFullId = " + hexFullId);
                    Timber.d("hexFullPwd = " + hexFullPwd);

                    PON_Contanst pon = new PON_Contanst();
                    byte[] bytes = PON_Contanst.byteMergerAll(pon.data_0x01_PPPoE_Set, StringToHex.hexStringToByteArray(hexFullId), StringToHex.hexStringToByteArray(hexFullPwd));
                    Timber.d("byteMergerHex = " + StringToHex.bytesToHexString(bytes));
                    mPonUdp.UdpSend(bytes);
                })
                .delay(1000, TimeUnit.MILLISECONDS)
                .doOnNext(s -> {
                    mPonUdp.UdpSend(mPon.data_0x81_PPPoE_Get);
                    String hex = mPonUdp.UdpReceivePON();
                    Timber.d("scoend PPPoE hex = " + hex);
                })
                .doOnError(throwable -> mPonInfoListener.onError(throwable))
                .subscribe();
    }

    public void requestLiveInfo(LinkInfoListener listener) {
        LinkInfo linkInfo = new LinkInfo();
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .doOnNext(s -> {
                    mLiveUdp.UdpSend(Live_Constant.sData);
                    String hex = mLiveUdp.UdpReceiveLive();
                    Timber.d("hex = %s", hex);
                    linkInfo.parseHex(hex);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> listener.onDataReceived(linkInfo))
                .doOnError(throwable -> listener.onError(throwable))
                .subscribe();
    }


    interface PonInfoListener {
        void onDataReceived(PonInfo ponInfo);

        void onError(Throwable throwable);
    }

    interface LinkInfoListener {
        void onDataReceived(LinkInfo linkInfo);

        void onError(Throwable throwable);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void destory() {
        if (mPonUdp != null) {
            mPonUdp.release();
        }

        if (mLiveUdp != null) {
            mLiveUdp.release();
        }
    }

}
