package com.android.ui.kent.demo.blank

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import com.kent.android.slim.sample.R
import kotlinx.android.synthetic.main.activity_animation.*


/**
 * Created by Kent Sung on 2022/1/14.
 */
class AnimationActivityKt : AppCompatActivity() {
    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        init()
    }

    var animationDrawable: AnimationDrawable? = null

    private fun init() {
        image_gift.setImageResource(R.drawable.frameanimation);
        animationDrawable = image_gift.drawable as AnimationDrawable
        btn_start.setOnClickListener{
            animationDrawable!!.stop()
            animationDrawable!!.start()
        }
//        animationDrawable.isOneShot = true
//        animationDrawable.start()

//        image_gift.animation.setAnimationListener(object: Animation.AnimationListener {
//            override fun onAnimationStart(animation: Animation?) {
//                Log.d("lala", "flag1")
//            }
//
//            override fun onAnimationEnd(animation: Animation?) {
//                Log.d("lala", "flag2")
//
//            }
//
//            override fun onAnimationRepeat(animation: Animation?) {
//
//            }
//        })

    }
}