package com.android.ui.kent.database.room;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * Created by Kent Song on 2019/2/12.
 */
@RunWith(AndroidJUnit4.class)
public class MemberDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private MemberDb mDb;

    @Before
    public void setupDb() {
        mDb = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), MemberDb.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() {
        mDb.close();
    }

    @Test
    public void test_insert_one_raw_member() {
        MemberDao memberDao = mDb.getMemberDao();
        memberDao.clearAll();

        Member jack = Member.newBuilder()
                .setId(1)
                .setAge(18)
                .setName("Jack")
                .setBirthday(new Date())
                .build();

        memberDao.insert(jack);
        Member member = memberDao.getMember(1);

        //ASSERT
        Assert.assertEquals(member.getId(), jack.getId());
    }

}