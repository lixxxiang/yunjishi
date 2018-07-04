package com.yunjishi.lixiang.yunjishi.view.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.android.lixiang.base.utils.view.DimenUtil
import com.baidu.location.g.j.B
import com.orhanobut.logger.Logger
import com.yunjishi.lixiang.yunjishi.R
import com.yunjishi.lixiang.yunjishi.view.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.support.v4.act
import java.util.*


class DefaultFragment : Fragment(), View.OnClickListener {

    private var mImageView1: AppCompatImageView? = null
    private var mImageView2: AppCompatImageView? = null
    private var userId: String? = null


    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        userId = (activity as MainActivity).getUserID()
        Logger.d(userId)
    }


    override fun onClick(v: View?) {
        when (v) {
            mImageView1 -> {
                if (userId != "-1")
                    (activity as MainActivity).changeFragment(3)
                else {
                    var mLoginLayout = activity!!.findViewById<LinearLayout>(R.id.mLoginLayout)
                    mLoginLayout.visibility = View.VISIBLE
                }
            }
            mImageView2 -> {
                if (userId != "-1")

                    (activity as MainActivity).changeFragment(5)
                else {
                    var mLoginLayout = activity!!.findViewById<LinearLayout>(R.id.mLoginLayout)
                    mLoginLayout.visibility = View.VISIBLE
                }
            }
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
        val textMargin = imageWidth + 82
        val imageHeight = imageWidth * 0.5.toFloat()
        val textMarginHeight = (imageHeight * 149) / 216 + 34

        mImageView1 = AppCompatImageView(activity)
        mImageView1!!.id = 1
        val mImageView1LayoutParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context!!, imageWidth), DimenUtil().dip2px(context!!, imageHeight))
        mImageView1LayoutParams.setMargins(DimenUtil().dip2px(context!!, 30.toFloat()), DimenUtil().dip2px(context!!, 34.toFloat()), 0, 0)
        mImageView1!!.setBackgroundResource(R.drawable.img_submit)
        mImageView1!!.layoutParams = mImageView1LayoutParams
        mMapFragmentRelativeLayout.addView(mImageView1)

        mImageView2 = AppCompatImageView(activity)
        mImageView2!!.id = 2
        val mImageView2LayoutParams = RelativeLayout.LayoutParams(DimenUtil().dip2px(context!!, imageWidth), DimenUtil().dip2px(context!!, imageHeight))
        mImageView2LayoutParams.setMargins(0, DimenUtil().dip2px(context!!, 34.toFloat()), DimenUtil().dip2px(context!!, 30.toFloat()), 0)
        mImageView2LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        mImageView2!!.setBackgroundResource(R.drawable.img_order)
        mImageView2!!.layoutParams = mImageView2LayoutParams
        mMapFragmentRelativeLayout.addView(mImageView2)

        mImageView1!!.setOnClickListener(this)
        mImageView2!!.setOnClickListener(this)

        val T1 = AppCompatTextView(activity)
        val T1L = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        T1L.setMargins(DimenUtil().dip2px(context!!, 48.toFloat()), DimenUtil().dip2px(context!!, textMarginHeight), 0, 0)
        T1.setTextColor(Color.parseColor("#FFFFFF"))
        T1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14F)
        T1.layoutParams = T1L
        T1.text = "在线提交"
        mMapFragmentRelativeLayout.addView(T1)

        val T2 = AppCompatTextView(activity)
        val T2L = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        T2L.setMargins(DimenUtil().dip2px(context!!, 48.toFloat()), DimenUtil().dip2px(context!!, textMarginHeight + 30), 0, 0)
        T2.setTextColor(Color.parseColor("#F5A623"))
        T2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14F)
        T2.layoutParams = T2L
        T2.text = "在线提交拍摄需求，调动卫星为您拍摄"

        mMapFragmentRelativeLayout.addView(T2)

        val T3 = AppCompatTextView(activity)
        val T3L = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        T3L.setMargins(DimenUtil().dip2px(context!!, textMargin), DimenUtil().dip2px(context!!, textMarginHeight), 0, 0)
        T3.setTextColor(Color.parseColor("#FFFFFF"))
        T3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14F)
        T3.text = "我的订单"

        T3.layoutParams = T3L
        mMapFragmentRelativeLayout.addView(T3)

        val T4 = AppCompatTextView(activity)
        val T4L = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        T4L.setMargins(DimenUtil().dip2px(context!!, textMargin), DimenUtil().dip2px(context!!, textMarginHeight + 30), 0, 0)
        T4.setTextColor(Color.parseColor("#F5A623"))
        T4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14F)
        T4.text = "实时查看我的订单"

        T4.layoutParams = T4L
        mMapFragmentRelativeLayout.addView(T4)

    }
}
