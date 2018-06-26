package com.yunjishi.lixiang.yunjishi.view.activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.yunjishi.lixiang.yunjishi.R
import cn.jzvd.JZVideoPlayerStandard
import kotlinx.android.synthetic.main.activity_video_player.*
import cn.jzvd.JZVideoPlayer
import com.android.lixiang.base.utils.view.StatusBarUtil
import kotlinx.android.synthetic.main.activity_select_time.*


class VideoPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        StatusBarUtil.setColor(this, Color.parseColor("#000000"), 0)

        mVideoPlayer.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4",
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                "")

        mVideoPlayerToolbar.title = ""
        setSupportActionBar(mVideoPlayerToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mVideoPlayerToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        JZVideoPlayer.releaseAllVideos()
    }
}
