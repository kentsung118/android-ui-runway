package com.android.ui.kent.demo.udp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.text.TextUtils;

import com.android.ui.kent.demo.udp.parser.LinkParser;
import com.android.ui.kent.demo.udp.parser.PPPoeParser;
import com.android.ui.kent.demo.udp.parser.PonInfoParser;
import com.android.ui.kent.demo.udp.vo.LinkInfo;
import com.android.ui.kent.demo.udp.vo.PonInfo;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Kent Song on 2019/2/19.
 */
public class DeviceManager implements LifecycleObserver {

    private PonInfoListener mPonInfoListener;
    UdpUtils mPonUdp;
    UdpUtils mLiveUdp;
    UdpCommand.PON mPon = new UdpCommand.PON();
    UdpCommand.Link mLink = new UdpCommand.Link();

    public DeviceManager() {
        init();
    }

    private void init() {
        String ip = null;
        try {
            //嘗試獲取3次IP
            int i = 0;
            while (i++ < 3) {
                String tempIp = getEth0Gateway();
                Timber.d("deviceManager/tempIp = %s", tempIp);
                if (tempIp.startsWith("192.")) {
                    ip = tempIp;
                    break;
                }
            }
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

    private String getEth0Gateway() throws IOException {
        Process process = Runtime.getRuntime().exec("adb shell getprop dhcp.eth0.gateway");
        String result = IOUtils.toString(process.getInputStream(), "utf-8");
        Timber.d("deviceManager/dhcp.eth0.gateway = %s", result);
        return result;
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
                        ponInfo.setWifyMac(PonInfoParser.parseWifiMac(hex));
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
                                .setMac(PonInfoParser.parseWifiMac(hex, 4, 16))
                                .setTemperature(PonInfoParser.parseTemperature(hex, 18, 22))
                                .setVoltage(PonInfoParser.parseVoltage(hex, 22, 26))
                                .setElectricCurrent(PonInfoParser.parseElectricCurrent(hex, 26, 30))
                                .setReciveOpticPower(PonInfoParser.parseReciveOpticPower(hex, 30, 34))
                                .setSendOpticPower(PonInfoParser.parseSendOpticPower(hex, 34, 38))
                                .build());
                    }
                })
                .delay(time, TimeUnit.MILLISECONDS)
                .doOnNext(s -> {
                    mPonUdp.UdpSend(mPon.data_0x31_WAN_Status_Get);
                    String hex = mPonUdp.UdpReceivePON();
                    if (!TextUtils.isEmpty(hex)) {
                        ponInfo.setWan(PonInfo.WAN.newBuilder()
                                .setIp(PonInfoParser.parseIp(hex, 24, 32))
                                .setSubMask(PonInfoParser.parseIp(hex, 32, 40))
                                .setDns(PonInfoParser.parseIp(hex, 40, 48))
                                .setGateway(PonInfoParser.parseIp(hex, 48, 56))
                                .build());
                    }

                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> mPonInfoListener.onDataReceived(ponInfo))
                .doOnError(throwable -> mPonInfoListener.onError(throwable))
                .subscribe();

    }

    public void getPPPoE(PonInfoListener listener) {
        PonInfo ponInfo = new PonInfo();
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .doOnNext(s -> {
                    mPonUdp.UdpSend(mPon.data_0x81_PPPoE_Get);
                    String hex = mPonUdp.UdpReceivePON();
                    Timber.d("getPPPoE hex = %s", hex);
                    PonInfo.PPPoe ppPoe = new PonInfo.PPPoe();
                    ppPoe.setAccount(PPPoeParser.parseHexString(hex, 0, 126));
                    ppPoe.setPwd(PPPoeParser.parseHexString(hex, 126, 186));
                    ponInfo.setPppoe(ppPoe);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> listener.onDataReceived(ponInfo))
                .subscribe();
    }

    public void setPPPoe(String id, String pwd) {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .doOnNext(s -> {
                    String hexId = HexUtils.stringToHexString(id);
                    String hexPwd = HexUtils.stringToHexString(pwd);
                    String hexFullId = StringUtils.rightPad(hexId, 126, "0");
                    String hexFullPwd = StringUtils.rightPad(hexPwd, 60, "0");
                    byte[] bytes = UdpCommand.byteMergerAll(mPon.data_0x01_PPPoE_Set, HexUtils.hexStringToByteArray(hexFullId), HexUtils.hexStringToByteArray(hexFullPwd));
                    Timber.d("byteMergerHex = " + HexUtils.bytesToHexString(bytes));
                    mPonUdp.UdpSend(bytes);
                })
                .doOnError(throwable -> mPonInfoListener.onError(throwable))
                .subscribe();
    }

    public void requestLiveInfo(LinkInfoListener listener) {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .concatMap((Function<String, ObservableSource<String>>) s -> {
                    mLiveUdp.UdpSend(mLink.data_live_status);
                    String hex = mLiveUdp.UdpReceiveLive();
                    Timber.d("hex = %s", hex);
                    return Observable.just(hex);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(hex -> {
                    LinkInfo linkInfo = LinkInfo.newBuilder()
                            .setReceiveDbm(LinkParser.parseReciveDbm(hex, 10, 14))
                            .setLinkStatus(LinkParser.parseLinkStatus(hex, 18, 20))
                            .build();
                    listener.onDataReceived(linkInfo);
                })
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
