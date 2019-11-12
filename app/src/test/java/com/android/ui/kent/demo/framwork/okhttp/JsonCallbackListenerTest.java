package com.android.ui.kent.demo.framwork.okhttp;

import com.android.ui.kent.demo.framwork.okhttp.vo.Bean;
import com.google.gson.Gson;

import org.junit.Test;

/**
 * Created by songzhukai on 2019-11-13.
 */
public class JsonCallbackListenerTest {


    @Test
    public void test(){
        String json = "{\"error_code\":0,\"reason\":\"Return Successd!\",\"instr\":\"竹子\",\"outstr\":\"艸ふ\"}";
        Bean bean = new Gson().fromJson(json , Bean.class);
        System.out.println(bean.toString());
    }

}