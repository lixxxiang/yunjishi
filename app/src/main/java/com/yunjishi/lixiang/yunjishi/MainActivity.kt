package com.yunjishi.lixiang.yunjishi

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.yunjishi.lixiang.yunjishi.fragment.EarthFragment
import com.yunjishi.lixiang.yunjishi.fragment.MissionFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        StatusBarUtil.setColor(this, Color.parseColor("#262626"), 0)

        initFragment()
        initNavigationView()
        changeFragment(0)

    }

    private val mStack = Stack<Fragment>()
    private val mEarthFragment by lazy { EarthFragment() }
    private val mMissionFragment by lazy { MissionFragment() }

    private fun initFragment(){
        val manager = supportFragmentManager.beginTransaction()
        manager.add(R.id.mFrameLayout,mEarthFragment)
        manager.add(R.id.mFrameLayout,mMissionFragment)
        manager.commit()
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

            changeFragment(0)
        }

        mFeild2.setOnClickListener {
            mEarthImageView.setBackgroundResource(R.drawable.ic_earth_white)
            mMissionImageView.setBackgroundResource(R.drawable.ic_mission)
            mSearchImageView.setBackgroundResource(R.drawable.ic_search_white)

            mEarthTextView.setTextColor(Color.parseColor("#FFFFFF"))
            mMissionTextView.setTextColor(Color.parseColor("#738FFE"))
            mSearchTextView.setTextColor(Color.parseColor("#FFFFFF"))

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
}
