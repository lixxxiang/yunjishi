package com.yunjishi.lixiang.yunjishi.view.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.github.lzyzsd.jsbridge.DefaultHandler

import com.yunjishi.lixiang.yunjishi.R
import com.yunjishi.lixiang.yunjishi.view.activity.MainActivity
import com.yunjishi.lixiang.yunjishi.view.activity.OrderDetailActivity
import kotlinx.android.synthetic.main.fragment_earth.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_mission.*
import kotlinx.android.synthetic.main.fragment_order.*


class OrderFragment : Fragment() {
    var mUploadMessage: ValueCallback<Uri>? = null
    private var userId: String? = null
    var mSharedPreferences: SharedPreferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        userId = (activity as MainActivity).getUserID()
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        mOrderFragmentWebView.setBackgroundColor(0);
        mOrderFragmentWebView.setDefaultHandler(DefaultHandler())
        mOrderFragmentWebView.webChromeClient = object : WebChromeClient() {

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
        com.orhanobut.logger.Logger.d("http://202.111.178.10:12380/demand.html?userId=$userId")
        mOrderFragmentWebView.loadUrl("http://202.111.178.10:12380/demand.html?userId=$userId")
        mOrderFragmentWebView.registerHandler("demandShow", BridgeHandler { data, function ->
            println("demandId$data")
            if (data != null) {
                val intent = Intent(activity, OrderDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putString("DEMAND_ID", data)
                bundle.putString("USER_ID", userId)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })
        mOrderFragmentWebView.registerHandler("productShow", BridgeHandler { data, function ->
            println("productId$data")
            if (data != null) {
                mSharedPreferences = activity!!.getSharedPreferences("XXX", Context.MODE_PRIVATE)
                val editor = mSharedPreferences!!.edit()
                editor.putString("PRODUCT_ID", data)
                editor.commit()
                val intent = activity!!.intent
                activity!!.overridePendingTransition(0, 0)
                activity!!.finish()
                activity!!.overridePendingTransition(0, 0)
                startActivity(intent)
            }
        })
    }
}