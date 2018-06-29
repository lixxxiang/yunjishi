package com.yunjishi.lixiang.yunjishi.view.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.widget.Toast
import com.android.lixiang.base.database.DatabaseManager
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.yunjishi.lixiang.yunjishi.R
import com.yunjishi.lixiang.yunjishi.view.fragment.EarthFragment
import com.yunjishi.lixiang.yunjishi.view.fragment.MissionFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import com.github.ikidou.fragmentBackHandler.BackHandlerHelper
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.github.lzyzsd.jsbridge.DefaultHandler
import com.google.gson.Gson
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.UserBean
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.UserBean2
import com.yunjishi.lixiang.yunjishi.presenter.database.DaoMaster
import com.yunjishi.lixiang.yunjishi.presenter.database.DaoSession
import com.yunjishi.lixiang.yunjishi.presenter.database.UserBeanDao
import com.yunjishi.lixiang.yunjishi.view.fragment.LoginFragment
import kotlinx.android.synthetic.main.fragment_earth.*
import org.greenrobot.greendao.database.Database
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {
    var mUploadMessage: ValueCallback<Uri>? = null
    var mDaoSession: DaoSession? = null
    var userBean = UserBean2()
    fun getDaoSession(): DaoSession {
        return mDaoSession!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        StatusBarUtil.setColor(this, Color.parseColor("#262626"), 0)


        initDao()
        initFragment()
        initLogin()
        initLogout()
        checkLogin()
        initNavigationView()
        changeFragment(0)

        mAvatarImageView.setOnClickListener {
            println(checkLogin())
            if (checkLogin()) {
                mLogoutLayout.visibility = View.VISIBLE
            } else
                mLoginLayout.visibility = View.VISIBLE
//            var userBean: UserBean = UserBean("1","lx","133","123")
//            mDaoSession!!.userBeanDao.insert(userBean)
        }

//        startActivity<VideoPlayerActivity>()
//        startActivity<PdfViewerActivity>()
    }

    private fun initLogout() {
        mLogoutWebView.setBackgroundColor(0)
        mLogoutWebView.setDefaultHandler(DefaultHandler())
        mLogoutWebView.webChromeClient = object : WebChromeClient() {

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
        mLogoutWebView.loadUrl("http://202.111.178.10:12380/logout.html")
        mLogoutWebView.registerHandler("closeLoginPage", { data, function ->
            println("data$data")
            if (data == "closeLoginPage") {
                mLogoutLayout.visibility = View.INVISIBLE
            }
        })

        mLogoutWebView.registerHandler("logoutCancel", { data, function ->
            println("data$data")
            if (data == "logoutCancel") {
                mLogoutLayout.visibility = View.INVISIBLE
            }
        })

        mLogoutWebView.registerHandler("logoutSuccess", { data, function ->
            println("data$data")
            if (data == "logoutSuccess") {
                mLogoutLayout.visibility = View.INVISIBLE
                mDaoSession!!.userBean2Dao.deleteAll()
                mAvatarImageView.setImageResource(R.drawable.ic_avatar)
                val intent = intent
                overridePendingTransition(0, 0)
                finish()
                overridePendingTransition(0, 0)
                startActivity(intent)
            }
        })
    }

    private fun checkLogin(): Boolean {
        if (mDaoSession!!.userBean2Dao!!.loadAll().isEmpty()) {
            println("---->>>>")
            println("未登录")
            return false
        } else {
            println("---->>>>")
            println("已登陆")
            mAvatarImageView.setImageResource(R.drawable.ic_avatar_login)
            return true
        }
    }

    private fun initDao() {
        var openHelper: DaoMaster.DevOpenHelper = DaoMaster.DevOpenHelper(this, "USER")
        var db: Database = openHelper.writableDb
        var daoMaster: DaoMaster = DaoMaster(db)
        mDaoSession = daoMaster.newSession()
    }

    fun getUserID(): String? {
        if (mDaoSession!!.userBean2Dao.loadAll().isEmpty()) {
            return "-1"
        } else
            return mDaoSession!!.userBean2Dao.loadAll()[0].userId
    }

    private fun initLogin() {
        mLoginWebView.setBackgroundColor(0)
        mLoginWebView.setDefaultHandler(DefaultHandler())
        mLoginWebView.webChromeClient = object : WebChromeClient() {

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
        mLoginWebView.loadUrl("http://202.111.178.10:12380/login.html")
        mLoginWebView.registerHandler("closeLoginPage", { data, function ->
            println("data$data")
            if (data == "closeLoginPage") {
                mLoginLayout.visibility = View.GONE



            }
        })
        mLoginWebView.registerHandler("loginSuccess", { data, function ->
            println("data$data")
            userBean = Gson().fromJson(data, UserBean2::class.java)
            println("userBean.userId" + userBean.userId)
            mDaoSession!!.userBean2Dao.insert(userBean)
            mLoginLayout.visibility = View.GONE
            mAvatarImageView.setImageResource(R.drawable.ic_avatar_login)
//            mLoginWebView.reload()
            val intent = intent
            overridePendingTransition(0, 0)
            finish()
            overridePendingTransition(0, 0)
            startActivity(intent)
        })
        mLoginWebView.registerHandler("resetPwd", { data, function ->
            println("data$data")
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            intent.data = Uri.parse(data)
            startActivity(intent)
        })
        mLoginWebView.registerHandler("register", { data, function ->
            println("data$data")
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            intent.data = Uri.parse(data)
            startActivity(intent)
        })
    }

    private val mStack = Stack<Fragment>()
    private val mLoginFragment by lazy { LoginFragment() }
    private val mEarthFragment by lazy { EarthFragment() }
    private val mMissionFragment by lazy { MissionFragment() }

    private fun initFragment() {
        val manager = supportFragmentManager.beginTransaction()
//        manager.add(R.id.mFrameLayout,mLoginFragment)
        manager.add(R.id.mFrameLayout, mEarthFragment)
        manager.add(R.id.mFrameLayout, mMissionFragment)
        manager.commit()
//        mStack.add(mLoginFragment)
        mStack.add(mEarthFragment)
        mStack.add(mMissionFragment)
    }

    private fun initNavigationView() {
        mEarthImageView.setBackgroundResource(R.drawable.ic_earth)
        mEarthTextView.setTextColor(Color.parseColor("#738FFE"))

        mFeild1.setOnClickListener {
            mEarthImageView.setBackgroundResource(R.drawable.ic_earth)
            mMissionImageView.setBackgroundResource(R.drawable.ic_mission_white)
            mSearchImageView.setBackgroundResource(R.drawable.ic_search_white)

            mEarthTextView.setTextColor(Color.parseColor("#738FFE"))
            mMissionTextView.setTextColor(Color.parseColor("#FFFFFF"))
            mSearchTextView.setTextColor(Color.parseColor("#FFFFFF"))
            mLoginLayout.visibility = View.GONE

            changeFragment(0)
        }

        mFeild2.setOnClickListener {
            mEarthImageView.setBackgroundResource(R.drawable.ic_earth_white)
            mMissionImageView.setBackgroundResource(R.drawable.ic_mission)
            mSearchImageView.setBackgroundResource(R.drawable.ic_search_white)

            mEarthTextView.setTextColor(Color.parseColor("#FFFFFF"))
            mMissionTextView.setTextColor(Color.parseColor("#738FFE"))
            mSearchTextView.setTextColor(Color.parseColor("#FFFFFF"))
            mLoginLayout.visibility = View.GONE

            changeFragment(1)

        }

        mFeild3.setOnClickListener {
            mEarthImageView.setBackgroundResource(R.drawable.ic_earth_white)
            mMissionImageView.setBackgroundResource(R.drawable.ic_mission_white)
            mSearchImageView.setBackgroundResource(R.drawable.ic_search)

            mEarthTextView.setTextColor(Color.parseColor("#FFFFFF"))
            mMissionTextView.setTextColor(Color.parseColor("#FFFFFF"))
            mSearchTextView.setTextColor(Color.parseColor("#738FFE"))

        }

    }

    fun changeFragment(position: Int) {
        val manager = supportFragmentManager.beginTransaction()
        for (fragment in mStack) {
            manager.hide(fragment)
        }

        manager.show(mStack[position])
        manager.commit()
    }

    override fun onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed()
        }
    }
}


