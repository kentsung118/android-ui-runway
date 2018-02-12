package com.android.ui.kent.common;

import com.rits.cloning.Cloner;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Kent on 2018/2/12.
 */
public class CloneUtilsTest {

    @Test
    public void testDeepClone(){
        Cloner cloner =  new Cloner();

        CloneUtils.UserInfo user1 = new CloneUtils.UserInfo();
        user1.name = "A1";
        user1.pwd = "123456";

        CloneUtils.Foo foo1 = new CloneUtils.Foo();
        foo1.id = "300";
        foo1.userInfo = user1;

        CloneUtils.Foo foo2 = cloner.deepClone(foo1);
        foo1.id = "400";
        foo2.userInfo.name = "A2";

        Assert.assertNotEquals(foo1.hashCode(), foo2.hashCode());
        Assert.assertNotEquals(foo1.id, foo2.id);
        Assert.assertNotEquals(foo1.userInfo.name, foo2.userInfo.name);
        Assert.assertEquals(foo1.userInfo.pwd, foo2.userInfo.pwd);

    }


}