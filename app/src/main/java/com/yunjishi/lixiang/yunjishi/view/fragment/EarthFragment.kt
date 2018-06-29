package com.yunjishi.lixiang.yunjishi.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.widget.Toast
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.github.lzyzsd.jsbridge.DefaultHandler

import com.yunjishi.lixiang.yunjishi.R
import com.yunjishi.lixiang.yunjishi.view.activity.OrderDetailActivity
import com.yunjishi.lixiang.yunjishi.view.activity.VideoPlayerActivity
import kotlinx.android.synthetic.main.fragment_earth.*
import org.jetbrains.anko.support.v4.startActivity
import com.yunjishi.lixiang.yunjishi.view.activity.MainActivity
import android.app.Activity
import android.content.Context
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.yunjishi.lixiang.yunjishi.view.activity.PdfViewerActivity
import kotlinx.android.synthetic.main.activity_main.*


class EarthFragment : Fragment() {

    var mUploadMessage: ValueCallback<Uri>? = null
    var userId: String?= ""
    var loginStatus: String?= ""
    var productId: String? = "-1"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_earth, container, false)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        userId = (activity as MainActivity).getUserID()
        if (userId == "-1")
            loginStatus = "no"
        else
            loginStatus = "yes"
        println("userId$userId")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mWebView.setDefaultHandler(DefaultHandler())
        val sp = activity!!.getSharedPreferences("XXX", Context.MODE_PRIVATE)
        println("sp.getString" + sp.getString("PRODUCT_ID", ""))
        productId = sp.getString("PRODUCT_ID", "")
        mWebView.webChromeClient = object : WebChromeClient() {

            fun openFileChooser(uploadMsg: ValueCallback<Uri>, AcceptType: String, capture: String) {
                this.openFileChooser(uploadMsg)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>, AcceptType: String) {
                this.openFileChooser(uploadMsg)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
                mUploadMessage = uploadMsg
            }
        }

        if(userId == "-1"){
            mWebView.loadUrl("http://202.111.178.10:12380/globe.html?loginStatus=$loginStatus")
        }else{
            mWebView.loadUrl("http://202.111.178.10:12380/globe.html?userId=$userId&loginStatus=$loginStatus&productId=$productId")
        }
        mWebView.registerHandler("videoPlay", BridgeHandler { data, function ->

            println("data$data")
            val intent = Intent(activity, VideoPlayerActivity::class.java)
            val bundle = Bundle()
            bundle.putString("URL", data)
            intent.putExtras(bundle)
            startActivity(intent)
        })
        mWebView.registerHandler("showLoginPage", BridgeHandler { data, function ->

            println("data$data")
            if (data == "showLoginPage"){
                var mLoginLayout = activity!!.findViewById<LinearLayout>(R.id.mLoginLayout)
                mLoginLayout.visibility = View.VISIBLE
            }
        })
        mWebView.registerHandler("showThematicReport", BridgeHandler { data, function ->

            println("data$data")
            if (data != ""){
                val intent = Intent(activity, PdfViewerActivity::class.java)
                val bundle = Bundle()
                bundle.putString("URL", data)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })
        sp.edit().clear().commit()
    }
}
