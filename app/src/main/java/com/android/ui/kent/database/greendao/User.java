package com.android.ui.kent.database.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.DaoException;

/**
 * Created by songzhukai on 2019-08-23.
 */

/**
 * schema：告知GreenDao当前实体属于哪个 schema
 * schema active：标记一个实体处于活跃状态，活动实体有更新、删除和刷新方法
 * nameInDb：在数据库中使用的别名，默认使用的是实体的类名，
 * indexes：定义索引，可以跨越多个列
 * createInDb：标记创建数据库表（默认：true）
 * generateConstructors 自动创建全参构造方法（同时会生成一个无参构造方法）（默认：true）
 * generateGettersSetters 自动生成 getters and setters 方法（默认：true）
 *
 * 作者：画星星高手
 * 链接：https://www.jianshu.com/p/bf3f360abb07
 * 来源：简书
 */
@Entity(
//        schema = "myschema",
        active = true,
        nameInDb = "AWESOME_USERS",
//        indexes = {
//                @Index(value = "name DESC", unique = true)
//        },
        createInDb = true,
        generateConstructors = false,
        generateGettersSetters = true
)
public class User {

    /**
     * 主键ID
     */
    @Id(autoincrement = true)
    private Long _id;

    private String name;

    private int age;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;

    public User() {
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }


    public Long get_id() {
        return this._id;
    }


    public void set_id(Long _id) {
        this._id = _id;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getAge() {
        return this.age;
    }


    public void setAge(int age) {
        this.age = age;
    }
}
