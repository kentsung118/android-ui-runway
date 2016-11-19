package com.android.ui.kent.demo.network.rxjava;

import com.android.ui.kent.demo.network.retrofit.vo.User;

/**
 * Created by Kent on 2016/11/19.
 */

public class UserAndEvents {
    public UserAndEvents(User user, User events) {
        this.events = events;
        this.user = user;
    }

    public User events;
    public User user;
}
