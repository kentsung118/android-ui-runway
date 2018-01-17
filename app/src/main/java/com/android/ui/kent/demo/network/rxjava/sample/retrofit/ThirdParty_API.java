package com.android.ui.kent.demo.network.rxjava.sample.retrofit;

import com.android.ui.kent.demo.network.rxjava.sample.vo.CancelProcessRes;
import com.android.ui.kent.demo.network.rxjava.sample.vo.MockReq;
import com.android.ui.kent.demo.network.rxjava.sample.vo.OrderProcessRes;
import com.android.ui.kent.demo.network.rxjava.sample.vo.QueryProcessRes;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Kent on 2018/1/17.
 */

public interface ThirdParty_API {

    /**
     * 第三方支付 ( 連線 ) 付款
     * @param body
     * @return
     */
    @POST("/OfflineTrans/OrderProcess")
    Observable<OrderProcessRes> orderProcess(@Body MockReq body);

    /**
     * 第三方支付 ( 連線 ) 取消
     * @param body
     * @return
     */
    @POST("/OfflineTrans/CancelProcess")
    Observable<CancelProcessRes> cancelProcess(@Body MockReq body);

    /**
     * 第三方支付 ( 連線 ) 查詢
     * @param body
     * @return
     */
    @POST("/OfflineTrans/QueryProcess")
    Observable<QueryProcessRes> queryProcess(@Body MockReq body);
}
