package com.android.ui.kent.database.room.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.Nullable;
import android.util.Log;
import com.android.ui.kent.common.BaseUtils;
import com.android.ui.kent.database.DBContentProvider;
import com.android.ui.kent.database.DBContract;
import com.android.ui.kent.database.DBHelper;
import com.android.ui.kent.model.database.vo.UserVO;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import static com.android.ui.kent.database.DBContract.TableUser.COL_AGE;
import static com.android.ui.kent.database.DBContract.TableUser.COL_GENDER;
import static com.android.ui.kent.database.DBContract.TableUser.COL_JSON;
import static com.android.ui.kent.database.DBContract.TableUser.COL_LAST_UPDATE;
import static com.android.ui.kent.database.DBContract.TableUser.COL_USER_ID;
import static com.android.ui.kent.database.DBContract.TableUser.COL_USER_PWD;
import static com.android.ui.kent.database.DBContract.TableUser.TABLE_NAME;

/**
 * Created by Kent on 2017/10/11.
 */

public class UserDAO extends DBContentProvider<UserVO> implements IUserDAO {

    private static final String TAG = UserDAO.class.getSimpleName();

    public UserDAO(Context context) {
        super(DBHelper.getDatabase(context));
    }

    @Override
    protected UserVO cursorToEntity(@Nullable Cursor cursor) {
        if (cursor == null) {
            return null;
        }

        String jsonStr = cursor.getString(idx(cursor, COL_JSON));
        return new Gson().fromJson(jsonStr, UserVO.class);
    }

    @Override
    protected ContentValues convertToContentValue(UserVO data) {
        ContentValues cv = new ContentValues();

        cv.put(COL_USER_ID, data.userId);
        cv.put(COL_USER_PWD, data.userPwd);
        cv.put(COL_GENDER, data.gender);
        cv.put(COL_AGE, data.age);

        String json = new Gson().toJson(data);
        cv.put(COL_JSON, json);

        cv.put(COL_LAST_UPDATE, BaseUtils.now("yyyyMMdd"));

        return cv;
    }

    private int idx(Cursor cursor, String colName) {
        return cursor.getColumnIndex(colName);
    }

    public long insert(UserVO userVO){
        return insert(DBContract.TableUser.TABLE_NAME, convertToContentValue(userVO));
    }

    public long getCount() {
        String query = String.format("SELECT COUNT(%s) FROM %s", COL_USER_ID, TABLE_NAME);
        SQLiteStatement statement = mDb.compileStatement(query);
        return statement.simpleQueryForLong();
    }

    @Override
    public long getCountByQuery(IUserDAO.UserQuery query) {
        String where = query.getWhere();
        String[] args = query.getArguments();

        StringBuilder sql = new StringBuilder();
        sql.append(String.format("SELECT COUNT(%s) FROM %s", COL_USER_ID, TABLE_NAME));
        if(where != null && args != null) {
            String formated_where = where.replace("?", "'%s'");
            String where_sql = String.format(formated_where, args);
            sql.append(String.format(" WHERE %s", where_sql));
        }

        System.out.println("SQL = "+sql.toString());
        SQLiteStatement statement = mDb.compileStatement(sql.toString());
        return statement.simpleQueryForLong();
    }

    @Override
    public List<UserVO> getUserByQuery(IUserDAO.UserQuery query) {
        String where = query.getWhere();
        String[] args = query.getArguments();
        String limit = query.getLimit();

        Log.d(TAG, "getProductByQuery: where = " + where);
        Log.d(TAG, "getProductByQuery: args = " + args);

        Cursor c = query(TABLE_NAME, null, where, args, null, limit);
        List<UserVO> pds = new ArrayList<>();
        while (c.moveToNext()) {
            pds.add(cursorToEntity(c));
        }
        return pds;
    }

    public static class UserQuery implements IUserDAO.UserQuery {

        LinkedHashMap<String, String> mMap = new LinkedHashMap<>();
        String limitStr = null;

        @Override
        public IUserDAO.UserQuery setGender(int gender) {
            mMap.put(COL_GENDER + " = ?", ""+gender);
            return this;
        }

        @Override
        public IUserDAO.UserQuery setAge(int age) {
            mMap.put(COL_AGE + " = ?", ""+age);
            return this;
        }

        @Override
        public String getWhere() {
            StringBuilder where = new StringBuilder();
            if (mMap.size() <= 0) {
                return null;
            }

            for (String key : mMap.keySet()) {
                where.append(" AND ");
                where.append(key);
            }

            where = new StringBuilder(where.substring(5));

            return where.toString();
        }

        @Override
        public String[] getArguments() {

            if (mMap.size() <= 0) {
                return null;
            }

            String[] args = new String[mMap.size()];
            args = mMap.values().toArray(args);

            return args;
        }

        @Override
        public IUserDAO.UserQuery setLimit(int begin, int end) {
            this.limitStr = begin + "," + end;
            return this;
        }

        @Override
        public String getLimit() {
            return this.limitStr;
        }
    }

    @Override
    public void clearAll() {
        mDb.execSQL(DBContract.TableUser.SQL_DELETE_TABLE);
    }

    @Override
    public void addRandomUser(int amount) {
        Random rand = new Random();

        for(int i=0;i<amount;i++){
            UserVO userVO = new UserVO();
            userVO.userId = "kent" + i;
            userVO.userPwd = "pwd" + rand.nextInt(9999);
            userVO.gender = rand.nextInt(1); // 0 or 1
            userVO.age = rand.nextInt(100) + 1; // 1~100

            this.insert(userVO);
        }
    }
}
