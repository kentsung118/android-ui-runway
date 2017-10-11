package com.android.ui.kent.database.user;

import com.android.ui.kent.model.database.vo.UserVO;
import java.util.List;

/**
 * Created by Kent on 2017/10/11.
 */

public interface IUserDAO {



    /** 查詢會員數量 **/
    long getCount();
    long getCountByQuery(UserQuery query);

    /** 查詢會員 **/
    List<UserVO> getUserByQuery(UserQuery query);



    interface UserQuery{

        UserQuery setGender(int gender);
        UserQuery setAge(int age);

        String getWhere();
        String[] getArguments();
    }

}
