package com.yunjishi.lixiang.yunjishi.view.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.orhanobut.logger.Logger
import com.yunjishi.lixiang.yunjishi.R
import com.yunjishi.lixiang.yunjishi.presenter.MissionPresenter
import com.yunjishi.lixiang.yunjishi.presenter.ParamsPresenter
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.SubmitOrderBean
import com.yunjishi.lixiang.yunjishi.presenter.injection.component.DaggerParamsComponent
import com.yunjishi.lixiang.yunjishi.presenter.injection.module.MissionModule
import com.yunjishi.lixiang.yunjishi.presenter.injection.module.ParamsModule
import com.yunjishi.lixiang.yunjishi.presenter.view.MissionView
import com.yunjishi.lixiang.yunjishi.presenter.view.ParamsView
import com.yunjishi.lixiang.yunjishi.view.activity.MainActivity
import com.yunjishi.lixiang.yunjishi.view.activity.SelectRatioActivity
import com.yunjishi.lixiang.yunjishi.view.activity.SelectTimeActivity
import com.yunjishi.lixiang.yunjishi.view.activity.SelectTypeParamsActivity
import com.yunjishi.lixiang.yunjishi.view.adapter.SelectParamsAdapter
import kotlinx.android.synthetic.main.fragment_mission.*
import kotlinx.android.synthetic.main.fragment_params.*

class ParamsFragment: BaseMvpFragment<ParamsPresenter>(), ParamsView {
    override fun injectComponent() {
        DaggerParamsComponent.builder().fragmentComponent(fragmentComponent)
                .paramsModule(ParamsModule())
                .build().inject(this)
    }

    override fun onSubmitOrderResult(res: SubmitOrderBean) {
        println("onSubmitOrderResult$res")
    }

    private var titleList: MutableList<String>? = mutableListOf()
    private var subTitleList: MutableList<String>? = mutableListOf()
    private var detailList: MutableList<String>? = mutableListOf()
    private var typeList: MutableList<String>? = mutableListOf()
    private var ratioList: MutableList<String>? = mutableListOf()
    private var adapter: SelectParamsAdapter? = null
    private var typeIndex = -1
    private var ratioIndex = -1
    private var userId: String? = null
    private var demandType: String? = null
    private var demandGeo: String? = null
    private var resolution: String? = null
    private var startTime: String? = null
    private var endTime: String? = null
    private var timesParam: String? = null
    private var time: String? = ""
    private var times: String? = ""
    private var TIMES: String? = ""
    private var flag1 = false
    private var flag2 = false
    private var flag3 = false
    private var flag4 = false
    private var pageIndex = 0

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        userId = (activity as MainActivity).getUserID()
        Logger.d(userId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_params, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        titleList!!.add("类型")
        titleList!!.add("分辨率")
        titleList!!.add("拍摄时间")
        titleList!!.add("拍摄频次")

        typeList!!.add("标准卫星图")
        typeList!!.add("夜光卫星图")
        typeList!!.add("卫星视频")

        ratioList!!.add("小于 1m")
        ratioList!!.add("1m - 3m")
        ratioList!!.add("3m - 8m")
        ratioList!!.add("8m - 16m")

        detailList!!.add("请选择")
        detailList!!.add("请选择")
        detailList!!.add("请选择")
        detailList!!.add("请选择")

        subTitleList!!.add("选择您想拍摄的影响类型")
        subTitleList!!.add("选择的分辨率值越低，清晰度越高")
        subTitleList!!.add("您想在什么时间段进行拍摄")
        subTitleList!!.add("在你您选择的时间范围内，您想拍摄几次")


        StatusBarUtil.setColor(activity, Color.parseColor("#000000"), 0)
        mParamsToolbar.title = "请选择拍摄时的参数"
        (activity as AppCompatActivity).setSupportActionBar(mParamsToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        adapter = SelectParamsAdapter(titleList, subTitleList, detailList, activity)
        mParamsListView.adapter = adapter
        mParamsToolbar.setNavigationOnClickListener {
        }

        mParamsListView.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    val intent = Intent(activity, SelectTypeParamsActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("INDEX", typeIndex.toString())
                    intent.putExtras(bundle)
                    startActivityForResult(intent, 0)
                }
                1 -> {
                    val intent = Intent(activity, SelectRatioActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("INDEX", ratioIndex.toString())
                    intent.putExtras(bundle)
                    startActivityForResult(intent, 1)
                }
                2 -> {
                    val intent = Intent(activity, SelectTimeActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("TIME", time)
                    intent.putExtras(bundle)
                    startActivityForResult(intent, 2)
                }
                3 -> {
                    initNumberPicker()
                }
            }
        }

        mParamsDoneTextView.setOnClickListener {
            mPresenter.mView = this
            println(userId!! + "  " + demandType!! + "  " + demandGeo!!
                    + "  " + resolution!! + "  " + startTime!! + "  " + endTime!! + "  " + timesParam!!)
            mPresenter.submitOrder(userId!!, demandType!!, demandGeo!!
                    , resolution!!, startTime!!, endTime!!, timesParam!!)
        }
    }

    private fun initNumberPicker() {
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle("拍摄频次")
        val inflater = LayoutInflater.from(activity)
        val v = inflater.inflate(R.layout.item_dialog, null)
        builder.setView(v)

        builder.setNegativeButton("取消") { arg0, arg1 ->
            arg0.dismiss()
        }
        builder.setPositiveButton("确定") { arg0, arg1 ->
            val et = v.findViewById(R.id.mEditText) as EditText
            times = et.text.toString()
            arg0.dismiss()
            if (times != "") {
                detailList!![3] = times.toString()
                timesParam = times
                TIMES = times
                flag4 = true
                adapter!!.notifyDataSetChanged()
            } else if (times == "" && TIMES != "") {
                detailList!![3] = TIMES.toString()
                timesParam = TIMES
                TIMES = times
                flag4 = true
                adapter!!.notifyDataSetChanged()
            }
            checkDone()
        }
        val dialog = builder.create()

        dialog.show()
        dialog.window.setGravity(Gravity.CENTER)//可以设置显示的位置
    }

    private fun checkDone() {
        if (flag1 && flag2 && flag3 && flag4) {
            mParamsDoneTextView.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println(data!!.getStringExtra("TYPE"))
        if (requestCode == 0 && resultCode == AppCompatActivity.RESULT_OK) {
            val detail = data!!.getStringExtra("TYPE")
            typeIndex = detail.toInt()
            println("typeIndex$typeIndex")
            if (detail != "-1") {
                detailList!![0] = typeList!![detail.toInt()]
                flag1 = true
                adapter!!.notifyDataSetChanged()
                checkDone()
                demandType = typeIndex.toString()
            }
        } else if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            val detail = data!!.getStringExtra("RATIO")
            ratioIndex = detail.toInt()
            if (detail != "-1") {
                detailList!![1] = ratioList!![detail.toInt()]
                flag2 = true
                adapter!!.notifyDataSetChanged()
                resolution = ratioList!![detail.toInt()]
                checkDone()
            }
        } else if (requestCode == 2 && resultCode == AppCompatActivity.RESULT_OK) {
            val detail = data!!.getStringExtra("TIME")
            time = detail
            if (detail != "-") {
                detailList!![2] = detail
                flag3 = true
                adapter!!.notifyDataSetChanged()
                startTime = detail.split("-")[0]
                endTime = detail.split("-")[1]
                checkDone()
            }
        }
    }
}
