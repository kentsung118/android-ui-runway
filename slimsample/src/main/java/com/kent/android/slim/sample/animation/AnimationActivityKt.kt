package com.android.ui.kent.demo.blank

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.kent.android.slim.sample.BaseBindingActivity
import com.kent.android.slim.sample.R
import com.kent.android.slim.sample.databinding.ActivityAnimationBinding


/**
 * Created by Kent Sung on 2022/1/14.
 */
class AnimationActivityKt : BaseBindingActivity<ActivityAnimationBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityAnimationBinding
        get() = ActivityAnimationBinding::inflate

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        init()
    }

    var animationDrawable: AnimationDrawable? = null

    private fun init() {

        binding.imageGift.setImageResource(R.drawable.frameanimation);
        animationDrawable = binding.imageGift.drawable as AnimationDrawable
        binding.btnStart.setOnClickListener{
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