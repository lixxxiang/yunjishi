package com.yunjishi.lixiang.yunjishi.view.activity

import android.app.Activity
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.yunjishi.lixiang.yunjishi.R
import com.yunjishi.lixiang.yunjishi.view.adapter.SelectTypeParamsAdapter
import kotlinx.android.synthetic.main.activity_select_type_params.*
import android.content.Intent



class SelectTypeParamsActivity : AppCompatActivity() {
    var titleList: MutableList<String>? = mutableListOf()
    var adapter: SelectTypeParamsAdapter? = null
    var index = -1
    var INDEX = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_type_params)
        StatusBarUtil.setColor(this, Color.parseColor("#000000"), 0)

        mSelectTypeParamsToolbar.title = "类型"
        setSupportActionBar(mSelectTypeParamsToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val bundle = intent.extras
        INDEX = bundle.getString("INDEX")
//        println(bundle.getString("INDEX"))
        initView()

        if (bundle.getString("INDEX") != "-1"){
            adapter!!.setSelectedItem(INDEX.toInt())
            adapter!!.notifyDataSetInvalidated()
        }

        mTypeListView.setOnItemClickListener { parent, view, position, id ->
            index = position
            adapter!!.setSelectedItem(position)
            adapter!!.notifyDataSetInvalidated()
        }

        mSelectTypeParamsToolbar.setNavigationOnClickListener {
            println("index$index")
            println("INDEX$INDEX")
            val intent = Intent()
            if(index == -1 && INDEX == "-1"){
                intent.putExtra("TYPE", index.toString())
                setResult(Activity.RESULT_OK, intent)
                this.finish()
            }else if(index == -1 && INDEX != "-1"){
                intent.putExtra("TYPE", INDEX)
                setResult(Activity.RESULT_OK, intent)
                this.finish()
            }else{
                intent.putExtra("TYPE", index.toString())
                setResult(Activity.RESULT_OK, intent)
                this.finish()
            }
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        println("index$index")
        println("INDEX$INDEX")
        val intent = Intent()
        if(index == -1 && INDEX == "-1"){
            intent.putExtra("TYPE", index.toString())
            setResult(Activity.RESULT_OK, intent)
            this.finish()
        }else if(index == -1 && INDEX != "-1"){
            intent.putExtra("TYPE", INDEX)
            setResult(Activity.RESULT_OK, intent)
            this.finish()
        }else{
            println("ddddddd")
            intent.putExtra("TYPE", index.toString())
            setResult(Activity.RESULT_OK, intent)
            this.finish()
        }
    }

    private fun initView() {
        titleList!!.add("标准卫星图")
        titleList!!.add("夜光卫星图")
        titleList!!.add("卫星视频")

        adapter = SelectTypeParamsAdapter(titleList, this)
        mTypeListView.adapter = adapter
        mTypeListView.choiceMode = AbsListView.CHOICE_MODE_SINGLE
    }
}
