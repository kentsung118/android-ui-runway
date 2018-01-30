package com.android.ui.kent.demo.network.rxjava.sample.retrofit;

import com.android.ui.kent.demo.network.rxjava.sample.vo.CancelProcessRes;
import com.android.ui.kent.demo.network.rxjava.sample.vo.MockReq;
import com.android.ui.kent.demo.network.rxjava.sample.vo.OrderProcessRes;
import com.android.ui.kent.demo.network.rxjava.sample.vo.QueryProcessRes;
import io.reactivex.Observable;

/**
 * Created by Kent on 2018/1/17.
 */

public class ThirdPartyComm implements ThirdParty_API {

    @Override public Observable<OrderProcessRes> orderProcess(MockReq body) {
        return null;
    }

    @Override public Observable<CancelProcessRes> cancelProcess(MockReq body) {
        return null;
    }

    @Override public Observable<QueryProcessRes> queryProcess(MockReq body) {
        return null;
    }
}
