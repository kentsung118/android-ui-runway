package com.android.ui.kent.database.room;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import android.content.Context;

/**
 * Created by Kent Song on 2019/2/11.
 */
@Database(entities = {Member.class}, version = 2, exportSchema = false)
public abstract class MemberDb extends RoomDatabase {

    private static final String DB_NAME = "MemberDatabase.db";
    private static volatile MemberDb instance;

    public static synchronized MemberDb getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static MemberDb create(final Context context) {
        return Room.databaseBuilder(
                context,
                MemberDb.class,
                DB_NAME)
                .addMigrations(MIGRATION_1_2)
                .build();
    }

    public abstract MemberDao getMemberDao();

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(String.format("ALTER TABLE %s ADD COLUMN "
                    + "%s INTEGER DEFAULT 0 NOT NULL;", Member.TABLE_NAME, "birthday"));

        }
    };
}
