package com.yunjishi.lixiang.yunjishi.view.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView
import android.widget.Toast
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.yunjishi.lixiang.yunjishi.R
import com.yunjishi.lixiang.yunjishi.view.adapter.SelectTimeAdapter
import kotlinx.android.synthetic.main.activity_select_time.*
import java.text.SimpleDateFormat
import java.util.*

class SelectTimeActivity : AppCompatActivity() {

    var ca = Calendar.getInstance()
    var mYear = ca.get(Calendar.YEAR)
    var mMonth = ca.get(Calendar.MONTH)
    var mDay = ca.get(Calendar.DAY_OF_MONTH)
    var titleList: MutableList<String>? = mutableListOf()
    var detailList: MutableList<String>? = mutableListOf()
    var startTime = String()
    var endTime = String()
    var adapter: SelectTimeAdapter? = null
    var index = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_time)
        StatusBarUtil.setColor(this, Color.parseColor("#000000"), 0)

        mSelectTimeToolbar.title = "选择拍摄时间"
        setSupportActionBar(mSelectTimeToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val bundle = intent.extras
        val TIME = bundle.getString("TIME")
        initView()

        println("TIME1$TIME")
        if (TIME != ""){
            println("TIME2$TIME")
            detailList!![0] = TIME.split("-")[0]
            detailList!![1] = TIME.split("-")[1]
            adapter!!.notifyDataSetChanged()
        }

        mTimeListView.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    DatePickerDialog(this, R.style.MyDatePickerDialogTheme, onDateSetListener, mYear, mMonth, mDay).show()
                }
                1 -> {
                    DatePickerDialog(this, R.style.MyDatePickerDialogTheme, onDateSetListener2, mYear, mMonth, mDay).show()

                }
            }
        }

        mSelectTimeToolbar.setNavigationOnClickListener {
            if (compare_date(detailList!![0], detailList!![1]) == 1) {
                Toast.makeText(this, "请输入正确的时间", Toast.LENGTH_SHORT).show()
            } else {
                if(detailList!![0] == "" || detailList!![1] == ""){
                    Toast.makeText(this, "请输入正确的时间", Toast.LENGTH_SHORT).show()
                }else{
                    val intent = Intent()
                    intent.putExtra("TIME", detailList!![0] + "-" + detailList!![1])
                    setResult(Activity.RESULT_OK, intent)
                    this.finish()
                }

            }
        }
    }

    private fun initView() {
        titleList!!.add("拍摄起始时间")
        titleList!!.add("拍摄终止时间")

        detailList!!.add("")
        detailList!!.add("")

        adapter = SelectTimeAdapter(titleList, detailList, this)
        mTimeListView.adapter = adapter
        mTimeListView.choiceMode = AbsListView.CHOICE_MODE_SINGLE

    }

    private val onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        val days: String = if (mMonth + 1 < 10) {
            if (mDay < 10)
                StringBuffer().append(mYear).append(".").append("0").append(mMonth + 1).append(".").append("0").append(mDay).toString()
            else
                StringBuffer().append(mYear).append(".").append("0").append(mMonth + 1).append(".").append(mDay).toString()
        } else {
            if (mDay < 10)
                StringBuffer().append(mYear).append(".").append(mMonth + 1).append(".").append("0").append(mDay).toString()
            else
                StringBuffer().append(mYear).append(".").append(mMonth + 1).append(".").append(mDay).toString()
        }
        detailList!![0] = days
        adapter!!.notifyDataSetChanged()
    }

    private val onDateSetListener2 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        val days: String = if (mMonth + 1 < 10) {
            if (mDay < 10)
                StringBuffer().append(mYear).append(".").append("0").append(mMonth + 1).append(".").append("0").append(mDay).toString()
            else
                StringBuffer().append(mYear).append(".").append("0").append(mMonth + 1).append(".").append(mDay).toString()
        } else {
            if (mDay < 10)
                StringBuffer().append(mYear).append(".").append(mMonth + 1).append(".").append("0").append(mDay).toString()
            else
                StringBuffer().append(mYear).append(".").append(mMonth + 1).append(".").append(mDay).toString()
        }
        detailList!![1] = days
        adapter!!.notifyDataSetChanged()
    }

    @SuppressLint("SimpleDateFormat")
    private fun compare_date(DATE1: String, DATE2: String): Int {
        val df = SimpleDateFormat("yyyy.MM.dd")
        try {
            val dt1 = df.parse(DATE1)
            val dt2 = df.parse(DATE2)

            return when {
                dt1.time > dt2.time -> {
                    1
                }
                dt1.time < dt2.time -> {
                    -1
                }
                else -> 0
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return 0
    }
}
