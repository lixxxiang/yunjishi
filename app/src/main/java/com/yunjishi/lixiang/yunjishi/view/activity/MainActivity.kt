package com.yunjishi.lixiang.yunjishi.view.activity

import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.yunjishi.lixiang.yunjishi.R
import com.yunjishi.lixiang.yunjishi.view.fragment.EarthFragment
import com.yunjishi.lixiang.yunjishi.view.fragment.MissionFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import com.github.ikidou.fragmentBackHandler.BackHandlerHelper
import com.github.lzyzsd.jsbridge.DefaultHandler
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.UserBean
import com.yunjishi.lixiang.yunjishi.presenter.database.DaoMaster
import com.yunjishi.lixiang.yunjishi.presenter.database.DaoSession
import com.yunjishi.lixiang.yunjishi.presenter.database.UserBeanDao
import com.yunjishi.lixiang.yunjishi.view.fragment.LoginFragment
import org.greenrobot.greendao.database.Database


class MainActivity : AppCompatActivity(){
    var mUploadMessage: ValueCallback<Uri>? = null
    var mDaoSession: DaoSession? = null


    fun getDaoSession():DaoSession{
        return mDaoSession!!
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        StatusBarUtil.setColor(this, Color.parseColor("#262626"), 0)

        initDao()
        initFragment()
        initLogin()
        initNavigationView()
        changeFragment(0)

        mAvatarImageView.setOnClickListener{
            mLoginLayout.visibility = View.VISIBLE
//            var userBean: UserBean = UserBean("1","lx","133","123")
//            mDaoSession!!.userBeanDao.insert(userBean)
        }
    }

    private fun initDao() {
        var openHelper: DaoMaster.DevOpenHelper = DaoMaster.DevOpenHelper(this,"USER")
        var db:Database = openHelper.writableDb
        var daoMaster: DaoMaster= DaoMaster(db)
        mDaoSession = daoMaster.newSession()
    }

    private fun initLogin() {
        mLoginWebView.setBackgroundColor(0);
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
        mLoginWebView.loadUrl("http://10.10.90.14:8088/login.html")
//        mWebView.registerHandler("submitFromWeb", BridgeHandler { data, function ->
//            Toast.makeText(activity, data, Toast.LENGTH_SHORT).show()
//        })

    }

    private val mStack = Stack<Fragment>()
    private val mLoginFragment by lazy { LoginFragment() }
    private val mEarthFragment by lazy { EarthFragment() }
    private val mMissionFragment by lazy { MissionFragment() }

    private fun initFragment(){
        val manager = supportFragmentManager.beginTransaction()
//        manager.add(R.id.mFrameLayout,mLoginFragment)
        manager.add(R.id.mFrameLayout,mEarthFragment)
        manager.add(R.id.mFrameLayout,mMissionFragment)
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

    private fun changeFragment(position: Int) {
        val manager = supportFragmentManager.beginTransaction()
        for (fragment in mStack){
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


