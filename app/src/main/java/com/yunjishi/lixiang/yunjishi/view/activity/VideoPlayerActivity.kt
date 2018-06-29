package com.yunjishi.lixiang.yunjishi.view.activity

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.yunjishi.lixiang.yunjishi.R
import cn.jzvd.JZVideoPlayerStandard
import kotlinx.android.synthetic.main.activity_video_player.*
import cn.jzvd.JZVideoPlayer
import com.android.lixiang.base.utils.view.StatusBarUtil
import io.vov.vitamio.Vitamio
import io.vov.vitamio.widget.MediaController
import kotlinx.android.synthetic.main.activity_select_time.*


class VideoPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Vitamio.isInitialized(applicationContext)
        setContentView(R.layout.activity_video_player)
        StatusBarUtil.setColor(this, Color.parseColor("#000000"), 0)


//        println(intent.extras.getString("URL"))
//        mVideoPlayer.setUp("http://202.111.178.10/unzip/video/长滩1/长滩1.mp4",
//                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
//                "")
        if (Vitamio.isInitialized(this)) {
            videoview!!.setVideoPath(intent.extras.getString("URL"))
//                        videoview!!.setVideoPath("http://202.111.178.10/unzip/video/长滩1/长滩1.mp4")

            videoview!!.setMediaController(MediaController(this))
            videoview!!.start()
        }

        mVideoPlayerToolbar.title = "视频"
        setSupportActionBar(mVideoPlayerToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mVideoPlayerToolbar.setNavigationOnClickListener {
            this.finish()
        }
    }

    override fun onBackPressed() {
        this.finish()
    }
}
