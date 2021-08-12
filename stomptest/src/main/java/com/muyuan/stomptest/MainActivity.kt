package com.muyuan.stomptest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import java.util.*
import com.muyuan.stomptest.R

class MainActivity : AppCompatActivity() {
    private var mStompClient: StompClient? = null
    private val compositeDisposable = CompositeDisposable()
    private val TAG = "MainActivity"
    private lateinit var msgTv: TextView
    private lateinit var connectionBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        msgTv = findViewById<TextView>(R.id.msgTv)
        connectionBtn = findViewById(R.id.connectionBtn)
    }

    override fun onDestroy() {
        super.onDestroy()
        mStompClient?.disconnect()
        compositeDisposable.clear()
    }

    public fun connectClick(v: View) {
        if (mStompClient != null && mStompClient!!.isConnected) {
            disConnect()
        } else {
            connect()
        }
    }

    private fun connect() {
        //        var ip = "121.89.207.106:8183"
//        var ip = "192.168.20.37:7878"
        var ip = "120.79.207.223:8080"
        val url = "ws://${ip}/myUrl/websocket?token=${UUID.randomUUID()}"
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
        liftCycle(null)
        mStompClient?.connect()
    }

    private fun disConnect() {
        mStompClient?.disconnect()
    }

    public fun liftCycle(v: View?) {
        //监听lifecycleEvent的回调状态
        val dispLifecycle = mStompClient!!.lifecycle()
                .doOnError { throwable: Throwable -> Log.e(TAG, "连接异常：${throwable.message}") }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ lifecycleEvent: LifecycleEvent ->
                    lifecycleEvent.type?.let { type ->
                        when (type) {
                            LifecycleEvent.Type.OPENED -> Log.d(TAG, "Stomp connection opened")
                            LifecycleEvent.Type.ERROR -> Log.e(TAG, "Stomp connection error", lifecycleEvent.exception)
                            LifecycleEvent.Type.CLOSED -> Log.d(TAG, "Stomp connection closed")
                            LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> Log.d(TAG, "Stomp failed server heartbeat")
                        }
                        if (type == LifecycleEvent.Type.OPENED) {
                            connectionBtn.text = "断开连接"
                        } else {
                            connectionBtn.text = "连接"
                        }
                    }
                }, { throwable: Throwable? -> Log.w(TAG, "stomp lifecycle 发生异常：${throwable?.message}") })
        compositeDisposable.add(dispLifecycle)
    }

    public fun topic(v: View?) {
        val dispLifecycle = mStompClient!!.topic("/topic/greetings")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ topicMessage ->
                    Log.d(TAG, "Received " + topicMessage.getPayload())
                    msgTv.text = topicMessage.getPayload()
                }) { throwable -> Log.e(TAG, "Error on subscribe topic", throwable) }
        compositeDisposable.add(dispLifecycle)
    }


}