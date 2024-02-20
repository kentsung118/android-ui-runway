package com.kent.android.slim.sample.audio

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioRecord
import android.media.AudioTrack
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_audio.btn_media_player_palyWav
import kotlinx.android.synthetic.main.activity_audio.btn_palyWav
import kotlinx.android.synthetic.main.activity_audio.btn_pcmToWav
import kotlinx.android.synthetic.main.activity_audio.btn_start
import kotlinx.android.synthetic.main.activity_audio.btn_stop
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


/**
 * Created by Kent Sung on 2024/2/5.
 */
class AudioActivity : AppCompatActivity() {

    val TAG = "lala"
    var audioRecord: AudioRecord? = null // 声明 AudioRecord 对象

    // 采样率，现在能够保证在所有设备上使用的采样率是44100Hz, 但是其他的采样率（22050, 16000, 11025）在一些设备上也可以使用。
    val SAMPLE_RATE_INHZ = 44100

    // 声道数。CHANNEL_IN_MONO and CHANNEL_IN_STEREO. 其中CHANNEL_IN_MONO是可以保证在所有设备能够使用的。
    val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_STEREO

    // 返回的音频数据的格式。 ENCODING_PCM_8BIT, ENCODING_PCM_16BIT, and ENCODING_PCM_FLOAT.
    val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT

