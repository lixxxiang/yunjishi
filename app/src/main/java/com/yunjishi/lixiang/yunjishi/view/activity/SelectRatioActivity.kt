package com.yunjishi.lixiang.yunjishi.view.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.yunjishi.lixiang.yunjishi.R
import com.yunjishi.lixiang.yunjishi.view.adapter.SelectTypeParamsAdapter
import kotlinx.android.synthetic.main.activity_select_ratio.*

class SelectRatioActivity : AppCompatActivity() {

    var titleList: MutableList<String>? = mutableListOf()
    var adapter: SelectTypeParamsAdapter? = null
    var index = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_ratio)
        StatusBarUtil.setColor(this, Color.parseColor("#000000"), 0)

        mSelectRatioToolbar.title = "分辨率"
        setSupportActionBar(mSelectRatioToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intent = intent
        val bundle = intent.extras
        val INDEX = bundle.getString("INDEX")
        initView()


        if (bundle.getString("INDEX") != "-1"){
            adapter!!.setSelectedItem(INDEX.toInt())
            adapter!!.notifyDataSetInvalidated()
        }

        mRatioListView.setOnItemClickListener { parent, view, position, id ->
            index = position
            println(position)
            adapter!!.setSelectedItem(position)
            adapter!!.notifyDataSetInvalidated()
        }

        mSelectRatioToolbar.setNavigationOnClickListener {
            val intent = Intent()
            if(index == -1 && INDEX == "-1"){
                intent.putExtra("RATIO", index.toString())
                setResult(Activity.RESULT_OK, intent)
                this.finish()
            }else if(index == -1 && INDEX != "-1"){
                intent.putExtra("RATIO", INDEX)
                setResult(Activity.RESULT_OK, intent)
                this.finish()
            }else{
                intent.putExtra("RATIO", index.toString())
                setResult(Activity.RESULT_OK, intent)
                this.finish()
            }
        }
    }

    private fun initView() {
        titleList!!.add("小于 1m")
        titleList!!.add("1m - 3m")
        titleList!!.add("3m - 8m")
        titleList!!.add("8m - 16m")

        adapter = SelectTypeParamsAdapter(titleList, this)
        mRatioListView.adapter = adapter
        mRatioListView.choiceMode = AbsListView.CHOICE_MODE_SINGLE

    }
}
