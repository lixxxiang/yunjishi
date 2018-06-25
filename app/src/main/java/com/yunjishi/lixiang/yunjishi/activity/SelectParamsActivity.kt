//package com.yunjishi.lixiang.yunjishi.activity
//
//import android.graphics.Color
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.support.v7.app.AlertDialog
//import android.view.Gravity
//import com.android.lixiang.base.utils.view.StatusBarUtil
//import com.yunjishi.lixiang.yunjishi.R
//import com.yunjishi.lixiang.yunjishi.adapter.SelectParamsAdapter
//import kotlinx.android.synthetic.main.activity_select_params.*
//import org.jetbrains.anko.startActivity
//import android.view.LayoutInflater
//import android.content.Intent
//import android.view.View
//import java.util.logging.Logger
//import android.widget.EditText
//
//
//
//
//class SelectParamsActivity : AppCompatActivity() {
//
//    var titleList: MutableList<String>? = mutableListOf()
//    var subTitleList: MutableList<String>? = mutableListOf()
//    var detailList: MutableList<String>? = mutableListOf()
//    var typeList: MutableList<String>? = mutableListOf()
//    var ratioList: MutableList<String>? = mutableListOf()
//    var typeIndex = -1
//    var ratioIndex = -1
//    var time = ""
//    var times = ""
//    var TIMES = ""
//    var adapter: SelectParamsAdapter? = null
//    var flag1 = false
//    var flag2 = false
//    var flag3 = false
//    var flag4 = false
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_select_params)
//        StatusBarUtil.setColor(this, Color.parseColor("#000000"), 0)
//        mSelectParamsToolbar.title = "请选择拍摄时的参数"
//        setSupportActionBar(mSelectParamsToolbar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//
//        initView()
//
//        mListView.setOnItemClickListener { parent, view, position, id ->
//            when (position) {
//                0 -> {
//                    val intent = Intent(this, SelectTypeParamsActivity::class.java)
//                    val bundle = Bundle()
//                    bundle.putString("INDEX",typeIndex.toString())
//                    intent.putExtras(bundle)
//                    startActivityForResult(intent, 0)
//                }
//                1 -> {
//                    val intent = Intent(this, SelectRatioActivity::class.java)
//                    val bundle = Bundle()
//                    bundle.putString("INDEX",ratioIndex.toString())
//                    intent.putExtras(bundle)
//                    startActivityForResult(intent, 1)
//                }
//                2 -> {
//                    val intent = Intent(this, SelectTimeActivity::class.java)
//                    val bundle = Bundle()
//                    bundle.putString("TIME",time)
//                    intent.putExtras(bundle)
//                    startActivityForResult(intent, 2)
//                }
//                3 -> {
//                    initNumberPicker()
//                }
//            }
//        }
//
//        mDoneTextView.setOnClickListener {
//            startActivity<OrderDetailActivity>()
//        }
//    }
//
//    private fun initNumberPicker() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("拍摄频次")
//        val inflater = LayoutInflater.from(this)
//        val v = inflater.inflate(R.layout.item_dialog, null)
//        builder.setView(v)
//
//        builder.setNegativeButton("取消") { arg0, arg1 ->
//            arg0.dismiss()
//        }
//        builder.setPositiveButton("确定") { arg0, arg1 ->
//            val et = v.findViewById(R.id.mEditText) as EditText
//            times = et.text.toString()
//            arg0.dismiss()
//            if (times != ""){
//                detailList!![3] = times
//                TIMES = times
//                flag4 = true
//                adapter!!.notifyDataSetChanged()
//            }else if(times == "" && TIMES != ""){
//                detailList!![3] = TIMES
//                TIMES = times
//                flag4 = true
//                adapter!!.notifyDataSetChanged()
//            }
//            checkDone()
//        }
//        val dialog = builder.create()
//
//        dialog.show()
//        dialog.window.setGravity(Gravity.CENTER)//可以设置显示的位置
//
//
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 0 && resultCode == RESULT_OK) {
//            val detail = data!!.getStringExtra("TYPE")
//            typeIndex = detail.toInt()
//            if(detail != "-1"){
//                detailList!![0] = typeList!![detail.toInt()]
//                flag1 = true
//                adapter!!.notifyDataSetChanged()
//                checkDone()
//            }
//        }else if(requestCode == 1 && resultCode == RESULT_OK){
//            val detail = data!!.getStringExtra("RATIO")
//            ratioIndex = detail.toInt()
//            if(detail != "-1"){
//                detailList!![1] = ratioList!![detail.toInt()]
//                flag2 = true
//                adapter!!.notifyDataSetChanged()
//                checkDone()
//            }
//        }else if(requestCode == 2 && resultCode == RESULT_OK){
//            val detail = data!!.getStringExtra("TIME")
//            time = detail
//            if(detail != ""){
//                detailList!![2] = detail
//                flag3 = true
//                adapter!!.notifyDataSetChanged()
//                checkDone()
//            }
//        }
//    }
//
//    private fun initView() {
//        titleList!!.add("类型")
//        titleList!!.add("分辨率")
//        titleList!!.add("拍摄时间")
//        titleList!!.add("拍摄频次")
//
//        typeList!!.add("标准卫星图")
//        typeList!!.add("夜光卫星图")
//        typeList!!.add("卫星视频")
//
//        ratioList!!.add("小于 1m")
//        ratioList!!.add("1m - 3m")
//        ratioList!!.add("3m - 8m")
//        ratioList!!.add("8m - 16m")
//
//        detailList!!.add("请选择")
//        detailList!!.add("请选择")
//        detailList!!.add("请选择")
//        detailList!!.add("请选择")
//
//        subTitleList!!.add("选择您想拍摄的影响类型")
//        subTitleList!!.add("选择的分辨率值越低，清晰度越高")
//        subTitleList!!.add("您想在什么时间段进行拍摄")
//        subTitleList!!.add("在你您选择的时间范围内，您想拍摄几次")
//
//        adapter = SelectParamsAdapter(titleList, subTitleList, detailList, this)
//        mListView.adapter = adapter
//    }
//
//    private fun checkDone(){
//        if(flag1 && flag2 && flag3 && flag4){
//            mDoneTextView.visibility = View.VISIBLE
//        }
//    }
//
//}
