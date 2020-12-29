package com.android.ui.kent;

import org.junit.Test;

import android.util.SparseArray;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testSparseArray() throws Exception {

        SparseArray sparseArray = new SparseArray<String>();
        sparseArray.put(1, "kent");

        assertEquals("kent", sparseArray.get(1));
    }
}