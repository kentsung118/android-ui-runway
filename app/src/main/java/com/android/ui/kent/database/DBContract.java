package com.android.ui.kent.database;

/**
 * Created by Kent on 2017/10/11.
 */

public class DBContract {

    static final int MAJOR_VER = 1;
    static final int MINOR_VER = 0;
    static final int VERSION = MAJOR_VER * 1000 + MINOR_VER;
    static final String DATABASE_NAME = "kent.db";

    public static class TableUser {

        public static final String TABLE_NAME = "table_user";

        public static final String COL_ID = "_id";
        public static final String COL_USER_ID = "user_id";
        public static final String COL_USER_PWD = "user_pwd";
        public static final String COL_GENDER = "gender";
        public static final String COL_AGE = "age";
        public static final String COL_JSON = "json";
        public static final String COL_LAST_UPDATE = "last_update";

        public static final String SQL_CREATE_TABLE =
                String.format(
                        "CREATE TABLE %s ("                             // TABLE_NAME
                                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"  // COL_ID
                                + "%s TEXT, "                           // COL_USER_ID
                                + "%s TEXT, "                           // COL_USER_PWD
                                + "%s INTEGER default 0, "              // COL_GENDER
                                + "%s INTEGER default 20, "              // COL_AGE
                                + "%s TEXT, "                           // COL_JSON
                                + "%s TEXT"                             // COL_LAST_UPDATE
                                + ");",
                        TABLE_NAME, COL_ID, COL_USER_ID, COL_USER_PWD, COL_GENDER, COL_AGE,
                        COL_JSON, COL_LAST_UPDATE);

        public static final String SQL_DROP_TABLE = "DELETE FROM "+TABLE_NAME;
    }
}
