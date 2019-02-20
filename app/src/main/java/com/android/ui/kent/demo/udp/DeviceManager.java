package com.android.ui.kent.demo.udp;

import com.android.ui.kent.demo.udp.vo.PonInfo;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Kent Song on 2019/2/19.
 */
public class DeviceManager {

    private PonInfoListener mPonInfoListener;
    UdpUtils mUdpUtils;
    PON_Contanst mPon = new PON_Contanst();


    public DeviceManager() {
        mUdpUtils = new UdpUtils();
    }

    public void requestPonInfo(PonInfoListener listener) {
        mPonInfoListener = listener;

        DecimalFormat decimalFormat = new DecimalFormat("#.0");

        int time = 200;
        PonInfo ponInfo = new PonInfo();
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .doOnNext(s -> {
                    mUdpUtils.UdpSend(mPon.data_0x92_WiFi_MAC_get);
                    String hex = mUdpUtils.UdpReceive();
                    Timber.d("hex = %s", hex);
                    ponInfo.setWifyMac(PonConverter.formatMac(hex));
                })
                .delay(time, TimeUnit.MILLISECONDS)
                .doOnNext(s -> {
                    mUdpUtils.UdpSend(mPon.data_0x91_ONU_MAC_get);
                    String hex = mUdpUtils.UdpReceive();
                    ponInfo.setOnuMac(hex);
                })
                .delay(time, TimeUnit.MILLISECONDS)
                .doOnNext(s -> {
                    mUdpUtils.UdpSend(mPon.data_0x89_Optical_Connect_status);
                    String hex = mUdpUtils.UdpReceive();
                    ponInfo.setPon(PonInfo.PON.newBuilder()
                            .setMac(PonConverter.formatMac(hex.substring(4, 16)))
                            .setTemperature(decimalFormat.format(PonConverter.parseHex4(hex.substring(18, 22)) * 0.1) + " 摄氏度")
                            .setVoltage(decimalFormat.format(PonConverter.parseHex4(hex.substring(22, 26)) * 0.1) + " V")
                            .setElectricCurrent(decimalFormat.format(PonConverter.parseHex4(hex.substring(26, 30)) * 0.1) + " V")
                            .setReciveOpticPower(decimalFormat.format(PonConverter.parseHex4(hex.substring(30, 34)) * 0.1) + " dBm")
                            .setSendOpticPower(decimalFormat.format(PonConverter.parseHex4(hex.substring(34, 38)) * 0.1) + " dBm")
                            .build());

                })
                .delay(time, TimeUnit.MILLISECONDS)
                .doOnNext(s -> {
                    mUdpUtils.UdpSend(mPon.data_0x31_WAN_Status_Get);
                    String hex = mUdpUtils.UdpReceive();
                    ponInfo.setWan(PonInfo.WAN.newBuilder()
                            .setIp(PonConverter.formatIp(hex.substring(24, 32)))
                            .setSubMask(PonConverter.formatIp(hex.substring(32, 40)))
                            .setDns(PonConverter.formatIp(hex.substring(40, 48)))
                            .setGateway(PonConverter.formatIp(hex.substring(48, 56)))
                            .build());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> mPonInfoListener.onDataReceived(ponInfo))
                .subscribe();

    }


    interface PonInfoListener {
        void onDataReceived(PonInfo ponInfo);
    }


}
