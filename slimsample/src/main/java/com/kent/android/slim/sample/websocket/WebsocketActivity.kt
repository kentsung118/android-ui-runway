package com.kent.android.slim.sample.websocket

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
import java.io.ByteArrayInputStream
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit

/**
 * Created by Kent Sung on 2024/1/30.
 */
class WebsocketActivity : AppCompatActivity() {
    
    companion object{
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
        btn_server_start.setOnClickListener{
            myWebsocketServer?.start()
        }

        btn_server_stop.setOnClickListener {
            myWebsocketServer?.stop()
        }

        btn_client_start.setOnClickListener {
            crateAiVLiverRWebSocket()
        }
    }




    private var myWebsocketServer: WebSocketServer? = null
    private fun startSocketServer() {
        // 192.168.1.101为安卓服务端，需要连接wifi后 高级选项ip设置为静态,输入自定义地址
        // 方便客户端 找 服务端,不需要用getHostAddress等，可能连接不上
        // 9090为端口
        val myHost = InetSocketAddress("127.0.0.1", 8090)
        val socketServer = MyWebSocketServer(myHost);
        myWebsocketServer = socketServer


    }

    override fun onDestroy() {
        super.onDestroy()
        myWebsocketServer?.stop()
    }

    class MyWebSocketServer(host: InetSocketAddress) : WebSocketServer(host) {
        override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
            Log.d(tag, "server onOpen")
            conn?.send("welcome to use websocket setvice")
        }


        override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
            Log.d(tag, "server onClose code=$code, reason=$reason")

        }

        override fun onMessage(conn: WebSocket?, message: String?) {
            Log.d(tag, "server onMessage $message")
            conn?.send("hi I'm server")

        }

        override fun onMessage(conn: WebSocket?, message: ByteBuffer?) {
            super.onMessage(conn, message)
        }

        override fun onError(conn: WebSocket?, ex: Exception?) {
            Log.d(tag, "server onError ex=$ex")
        }

        override fun onStart() {
            Log.d(tag, "server onStart")
        }
    }


    private val webSocketUrl = "http://127.0.0.1:8090"
    private var mWebSocket: okhttp3.WebSocket? = null

    fun crateAiVLiverRWebSocket() {
//        viewModelScope.launch(Dispatchers.IO) {
            val client = OkHttpClient.Builder()
                .pingInterval(3, TimeUnit.SECONDS)
                .build()
            val request = Request.Builder()
                .url(webSocketUrl)
                .build()

            mWebSocket = client.newWebSocket(request, object : WebSocketListener() {
                override fun onMessage(webSocket: okhttp3.WebSocket, text: String) {
                    super.onMessage(webSocket, text)
                    Log.d(tag, "client onMessage text -> $text")
                    webSocket.send("hi, i'm client")

                }

                override fun onMessage(webSocket: okhttp3.WebSocket, bytes: ByteString) {
                    super.onMessage(webSocket, bytes)
                    Log.d(tag, "client onMessage bytes -> $bytes")
                    val inputStream = ByteArrayInputStream(bytes.toByteArray())
//                    bytes.size
                }

                override fun onOpen(webSocket: okhttp3.WebSocket, response: Response) {
                    super.onOpen(webSocket, response)
                    Log.d(tag, "client onOpen  -response> $response")
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
//        }
    }

}