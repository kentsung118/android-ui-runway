package com.android.ui.kent.database.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.android.ui.kent.demo.network.retrofit.vo.User;

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
