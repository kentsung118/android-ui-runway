package com.android.ui.kent.demo.network.rxjava.sample;

import com.android.ui.kent.demo.network.rxjava.sample.retrofit.ThirdPartyComm;

import static org.mockito.Mockito.mock;

/**
 * Created by Kent on 2018/1/17.
 */

public class DemoRxJava {

    //建立2個主線流程 ( 搭配模擬 Dialog 事件)

    /**
     *
     * 1. Observable ( 交易發起)
     *    Subscribe ( 監聽交易結果 )
     *
     * ## 以新光重慶第三方支付例子
     * start --> 交易發起 API --> 。成功   -->  紀錄交易結果  -->  結束
     *                       --> 。待驗證 -->  等待檢核 Dialog --> Observable (等待檢核)
     *                       --> 。失敗   -->  例外錯誤 Dialog --> Observable (錯誤處理)
     *
     */


    public static void main(String[] args) throws Exception{

        ThirdPartyComm comm = mock(ThirdPartyComm.class);
        //when(comm.orderProcess(new MockReq())).thenReturn("first");

    }




}
