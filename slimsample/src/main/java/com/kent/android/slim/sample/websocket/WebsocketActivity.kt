package com.kent.android.slim.sample.websocket

import android.annotation.SuppressLint
import android.app.Activity
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.kent.android.slim.sample.R
import kotlinx.android.synthetic.main.activity_websocket.btn_client_start
import kotlinx.android.synthetic.main.activity_websocket.btn_server_start
import kotlinx.android.synthetic.main.activity_websocket.btn_server_stop
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocketListener
import okio.ByteString
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit
import java.util.zip.GZIPInputStream


/**
 * Created by Kent Sung on 2024/1/30.
 */
class WebsocketActivity : AppCompatActivity() {

    companion object {
        const val tag = "websocket"
    }

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_websocket)

        startSocketServer()

        initAction()
    }

    private fun initAction() {
        btn_server_start.setOnClickListener {
            myWebsocketServer?.start()
        }

        btn_server_stop.setOnClickListener {
            myWebsocketServer?.stop()
            Toast.makeText(this, "server stop", Toast.LENGTH_SHORT).show()
        }

        btn_client_start.setOnClickListener {
            initPlayer()
            crateAiVLiverRWebSocket(this)
        }
    }


    private var myWebsocketServer: WebSocketServer? = null
    private fun startSocketServer() {
        // 192.168.1.101为安卓服务端，需要连接wifi后 高级选项ip设置为静态,输入自定义地址
        // 方便客户端 找 服务端,不需要用getHostAddress等，可能连接不上
        // 9090为端口
        val myHost = InetSocketAddress("127.0.0.1", 8091)
        val socketServer = MyWebSocketServer(myHost, this);
        myWebsocketServer = socketServer
    }

    override fun onDestroy() {
        super.onDestroy()
        myWebsocketServer?.stop()
        // 停止播放并释放资源
        if (audioTrack != null) {
            audioTrack!!.stop()
            audioTrack!!.release()
        }
        if (playbackThread != null) {
            playbackThread!!.interrupt()
        }
    }

    class MyWebSocketServer(host: InetSocketAddress, val activity: Activity) : WebSocketServer(host) {
        override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
            Log.d(tag, "server onOpen")
            activity.runOnUiThread {
                Toast.makeText(activity, "server onOpen", Toast.LENGTH_SHORT).show()
            }

            conn?.send("welcome to use websocket setvice")
            Log.d(tag, "server send audio")
            val filePath = activity.getExternalFilesDir(Environment.DIRECTORY_PODCASTS).toString() + "/wav_kent.wav"
            val file = File(filePath)
            if (!file.exists()) {
                activity.runOnUiThread {
                    Toast.makeText(activity, "file not found", Toast.LENGTH_SHORT).show()
                }
                return
            }
            val byteArray = convertFileToByteArray(file)
            conn?.send(byteArray)
        }


        override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
            Log.d(tag, "server onClose code=$code, reason=$reason")
            activity.runOnUiThread {
                Toast.makeText(activity, "server onClose", Toast.LENGTH_SHORT).show()
            }

        }

        override fun onMessage(conn: WebSocket?, message: String?) {
            Log.d(tag, "server onMessage $message")
//            conn?.send("hi I'm server")

        }

        override fun onMessage(conn: WebSocket?, message: ByteBuffer?) {
            super.onMessage(conn, message)
        }

        override fun onError(conn: WebSocket?, ex: Exception?) {
            Log.d(tag, "server onError ex=$ex")
            activity.runOnUiThread {
                Toast.makeText(activity, "erver onError ex=$ex", Toast.LENGTH_SHORT).show()
            }

        }

        override fun onStart() {
            Log.d(tag, "server onStart")
            activity.runOnUiThread {
                Toast.makeText(activity, "erver onStart", Toast.LENGTH_SHORT).show()
            }
        }

        fun convertFileToByteArray(file: File?): ByteArray? {
            return try {
                // 读取文件内容到 InputStream
                val inputStream: InputStream = FileInputStream(file)
                val outputStream = ByteArrayOutputStream()
                val buffer = ByteArray(4096)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    // 将读取的数据写入 ByteArrayOutputStream
                    outputStream.write(buffer, 0, bytesRead)
                }
                // 关闭输入流
                inputStream.close()
                // 将 ByteArrayOutputStream 转换为 byte 数组并返回
                outputStream.toByteArray()
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }


    //    private val webSocketUrl = "http://127.0.0.1:8091"
    private val webSocketUrl = "https://demo-ws-server-prod.17app.co/ws"
    private var mWebSocket: okhttp3.WebSocket? = null

    fun crateAiVLiverRWebSocket(activity: Activity) {
        val client = OkHttpClient.Builder()
            .pingInterval(3, TimeUnit.SECONDS)
            .addInterceptor(BaseInterceptor())
            .build()
        val request = Request.Builder()
            .url(webSocketUrl)
            .build()

        mWebSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: okhttp3.WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d(tag, "client onMessage text -> $text")
//                webSocket.send("hi, i'm client")

            }

            override fun onMessage(webSocket: okhttp3.WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                Log.d(tag, "client onMessage bytes -> $bytes")
                val decodeByteArray = decompressGzip(bytes.toByteArray())
                val text = String(decodeByteArray, StandardCharsets.UTF_8)
                Log.d(tag, "client onMessage decodeByteArray text -> $text")
                val model = Gson().fromJson(text, MessageModel::class.java)

//                val text = convertBinaryToText(bytes.toByteArray(), StandardCharsets.UTF_8)
//                Log.d(tag,"Converted text: $text")

//                val inputStream = ByteArrayInputStream(bytes.toByteArray())
                if(model?.aiVLiverAction?.wav !=null){
                    model?.aiVLiverAction?.wav.let {
                        if (it.isEmpty()) return
                        Log.d(tag, "playAudio")
                        val byteSource = Base64.decode(it, Base64.DEFAULT)
                        playAudio(byteSource)
                    }
                }


            }

            @Throws(IOException::class)
            fun decompressGzip(compressedData: ByteArray): ByteArray {
                val inputStream = ByteArrayInputStream(compressedData)
                val outputStream = ByteArrayOutputStream()
                GZIPInputStream(inputStream).use { gzipInputStream ->
                    val buffer = ByteArray(1024)
                    var bytesRead: Int
                    while (gzipInputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                    }
                }
                return outputStream.toByteArray()
            }

            override fun onOpen(webSocket: okhttp3.WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d(tag, "client onOpen  -response> $response")
                activity.runOnUiThread {
                    Toast.makeText(activity, "client onOpen  -response> $response", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onClosed(webSocket: okhttp3.WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d(tag, "client onClosed code=$code, reason=$reason)")
            }

            override fun onClosing(webSocket: okhttp3.WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                Log.d(tag, "client onClosing code=$code, reason=$reason)")
            }

            override fun onFailure(webSocket: okhttp3.WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d(tag, "client onFailure t=$t, response=$response)")
            }
        })
    }


    private var audioTrack: AudioTrack? = null
    private var playbackThread: Thread? = null

    fun initPlayer() {
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

    private fun playAudio(byteSource: ByteArray) {
        // 获取 WAV 数据的字节数组

        // 开启新线程播放音频
        playbackThread = Thread {
            try {
                // 开始播放
                audioTrack!!.play()

                // 写入数据到缓冲区
                audioTrack!!.write(byteSource, 0, byteSource.size)

                // 停止播放
//                audioTrack!!.stop()
//                audioTrack!!.release()
//                audioTrack = null
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        playbackThread!!.start()
    }

    private fun playAudio(byteArrayInputStream: BufferedInputStream) {
        // 获取 WAV 数据的字节数组
        val audioData = getAudioByteArray(byteArrayInputStream) // 从你的 ByteArrayInputStream 中获取数据

        // 开启新线程播放音频
        playbackThread = Thread {
            try {
                // 开始播放
                audioTrack!!.play()

                // 写入数据到缓冲区
                audioTrack!!.write(audioData, 0, audioData.size)

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

    private fun getAudioByteArray(byteArrayInputStream: BufferedInputStream): ByteArray {
//        val filePath = getExternalFilesDir(Environment.DIRECTORY_PODCASTS).toString() + "/wav_1707122027749.wav"
//        val file = File(filePath)
//        val inputStream = FileInputStream(file)
//        val byteArrayInputStream = BufferedInputStream(inputStream)
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


}