    /**
     * 录音的工作线程
     */
    var recordingAudioThread: Thread? = null
    var isRecording = false //mark if is recording
    var pcmPath: String? = null


    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.kent.android.slim.sample.R.layout.activity_audio)
        init()
    }

    private fun init() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        btn_start.setOnClickListener {
            pcmPath = startRecordAudio()
            Toast.makeText(this, "開始錄音, filePath=$pcmPath", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "pcmPath=$pcmPath")
        }

        btn_stop.setOnClickListener {
            stopRecordAudio()
            Toast.makeText(this, "停止錄音", Toast.LENGTH_SHORT).show()
        }

        btn_pcmToWav.setOnClickListener {
            pcmPath?.let {
                pcmToWav(it)
            }
        }
        btn_palyWav.setOnClickListener {
            initTrackPlayer()
            playAudio()
        }
        btn_media_player_palyWav.setOnClickListener {
            val mediaPlayer = MediaPlayer()
            val filePath = getExternalFilesDir(Environment.DIRECTORY_PODCASTS).toString() + "/v1_welcome.wav"
            try {
                mediaPlayer.setDataSource(filePath)
                mediaPlayer.prepare()
                mediaPlayer.start()
                // 需要 release，為了簡便使用先不呼叫
//                mediaPlayer.release()
            } catch (e: IOException) {
                Toast.makeText(this, "請確認檔案是否存在 $filePath", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    /**
     * 开始录音，返回临时缓存文件（.pcm）的文件路径
     */
    private fun startRecordAudio(): String? {

        val audioCacheFilePath = getExternalFilesDir(Environment.DIRECTORY_MUSIC)!!.absolutePath + "/" + "kent.pcm"
        try {
            // 获取最小录音缓存大小，
            val minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_INHZ, CHANNEL_CONFIG, AUDIO_FORMAT)
            audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_INHZ, CHANNEL_CONFIG, AUDIO_FORMAT, minBufferSize)


            // 开始录音
            isRecording = true
            audioRecord?.startRecording()

            // 创建数据流，将缓存导入数据流
            recordingAudioThread = Thread(Runnable {
                val file = File(audioCacheFilePath)
                Log.i("lala", "audio cache pcm file path:$audioCacheFilePath")


                //以防万一，看一下这个文件是不是存在，如果存在的话，先删除掉
                if (file.exists()) {
                    file.delete()
                }
                try {
                    file.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                var fos: FileOutputStream? = null
                try {
                    fos = FileOutputStream(file)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    Log.e("lala", "临时缓存文件未找到")
                }
                if (fos == null) {
                    return@Runnable
                }
                val data = ByteArray(minBufferSize)
                var read: Int
                if (fos != null) {
                    while (isRecording && !recordingAudioThread!!.isInterrupted) {
                        read = audioRecord!!.read(data, 0, minBufferSize)
                        if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                            try {
                                fos.write(data)
                                Log.i("audioRecordTest", "写录音数据->$read")
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
                try {
                    // 关闭数据流
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            })
            recordingAudioThread?.start()
        } catch (e: IllegalStateException) {
            Toast.makeText(this, "需要获取录音权限！", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "需要获取录音权限！")
        } catch (e: SecurityException) {
            Toast.makeText(this, "需要获取录音权限！", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "需要获取录音权限！")
        }
        return audioCacheFilePath
    }

    /**
     * 停止录音
     */
    fun stopRecordAudio() {
        try {
            isRecording = false
            if (audioRecord != null) {
                audioRecord?.stop()
                audioRecord?.release()
                audioRecord = null
                recordingAudioThread?.interrupt()
                recordingAudioThread = null
                Log.d(TAG, "stopRecordAudio done")
            }
        } catch (e: Exception) {
            Log.w(TAG, e.localizedMessage)
        }
    }

    fun pcmToWav(pcmPath: String) {
        //wav文件的路径放在系统的音频目录下
        var wavFilePath = getExternalFilesDir(Environment.DIRECTORY_PODCASTS).toString() + "/wav_kent.wav"
        val ptwUtil = PcmToWavUtil()
        ptwUtil.pcmToWav(pcmPath, wavFilePath, true)
        Log.d(TAG, "pcmToWav done")
        Toast.makeText(this, "pcmToWav 成功 wavFilePath=$wavFilePath", Toast.LENGTH_SHORT).show()

    }

    private var audioTrack: AudioTrack? = null
    private var playbackThread: Thread? = null

    fun initTrackPlayer() {
        // 设置音频参数
        val sampleRate = 44100
        val channelConfig = AudioFormat.CHANNEL_IN_STEREO
        val audioFormat = AudioFormat.ENCODING_PCM_16BIT

        // 计算缓冲区大小
        val bufferSize = AudioTrack.getMinBufferSize(sampleRate, channelConfig, audioFormat)

        // 初始化 AudioTrack
        audioTrack = AudioTrack(
            AudioManager.STREAM_MUSIC,
            sampleRate,
            channelConfig,
            audioFormat,
            bufferSize,
            AudioTrack.MODE_STREAM
        )
    }

    private fun playAudio() {
        // 获取 WAV 数据的字节数组
        val audioData = getAudioByteArray() // 从你的 ByteArrayInputStream 中获取数据
        val base64String = Base64.encodeToString(audioData, Base64.DEFAULT)
        Log.d(TAG, "base64String -> ${base64String}")
        val byteSource = Base64.decode(base64String, Base64.DEFAULT)

        // 开启新线程播放音频
        playbackThread = Thread {
            try {
                // 开始播放
                audioTrack!!.play()

                // 写入数据到缓冲区
                audioTrack!!.write(byteSource, 0, byteSource.size)

                // 停止播放
                audioTrack!!.stop()
                audioTrack!!.release()
                audioTrack = null
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        playbackThread!!.start()
    }

//    fun convertBinaryToBase64(file: File): String? {
//        return try {
//            // 读取二进制文件内容到字节数组
//            val inputStream = FileInputStream(file)
//            val buffer = ByteArray(file.length().toInt())
//            inputStream.read(buffer)
//            inputStream.close()
//
//            // 使用 Base64 编码器将字节数组转换为 Base64 字符串
////            Base64.encodeToString()
////            Base64.getEncoder().encodeToString(buffer)
//        } catch (e: IOException) {
//            e.printStackTrace()
//            null
//        }
//    }

//    private fun getAudioByteArray(bytes: ByteArray): ByteArray {
////        val filePath = getExternalFilesDir(Environment.DIRECTORY_PODCASTS).toString() + "/wav_kent.wav"
//        val file = File(bytes)
//        val inputStream = FileInputStream(file)
//        val byteArrayInputStream = BufferedInputStream(inputStream)
//        // 从你的 ByteArrayInputStream 中获取数据并转为字节数组
//        // 这里假设你的 ByteArrayInputStream 叫做 byteArrayInputStream
//        val bufferSize: Int = byteArrayInputStream.available()
//        val buffer = ByteArray(bufferSize)
//        try {
//            byteArrayInputStream.read(buffer, 0, bufferSize)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        return buffer
//    }
    private fun getAudioByteArray(): ByteArray {
        val filePath = getExternalFilesDir(Environment.DIRECTORY_PODCASTS).toString() + "/wav_kent.wav"
        val file = File(filePath)
        val inputStream = FileInputStream(file)
        val byteArrayInputStream = BufferedInputStream(inputStream)
        // 从你的 ByteArrayInputStream 中获取数据并转为字节数组
        // 这里假设你的 ByteArrayInputStream 叫做 byteArrayInputStream
        val bufferSize: Int = byteArrayInputStream.available()
        val buffer = ByteArray(bufferSize)
        try {
            byteArrayInputStream.read(buffer, 0, bufferSize)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return buffer
    }

    override fun onDestroy() {
        super.onDestroy()
        // 停止播放并释放资源
        if (audioTrack != null) {
            audioTrack!!.stop()
            audioTrack!!.release()
        }
        if (playbackThread != null) {
            playbackThread!!.interrupt()
        }
    }


}