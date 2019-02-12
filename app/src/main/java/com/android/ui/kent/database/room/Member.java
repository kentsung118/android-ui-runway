package com.android.ui.kent.database.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.android.ui.kent.database.room.convert.DateConvert;

import java.util.Date;

/**
 * Created by Kent Song on 2019/2/11.
 */
@Entity
@TypeConverters(DateConvert.class)
public class Member {

    public static final String TABLE_NAME = "member";

    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private int id;
    private String name;
    private int age;
    private Date birthday;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
