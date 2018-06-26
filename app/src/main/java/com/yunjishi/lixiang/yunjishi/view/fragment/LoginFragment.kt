package com.yunjishi.lixiang.yunjishi.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import com.github.lzyzsd.jsbridge.DefaultHandler

import com.yunjishi.lixiang.yunjishi.R
import kotlinx.android.synthetic.main.fragment_earth.*
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {
    var mUploadMessage: ValueCallback<Uri>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        mLoginWebView.setDefaultHandler(DefaultHandler())
//        mLoginWebView.webChromeClient = object : WebChromeClient() {
//
//            fun openFileChooser(uploadMsg: ValueCallback<Uri>, AcceptType: String, capture: String) {
//                this.openFileChooser(uploadMsg)
//            }
//
//            fun openFileChooser(uploadMsg: ValueCallback<Uri>, AcceptType: String) {
//                this.openFileChooser(uploadMsg)
//            }
//
//            fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
//                mUploadMessage = uploadMsg
//            }
//        }
//        mLoginWebView.loadUrl("http://10.10.90.14:8088/login.html")
////        mWebView.registerHandler("submitFromWeb", BridgeHandler { data, function ->
////            Toast.makeText(activity, data, Toast.LENGTH_SHORT).show()
////        })
//    }
}
