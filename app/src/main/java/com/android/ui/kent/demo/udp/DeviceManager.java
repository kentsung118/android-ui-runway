package com.android.ui.kent.demo.udp;

import com.android.ui.kent.demo.udp.vo.LinkInfo;
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
    UdpUtils mPonUdp;
    UdpUtils mLiveUdp;
    PON_Contanst mPon = new PON_Contanst();


    public DeviceManager() {
        mPonUdp = new UdpUtils("192.168.1.1", 16888);
        mLiveUdp = new UdpUtils("192.168.1.211", 65530);
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
                    ponInfo.setWifyMac(PonConverter.formatMac(hex));
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
                    mPonUdp.UdpSend(mPon.data_0x31_WAN_Status_Get);
                    String hex = mPonUdp.UdpReceivePON();
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
                .subscribe();
    }


    interface PonInfoListener {
        void onDataReceived(PonInfo ponInfo);
    }

    interface LinkInfoListener {
        void onDataReceived(LinkInfo linkInfo);
    }

}
