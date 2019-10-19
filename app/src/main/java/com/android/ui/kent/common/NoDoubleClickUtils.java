package com.android.ui.kent.common;

/**
 * Created by Kent on 16/7/18.
 */
public class NoDoubleClickUtils {

    private static long lastClickTime;
    private final static int SPACE_TIME = 1500;

    public synchronized static boolean isDoubleClick() {
        return isDoubleClick(SPACE_TIME);
    }

    public synchronized static boolean isDoubleClick(int millisecond) {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        isClick2 = (currentTime - lastClickTime) <= millisecond;
        lastClickTime = currentTime;
        return isClick2;
    }
}
