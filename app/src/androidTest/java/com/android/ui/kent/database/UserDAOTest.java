package com.android.ui.kent.database;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.android.ui.kent.database.room.user.UserDAO;
import com.android.ui.kent.model.database.vo.UserVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Kent on 2017/10/11.
 */

@RunWith(AndroidJUnit4.class)
public class UserDAOTest {

    private UserDAO mDao;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDao = new UserDAO(context);
        mDao.delete(DBContract.TableUser.TABLE_NAME, null, null);
    }

    @Test
    public void getCount() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        UserVO user;

        map.put("userId", "Kent1");
        map.put("userPwd", "123456");
        user = createMockItem(map);
        mDao.insert(user);

        map.put("userId", "Kent2");
        map.put("userPwd", "123456");
        user = createMockItem(map);
        mDao.insert(user);

        map.put("userId", "Kent3");
        map.put("userPwd", "123456");
        user = createMockItem(map);
        mDao.insert(user);

        map.put("userId", "Kent4");
        map.put("userPwd", "123456");
        user = createMockItem(map);
        mDao.insert(user);


        map.put("userId", "Kent5");
        map.put("userPwd", "123456");
        map.put("gender", "1");
        map.put("age", "18");
        user = createMockItem(map);
        mDao.insert(user);

        assertThat(mDao.getCount(), is(5L));


    }

    @Test
    public void getCountByQuery() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        UserVO user;

        map.put("userId", "Kent1");
        map.put("userPwd", "123456");
        map.put("gender", "0");
        map.put("age", "18");
        user = createMockItem(map);
        mDao.insert(user);

        map.put("userId", "Kent2");
        map.put("userPwd", "123456");
        map.put("gender", "1");
        map.put("age", "18");
        user = createMockItem(map);
        mDao.insert(user);

        map.put("userId", "Kent3");
        map.put("userPwd", "123456");
        map.put("gender", "1");
        map.put("age", "18");
        user = createMockItem(map);
        mDao.insert(user);

        UserDAO.UserQuery userQuery = new UserDAO.UserQuery();
        userQuery.setGender(1);

        assertThat(mDao.getCountByQuery(userQuery), is(2L));
    }

    @Test
    public void getUserByQuery() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        UserVO user;

        map.put("userId", "Kent5");
        map.put("userPwd", "123456");
        map.put("gender", "1");
        map.put("age", "18");
        user = createMockItem(map);
        mDao.insert(user);

        UserDAO.UserQuery userQuery = new UserDAO.UserQuery();
        userQuery.setGender(1);
        userQuery.setAge(18);

        assertThat(mDao.getUserByQuery(userQuery).size(), is(1));
    }


    private UserVO createMockItem(HashMap<String, String> map) {

        //tmep值可作為預設資料
        String templateJson = "{\n"
                + "\"other_col1\": \"0000000000000\",\n"
                + "\"other_col2\": \"0000000000000\""
                + "}";

        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Gson gson = new Gson();
        Map<String, String> tempMap = gson.fromJson(templateJson, type);
        if (map != null) {
            for (String key : map.keySet()) {
                tempMap.put(key, map.get(key));
            }
        }

        String finalJsonStr = gson.toJson(map);
        return gson.fromJson(finalJsonStr, UserVO.class);
    }



}
