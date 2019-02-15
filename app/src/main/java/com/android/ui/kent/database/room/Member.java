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

    public Member() {
    }

    private Member(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setAge(builder.age);
        setBirthday(builder.birthday);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

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


    public static final class Builder {
        private int id;
        private String name;
        private int age;
        private Date birthday;

        private Builder() {
        }

        public Builder setId(int val) {
            id = val;
            return this;
        }

        public Builder setName(String val) {
            name = val;
            return this;
        }

        public Builder setAge(int val) {
            age = val;
            return this;
        }

        public Builder setBirthday(Date val) {
            birthday = val;
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }
}
