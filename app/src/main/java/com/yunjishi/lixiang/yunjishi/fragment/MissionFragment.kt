package com.yunjishi.lixiang.yunjishi.fragment

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.ZoomControls
import com.android.lixiang.base.utils.view.DimenUtil
import com.baidu.location.*
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng

import com.yunjishi.lixiang.yunjishi.R
import com.yunjishi.lixiang.yunjishi.activity.SelectParamsActivity
import kotlinx.android.synthetic.main.fragment_mission.*
import kotlinx.coroutines.experimental.channels.actor
import org.jetbrains.anko.support.v4.startActivity

class MissionFragment : Fragment(), SensorEventListener {

    var mMapView: MapView? = null
    private var mBaiduMap: BaiduMap? = null
    var myListener = MyLocationListenner()
    private var mLocClient: LocationClient? = null
    private var isFirstLoc = true
    private var locData: MyLocationData? = null
    private var mCurrentDirection = 0
    private var mCurrentLat = 0.0
    private var mCurrentLon = 0.0
    private var mCurrentAccracy: Float = 0.toFloat()
    private var lastX: Double? = 0.0
    val handler = Handler()
    var task: Runnable? = null

    private var mTopRelativeLayout: RelativeLayout? = null
    private var mLeftRelativeLayout: RelativeLayout? = null
    private var mRightRelativeLayout: RelativeLayout? = null
    private var mbottomRelativeLayout: RelativeLayout? = null


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val x = event!!.values[SensorManager.DATA_X].toDouble()
        if (Math.abs(x - lastX!!) > 1.0) {
            mCurrentDirection = x.toInt()
            locData = MyLocationData.Builder()
                    .accuracy(0F)
                    .direction(mCurrentDirection.toFloat()).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build()
            mBaiduMap!!.setMyLocationData(locData)
        }
        lastX = x
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mission, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        handlePermisson()
        initMap()

        mFeild1.setOnClickListener {
            mSelectAreaRelativeLayout.visibility = View.INVISIBLE
            mMapViewRelativeLayout.visibility = View.VISIBLE
        }

        mCameraImageView.setOnClickListener{
            handler.removeCallbacks(task)
            startActivity<SelectParamsActivity>()
        }

        mZoomInButton.setOnClickListener {
            val zoomIn: MapStatusUpdate? = MapStatusUpdateFactory.zoomIn()
            mBaiduMap!!.animateMapStatus(zoomIn)
        }

        mZoomOutButton.setOnClickListener {
            val zoomOut: MapStatusUpdate? = MapStatusUpdateFactory.zoomOut()
            mBaiduMap!!.animateMapStatus(zoomOut)


        }
    }

    @SuppressLint("ResourceType")
    private fun initView() {

        var width = activity!!.application.resources.displayMetrics.widthPixels
        var height = activity!!.application.resources.displayMetrics.heightPixels
        //1200 1920 px   706 1129 dp   768
        println(DimenUtil().px2dip(activity!!,height.toFloat()))
        println(DimenUtil().px2dip(activity!!,width.toFloat()))

        task = object : Runnable {
            override fun run() {
                // TODO Auto-generated method stub
                handler.postDelayed(this, 3 * 1000)
                val curTranslationY = mScanImageView!!.translationY
                val animator: ObjectAnimator = ObjectAnimator.ofFloat(mScanImageView!!,
                        "translationY",
                        curTranslationY,
                        DimenUtil().dip2px(activity!!, 396F).toFloat(),
                        curTranslationY)
                animator.duration = 3000
                animator.start()
            }
        }

        handler.post(task)
    }

    private fun initMap() {
        val mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL
        mMapView = mapView
        mLocClient = LocationClient(activity)
        mLocClient!!.registerLocationListener(myListener)
        val option = LocationClientOption()
        option.isOpenGps = true
        option.setCoorType("bd09ll")
        option.setScanSpan(1000)
        option.setAddrType("all")
        option.setIsNeedLocationPoiList(true)
        mLocClient!!.locOption = option
        mLocClient!!.start()

        mBaiduMap = mMapView!!.map
        mBaiduMap!!.isMyLocationEnabled = true
        mBaiduMap!!.setMyLocationConfigeration(MyLocationConfiguration(mCurrentMode, true, null))
        val builder = MapStatus.Builder()
        builder.overlook(0f)
        val child = mMapView!!.getChildAt(1)
        if (child != null && (child is ImageView || child is ZoomControls)) {
            child.visibility = View.INVISIBLE
        }
        mMapView!!.showScaleControl(false)
        mMapView!!.showZoomControls(false)
        val mUiSettings = mBaiduMap!!.uiSettings
        mUiSettings.isScrollGesturesEnabled = true
        mUiSettings.isOverlookingGesturesEnabled = true
        mUiSettings.isZoomGesturesEnabled = true


    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun handlePermisson() {
        val permission = Manifest.permission.ACCESS_COARSE_LOCATION
        val checkSelfPermission = ActivityCompat.checkSelfPermission(activity!!, permission)
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission)) {
            } else {
                myRequestPermission()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun myRequestPermission() {
        val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView!!.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mMapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView!!.onPause()
    }

    inner class MyLocationListenner : BDLocationListener {
        var lati: Double = 0.toDouble()
        var longi: Double = 0.toDouble()
        var address: String = ""
        internal lateinit var poi: List<Poi>

        override fun onReceiveLocation(location: BDLocation?) {
            if (location == null || mMapView == null) {
                return
            }

            val locData = MyLocationData.Builder()
                    .accuracy(0F)
                    .direction(mCurrentDirection.toFloat())
                    .latitude(location.latitude)
                    .longitude(location.longitude).build()
            lati = location.latitude
            longi = location.longitude
            mCurrentLat = location.latitude
            mCurrentLon = location.longitude
            address = location.addrStr
            mCurrentAccracy = location.radius
            poi = location.poiList
            mBaiduMap!!.setMyLocationData(locData)
            if (isFirstLoc) {
                isFirstLoc = false
                val ll = LatLng(location.latitude,
                        location.longitude)
                val builder = MapStatus.Builder()
                builder.target(ll).zoom(8.0f)
                mBaiduMap!!.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
            }
        }

        fun onConnectHotSpotMessage(s: String, i: Int) {

        }
    }
}
