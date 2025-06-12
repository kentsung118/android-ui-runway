package com.android.ui.kent.demo.edittext

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import com.kent.android.slim.sample.BaseBindingActivity
import com.kent.android.slim.sample.R
import com.kent.android.slim.sample.databinding.ActivityEditTextBinding


/**
 * Created by Kent Sung on 2022/1/14.
 */
class EditTextActivity :  BaseBindingActivity<ActivityEditTextBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityEditTextBinding
        get() = ActivityEditTextBinding::inflate

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_text)
        init()
    }

    private fun init() {
        val watcher = object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("lala","beforeTextChanged, $s, start=$start, count=$count, after=$after")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("lala","onTextChanged, $s, start=$start, before=$before, count=$count")
                binding.textPreview.text = s
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d("lala","afterTextChanged, $s")
            }
        }
        binding.editText.addTextChangedListener(watcher)
        binding.editText.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                binding.editText.postDelayed({
                    // should post delay to get cursor
                    val cursorPosition: Int = binding.editText.getSelectionStart()
                    Log.d("lala","cursorPosition, $cursorPosition")
                },100)
            }
            false
        })
    }
}