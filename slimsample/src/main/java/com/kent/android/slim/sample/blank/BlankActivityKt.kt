package com.android.ui.kent.demo.blank

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kent.android.slim.sample.R

/**
 * Created by Kent Sung on 2022/1/14.
 */
class BlankActivityKt :  AppCompatActivity() {
    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_black)
        init()
    }

    private fun init() {

    }
}