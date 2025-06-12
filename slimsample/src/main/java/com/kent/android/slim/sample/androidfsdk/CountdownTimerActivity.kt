package com.kent.android.slim.sample.androidfsdk

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.kent.android.slim.sample.BaseBindingActivity
import com.kent.android.slim.sample.R
import com.kent.android.slim.sample.databinding.ActivityCountdownTimerBinding
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Kent Sung on 2023/2/6.
 */
class CountdownTimerActivity : BaseBindingActivity<ActivityCountdownTimerBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityCountdownTimerBinding
        get() = ActivityCountdownTimerBinding::inflate


    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown_timer)


        var maxTimeSeconds = 20L
        val timer = object : CountDownTimer(maxTimeSeconds * 1000L, 1000L) {
            override fun onTick(p0: Long) {
                binding.timeText.text = convertMsToMinAndSecIn2DigitsFormat(maxTimeSeconds * 1000)
                maxTimeSeconds -= 1
            }

            override fun onFinish() {
                binding.timeText.text = "down"
            }

        }
        timer.start()


    }

    /**
     * @param duration in ms
     * @return min : sec (e.g. 00:03)
     */
    fun convertMsToMinAndSecIn2DigitsFormat(duration: Long): String? {
        val defaultDisplay = "00:00"
        return if (duration < 1000) {
            defaultDisplay
        } else try {
            val min = TimeUnit.MILLISECONDS.toMinutes(duration)
            val sec = TimeUnit.MILLISECONDS.toSeconds(duration) -
                    TimeUnit.MINUTES.toSeconds(min)
            String.format("%02d:%02d", min, sec)
        } catch (e: AbstractMethodError) {
            defaultDisplay
        } catch (e: Exception) {
            defaultDisplay
        }
    }
}