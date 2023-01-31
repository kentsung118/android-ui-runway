package com.kent.android.slim.sample

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.ui.kent.demo.blank.AnimationActivityKt
import com.android.ui.kent.demo.framwork.okhttp.OkHttpClientActivity
import com.kent.android.slim.sample.letv.desktopmanager.DesktopManagerActivity
import com.kent.android.slim.sample.retorfit.RetrofitActivity
import com.kent.android.slim.sample.service.TickSingleton
import com.kent.android.slim.sample.share.ShareActivity
import com.kent.android.slim.sample.workmanager.WorkActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val mTitle = arrayOf(
        "StartApp",
        "Transition Drawable",
        "Constraint Layout",
        "DesktopManager",
        "WorkManager",
        "Room",
        "OkHttpClientActivity",
        "Third party Share",
        "Animation Drawable",
        "RetrofitActivity"
    )
    private val mClasses = arrayOf<Class<*>>(
        StartAppActivity::class.java,
        TransitionDrawableActivity::class.java,
        ConstransLayoutDemoActivity::class.java,
        DesktopManagerActivity::class.java,
        WorkActivity::class.java,
        RoomActivity::class.java,
        OkHttpClientActivity::class.java,
        ShareActivity::class.java,
        AnimationActivityKt::class.java,
        RetrofitActivity::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.setAdapter(ArrayAdapter(this, R.layout.item_list, R.id.tv_items, mTitle))
        initListener()

        Log.d("lala", "BRAND=${Build.BRAND}")
        Log.d("lala", "PRODUCT=${Build.PRODUCT}")
        Log.d("lala", "DEVICE=${Build.DEVICE}")
        Log.d("lala", "MODEL=${Build.MODEL}")
        Log.d("lala", "MANUFACTURER=${Build.MANUFACTURER}")
        Log.d("lala", "MANUFACTURER=${Build.DISPLAY}")

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                delay(5000)
                launch {
                    TickSingleton.tickHandler?.tickTimerFlow?.collect {
                        Log.d("lala", "MainActivity collect tickTimerFlow tick=$it")
                    }
                }

//                launch {
//                    TickSingleton.tickHandler?.tickFlow?.collect {
//                        Log.d("lala", "MainActivity collect tickFlow tick=$it")
//                    }
//                }

               Log.d("lala", "MainActivity flag1")
            }
        }



    }

    private fun initListener() {
        list.onItemClickListener = AdapterView.OnItemClickListener { adapterView: AdapterView<*>?, view: View?, i: Int, l: Long ->
            startActivity(
                Intent(this@MainActivity, mClasses[i])
            )
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("lala", "MainActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("lala", "MainActivity onDestroy")
    }
}
