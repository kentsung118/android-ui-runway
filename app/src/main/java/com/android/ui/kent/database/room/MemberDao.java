package com.android.ui.kent.database.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import android.database.Cursor;

import java.util.List;

/**
 * Created by Kent Song on 2019/2/11.
 */

@Dao
public interface MemberDao {

    @Query("SELECT * FROM Member")
    List<Member> getAllMembers();

    @Insert
    void insert(Member... members);

    @Update
    void update(Member... members);

    @Delete
    void delete(Member... members);

    @Query("SELECT * FROM Member WHERE id=:id")
    Member getMember(int id);

    @Query("SELECT * FROM Member")
    Cursor getUserCursor();

    @Query("SELECT * FROM Member WHERE age=:age")
    List<Member> getUsersByAge(int age);

    @Query("SELECT * FROM Member WHERE age=:age LIMIT :max")
    List<Member> getUsersByAge(int max, int... age);

    @Query("DELETE FROM Member")
    void clearAll();

}
