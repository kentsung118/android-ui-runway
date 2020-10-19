package com.kent.android.slim.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mTitle = arrayOf(
            "StartApp",
            "Transition Drawable",
            "Constraint Layout"

    )
    private val mClasses = arrayOf<Class<*>>(
            StartAppActivity::class.java,
            TransitionDrawableActivity::class.java,
            ConstransLayoutDemoActivity::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.setAdapter(ArrayAdapter(this, R.layout.item_list, R.id.tv_items, mTitle))
        initListener()
    }

    private fun initListener() {
        list.onItemClickListener = AdapterView.OnItemClickListener { adapterView: AdapterView<*>?, view: View?, i: Int, l: Long ->
            startActivity(Intent(this@MainActivity, mClasses[i])
            )
        }
    }


}
