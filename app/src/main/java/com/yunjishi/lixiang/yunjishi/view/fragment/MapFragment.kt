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
import com.yunjishi.lixiang.yunjishi.R
import kotlinx.android.synthetic.main.fragment_login.*


class MapFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var screenHeight = activity!!.application.resources.displayMetrics.heightPixels.toFloat()
        val imageWidth = (DimenUtil().px2dip(context!!, screenHeight.toFloat()) - 60 - 60 - 35) * 0.5.toFloat()
        val imageHeight = imageWidth * 0.5
        val mImageView1 = AppCompatImageView(activity)
        mImageView1.id = 1
        val mImageView1LayoutParams = RelativeLayout.LayoutParams(
                imageWidth.toInt(),imageHeight.toInt()
//                DimenUtil().dip2px(activity!!, imageWidth),
//                DimenUtil().dip2px(activity!!, imageWidth * 0.5.toFloat())
        )
        mImageView1LayoutParams.setMargins(30, 34, 0, 0)
        mImageView1.setBackgroundResource(R.drawable.img_submit)
        mImageView1.layoutParams = mImageView1LayoutParams
        mMapFragmentRelativeLayout.addView(mImageView1)
    }

}
