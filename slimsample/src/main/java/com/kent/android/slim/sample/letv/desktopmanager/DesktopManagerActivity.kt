package com.kent.android.slim.sample.letv.desktopmanager

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kent.android.slim.sample.R
import com.kent.android.slim.sample.letv.desktopmanager.bean.ScreenInfo
import kotlinx.android.synthetic.main.activity_desktop_manager.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by songzhukai on 2020/12/28.
 */
class DesktopManagerActivity : AppCompatActivity() {

    val TAG =  DesktopManagerActivity::class.simpleName

    lateinit var mScreenInfos :ArrayList<ScreenInfo>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desktop_manager)

        initData()
    }

    fun initData(){
        mScreenInfos = XmlLoader.loadScreenInfoFromXML(this, R.xml.default_screens_cibn)
        Log.d(TAG,"mScreenInfos size = ${mScreenInfos.size}")

        mScreenInfos[10].locked = true


        var inUseAdapter = ScreenAdapter(mScreenInfos, this)
        InUseRv.layoutManager = GridLayoutManager(this, 7)

        InUseRv.adapter = inUseAdapter


    }
}