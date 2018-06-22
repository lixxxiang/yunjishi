package com.yunjishi.lixiang.yunjishi

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.apache.cordova.*
import org.apache.cordova.engine.SystemWebViewEngine
import org.jetbrains.anko.startActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity(), CordovaInterface {

    var cordovaWebView: CordovaWebView? = null
    private val threadPool = Executors.newCachedThreadPool()
    protected var activityResultRequestCode: Int = 0
    protected var prefs = CordovaPreferences()
    protected var pluginEntries: ArrayList<PluginEntry>? = null
    protected var activityResultCallback1: CordovaPlugin? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val parser = ConfigXmlParser()
        parser.parse(activity)
        cordovaWebView = CordovaWebViewImpl(SystemWebViewEngine(webview1))
        cordovaWebView!!.init(this, parser.pluginEntries, parser.preferences)
        webview1.loadUrl("file:///android_asset/www/index.html")
        btn.setOnClickListener {
            webview1.loadUrl("javascript:fly(\"" + 43.9706700000 + "\",\"" + 125.3831700000 + "\")")
        }

        btn2.setOnClickListener {
//            webview1.loadUrl("javascript:flyBack(\"" + 39.9046900000 + "\",\"" + 116.4071700000 + "\")")
            startActivity<testMapActivity>()
        }
    }
    //43.9706700000,125.3831700000  116.4071700000, 39.9046900000, 20000000


    override fun requestPermissions(p0: CordovaPlugin?, p1: Int, p2: Array<out String>?) {
    }

    override fun startActivityForResult(p0: CordovaPlugin?, p1: Intent?, p2: Int) {
        setActivityResultCallback(p0)
        try {
            startActivityForResult(p1, p2)
        } catch (e: RuntimeException) {
            activityResultCallback1 = null
            throw e
        }
    }

    override fun setActivityResultCallback(p0: CordovaPlugin?) {
        if (activityResultCallback1 != null) {
            activityResultCallback1!!.onActivityResult(activityResultRequestCode, Activity.RESULT_CANCELED, null)
        }
        activityResultCallback1 = p0
    }

    override fun getActivity(): Activity {
        return this
    }

    override fun onMessage(p0: String?, p1: Any?): Any? {
        if ("exit" == p0) {
            activity.finish()
        }
        return null
    }

    override fun getThreadPool(): ExecutorService {
        return threadPool

    }

    override fun hasPermission(p0: String?): Boolean {
        return false

    }

    override fun requestPermission(p0: CordovaPlugin?, p1: Int, p2: String?) {
    }


}
