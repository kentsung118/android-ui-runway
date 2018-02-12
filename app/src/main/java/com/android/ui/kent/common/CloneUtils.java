package com.android.ui.kent.common;

import com.rits.cloning.Cloner;

/**
 * Created by Kent on 2018/2/12.
 */

public class CloneUtils<T> {

    public T deepClone(T obj){
        Cloner cloner = new Cloner();
        return cloner.deepClone(obj);
    }



    public static class Foo {

        public String id;
        public UserInfo userInfo;

    }

    public static class UserInfo {

        public String name;
        public String pwd;
    }

}
