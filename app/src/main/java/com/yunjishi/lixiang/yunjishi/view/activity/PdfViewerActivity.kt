package com.yunjishi.lixiang.yunjishi.view.activity

import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.yunjishi.lixiang.yunjishi.R
import es.voghdev.pdfviewpager.library.remote.DownloadFile
import kotlinx.android.synthetic.main.activity_pdf_viewer.*
import kotlinx.android.synthetic.main.fragment_mission.*
import java.lang.Exception
import es.voghdev.pdfviewpager.library.RemotePDFViewPager
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import es.voghdev.pdfviewpager.library.util.FileUtil
import android.widget.LinearLayout
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.android.lixiang.base.utils.view.StatusBarUtil
import kotlinx.android.synthetic.main.activity_select_time.*


//class PdfViewerActivity : AppCompatActivity(), DownloadFile.Listener {
//    var adapter: PDFPagerAdapter? = null
//    var remotePDFViewPager: RemotePDFViewPager? = null
//
//    override fun onSuccess(url: String?, destinationPath: String?) {
//        adapter = PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url))
//        remotePDFViewPager!!.setAdapter(adapter)
//    }
//
//    override fun onFailure(e: Exception?) {
//    }
//
//    override fun onProgressUpdate(progress: Int, total: Int) {
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_pdf_viewer)
//        mPdfToolbar.title = "我的订单"
//        setSupportActionBar(mPdfToolbar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        mPdfToolbar.setNavigationOnClickListener {
//            finish()
//        }
//        val url = "http://www.cals.uidaho.edu/edComm/curricula/CustRel_curriculum/content/sample.pdf"
//
//        remotePDFViewPager = RemotePDFViewPager(this, url, this)
//
//
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//
//        adapter!!.close()
//    }
//}

class PdfViewerActivity : AppCompatActivity(), DownloadFile.Listener {
    internal lateinit var root: LinearLayout
    internal lateinit var remotePDFViewPager: RemotePDFViewPager
    internal var adapter: PDFPagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)
        StatusBarUtil.setColor(this, Color.parseColor("#000000"), 0)

        mPdfToolbar.title = "详情"
        setSupportActionBar(mPdfToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        btn_download.setOnClickListener {
        remotePDFViewPager = RemotePDFViewPager(this, "http://202.111.178.10/unzip/thematic/石河子长势同期对比201706/石河子作物长势监测产品06com.pdf", this)
        remotePDFViewPager.id = R.id.pdfViewPager
        remote_pdf_root.addView(remotePDFViewPager,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
//        }
        mPdfToolbar.setNavigationOnClickListener {
            this.finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        if (adapter != null) {
            adapter!!.close()
        }
    }


    override fun onSuccess(url: String, destinationPath: String) {
        adapter = PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url))
        remotePDFViewPager.adapter = adapter
    }

    override fun onFailure(e: Exception) {
        e.printStackTrace()
    }

    override fun onProgressUpdate(progress: Int, total: Int) {

    }

    companion object {

        fun open(context: Context) {
            val i = Intent(context, PdfViewerActivity::class.java)
            context.startActivity(i)
        }
    }
}

