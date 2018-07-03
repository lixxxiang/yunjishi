package com.yunjishi.lixiang.yunjishi.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatImageView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.android.lixiang.base.utils.view.DimenUtil
import com.orhanobut.logger.Logger
import com.yunjishi.lixiang.yunjishi.R
import com.yunjishi.lixiang.yunjishi.view.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.support.v4.act
import java.util.*


class DefaultFragment : Fragment(), View.OnClickListener {

    private var mImageView1: AppCompatImageView? = null

    override fun onClick(v: View?) {
        when (v) {
            mImageView1 -> (activity as MainActivity).changeFragment(3)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var screenWidth = activity!!.application.resources.displayMetrics.heightPixels.toFloat()
        var screenHeight = activity!!.application.resources.displayMetrics.widthPixels.toFloat()

        val imageWidth = (DimenUtil().px2dip(context!!, screenHeight) - 60 - 60 - 35) * 0.5.toFloat()
        val imageHeight = imageWidth * 0.5.toFloat()

        mImageView1 = AppCompatImageView(activity)
        mImageView1!!.id = 1
        val mImageView1LayoutParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context!!, imageWidth), DimenUtil().dip2px(context!!, imageHeight))
        mImageView1LayoutParams.setMargins(DimenUtil().dip2px(context!!, 30.toFloat()), DimenUtil().dip2px(context!!, 34.toFloat()), 0, 0)
        mImageView1!!.setBackgroundResource(R.drawable.img_submit)
        mImageView1!!.layoutParams = mImageView1LayoutParams
        mMapFragmentRelativeLayout.addView(mImageView1)

        val mImageView2 = AppCompatImageView(activity)
        mImageView2.id = 2
        val mImageView2LayoutParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context!!, imageWidth), DimenUtil().dip2px(context!!, imageHeight))
        mImageView2LayoutParams.setMargins(0, DimenUtil().dip2px(context!!, 34.toFloat()), DimenUtil().dip2px(context!!, 30.toFloat()), 0)
        mImageView2LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        mImageView2.setBackgroundResource(R.drawable.img_order)
        mImageView2.layoutParams = mImageView2LayoutParams
        mMapFragmentRelativeLayout.addView(mImageView2)

        mImageView1!!.setOnClickListener(this)
    }
}
