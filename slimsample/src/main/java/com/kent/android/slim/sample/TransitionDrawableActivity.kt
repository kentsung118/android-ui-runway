package com.kent.android.slim.sample

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.kent.android.slim.sample.databinding.ActivityMainBinding
import com.kent.android.slim.sample.databinding.ActivityTransitionDrawableBinding

/**
 * Created by songzhukai on 2020/9/27.
 */
class TransitionDrawableActivity  : BaseBindingActivity<ActivityTransitionDrawableBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityTransitionDrawableBinding
        get() = ActivityTransitionDrawableBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition_drawable)

        binding.btnStart.setOnClickListener {
            transitionBitmap()
        }


    }

    private fun transitionBitmap() {

        val view = binding.rootView

//        val drawable1: Drawable = BitmapDrawable(view.context.resources, bitmaps[0])
//        val drawable2: Drawable = BitmapDrawable(view.context.resources, bitmaps[1])

        val drawable1 = view.resources.getDrawable(R.drawable.transition_bg_1) as BitmapDrawable
        val drawable2 = view.resources.getDrawable(R.drawable.transition_bg_2) as BitmapDrawable


        val transitionDrawable = TransitionDrawable(arrayOf(drawable1, drawable2))
        view.background = transitionDrawable
        transitionDrawable.startTransition(1000)
    }



}