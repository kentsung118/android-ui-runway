package com.android.ui.kent.demo.framwork.retrofit;

/**
 * Created by songzhukai on 2019-11-15.
 * [0] = @Query("ip") String ip
 * [1] = @Query("key") String key
 * 抽象類的自我實現類
 */
public abstract class ParameterHandler {

    abstract void apply(RequestBuilder builder, String value);

    static final class Query extends ParameterHandler {

        private String name;

        public Query(String name) {
            //參數名
            this.name = name;
        }

        @Override
        void apply(RequestBuilder builder, String value) {
            //value 是參數值
            if(value == null) return;
            System.out.println("apply @Query name="+name+", value="+value);
            builder.addQueryParam(name, value);

        }
    }

    static final class Field extends ParameterHandler {

        private String name;

        public Field(String name) {
            //參數名
            this.name = name;
        }

        @Override
        void apply(RequestBuilder builder, String value) {
            //value 是參數值
            if(value == null) return;
            System.out.println("apply @Field name="+name+", value="+value);
            builder.addFormField(name, value);
        }
    }
}
