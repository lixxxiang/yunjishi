package com.yunjishi.lixiang.yunjishi.view.fragment

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Point
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
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.lixiang.base.utils.view.DimenUtil
import com.baidu.location.*
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.DistanceUtil
import com.orhanobut.logger.Logger
import com.yunjishi.lixiang.yunjishi.GetArea
import com.yunjishi.lixiang.yunjishi.R.id.*
import com.yunjishi.lixiang.yunjishi.view.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : Fragment(), View.OnClickListener, SensorEventListener {
    private var lastX: Double? = 0.0
    private var locData: MyLocationData? = null

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

    override fun onClick(v: View?) {
        when (v) {
            ZIB -> {
                val zoomIn: MapStatusUpdate? = MapStatusUpdateFactory.zoomIn()
                mBaiduMap!!.animateMapStatus(zoomIn)
            }

            ZOB -> {
                val zoomOut: MapStatusUpdate? = MapStatusUpdateFactory.zoomOut()
                mBaiduMap!!.animateMapStatus(zoomOut)
            }

            CA -> {
                if (scopeGeo == "") {
                    Toast.makeText(activity, "请选择区域", Toast.LENGTH_SHORT).show()
                } else
                    (activity as MainActivity).changeFragment(4)
            }
        }
    }

    var mMapView: MapView? = null
    private var mBaiduMap: BaiduMap? = null
    var myListener = MyLocationListenner()
    private var mLocClient: LocationClient? = null
    private var isFirstLoc = true
    private var mCurrentDirection = 0
    private var mCurrentLat = 0.0
    private var mCurrentLon = 0.0
    private var mCurrentAccracy: Float = 0.toFloat()
    private var geoString = String()
    private var scopeGeo = String()
    private var area: Double? = 0.0
    private var demandGeo: String? = null
    private var center = String()
    private var T: RelativeLayout? = null
    private var R: RelativeLayout? = null
    private var B: RelativeLayout? = null
    private var L: RelativeLayout? = null
    private var C: RelativeLayout? = null
    private var CA: RelativeLayout? = null
    private var TV: AppCompatTextView? = null
    private var ITL: AppCompatImageView? = null
    private var ITR: AppCompatImageView? = null
    private var IBR: AppCompatImageView? = null
    private var IBL: AppCompatImageView? = null
    private var S: AppCompatImageView? = null// scan line
    var task: Runnable? = null
    val handler = Handler()
    private var Z: AppCompatImageView? = null
    private var ZIB: AppCompatButton? = null
    private var ZOB: AppCompatButton? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.yunjishi.lixiang.yunjishi.R.layout.fragment_map, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        handlePermisson()
        initMap()
        initViews()
    }

    @SuppressLint("ResourceType")
    private fun initViews() {

        var screenHeight = activity!!.application.resources.displayMetrics.heightPixels.toFloat()
        var screenWidth = activity!!.application.resources.displayMetrics.widthPixels.toFloat()
        var realWidth = screenWidth * 964 / 1024
        val SQUARE = screenHeight * 463 / 768
        val THeight = screenHeight * 94 / 768
        val RWidth = (realWidth - SQUARE) * 0.5
        val BHeight = screenHeight * 99 / 1024
        val CAWidth = screenHeight * 46 / 768
        val IWidth = screenHeight * 31 / 768// four corner purple
        val SWidth = screenHeight * 424 / 768// four corner purple
        val SHeight = screenHeight * 3 / 768
        val ZWidth = screenHeight * 28 / 768
        val ZHeight = screenHeight * 59 / 768
        val BWidth = screenHeight * 40 / 768

        Logger.d("$THeight   $screenHeight $SQUARE  $BHeight")

        T = RelativeLayout(activity)
        T!!.id = 1
        val TL = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, THeight.toInt())
        T!!.setBackgroundColor(Color.parseColor("#99000000"))
        T!!.layoutParams = TL
        mMapRoot.addView(T)

        R = RelativeLayout(activity)
        R!!.id = 2
        val RL = RelativeLayout.LayoutParams(RWidth.toInt(), SQUARE.toInt())
        RL.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        RL.setMargins(0, THeight.toInt(), 0, 0)
        R!!.setBackgroundColor(Color.parseColor("#99000000"))
        R!!.layoutParams = RL
        mMapRoot.addView(R)

        B = RelativeLayout(activity)
        B!!.id = 3
        val BL = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, BHeight.toInt())
        BL.addRule(RelativeLayout.BELOW, R!!.id)
        B!!.setBackgroundColor(Color.parseColor("#99000000"))
        B!!.layoutParams = BL
        mMapRoot.addView(B)

        L = RelativeLayout(activity)
        L!!.id = 4
        val LL = RelativeLayout.LayoutParams(RWidth.toInt(), SQUARE.toInt())
        LL.setMargins(0, THeight.toInt(), 0, 0)
        L!!.setBackgroundColor(Color.parseColor("#99000000"))
        L!!.layoutParams = LL
        mMapRoot.addView(L)

        C = RelativeLayout(activity)
        C!!.id = 5
        val CL = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        CL.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        CL.addRule(RelativeLayout.BELOW, B!!.id)
        C!!.setBackgroundColor(Color.parseColor("#BF000000"))
        C!!.layoutParams = CL
        mMapRoot.addView(C)

        CA = RelativeLayout(activity)
        CA!!.id = 6
        val CAL = RelativeLayout.LayoutParams(CAWidth.toInt(), CAWidth.toInt())
        CAL.addRule(RelativeLayout.CENTER_IN_PARENT)
        CA!!.setBackgroundResource(com.yunjishi.lixiang.yunjishi.R.drawable.ic_camera)
        CA!!.layoutParams = CAL
        C!!.addView(CA)
        CA!!.setOnClickListener(this)


        TV = AppCompatTextView(activity)
        TV!!.id = 7
        val TVL = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        TVL.addRule(RelativeLayout.CENTER_IN_PARENT)
        TV!!.setTextColor(Color.parseColor("#FFFFFF"))
        TV!!.textSize = 14F
        TV!!.layoutParams = TVL
        B!!.addView(TV)

        mMapFragmentbar.title = "请选择拍摄区域"
        (activity as AppCompatActivity).setSupportActionBar(mMapFragmentbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mMapFragmentbar.setNavigationOnClickListener {
            (activity as MainActivity).changeFragment(2)
        }

        ITL = AppCompatImageView(activity)
        val ITLL = RelativeLayout.LayoutParams(IWidth.toInt(), IWidth.toInt())
        ITLL.addRule(RelativeLayout.BELOW, T!!.id)
        ITLL.addRule(RelativeLayout.RIGHT_OF, L!!.id)
        ITL!!.setBackgroundResource(com.yunjishi.lixiang.yunjishi.R.drawable.ic_lt)
        ITL!!.layoutParams = ITLL
        mMapRoot!!.addView(ITL)

        ITR = AppCompatImageView(activity)
        val ITRL = RelativeLayout.LayoutParams(IWidth.toInt(), IWidth.toInt())
        ITRL.addRule(RelativeLayout.BELOW, T!!.id)
        ITRL.addRule(RelativeLayout.LEFT_OF, R!!.id)
        ITR!!.setBackgroundResource(com.yunjishi.lixiang.yunjishi.R.drawable.ic_rt)
        ITR!!.layoutParams = ITRL
        mMapRoot!!.addView(ITR)

        IBR = AppCompatImageView(activity)
        val IBRL = RelativeLayout.LayoutParams(IWidth.toInt(), IWidth.toInt())
        IBRL.addRule(RelativeLayout.ABOVE, B!!.id)
        IBRL.addRule(RelativeLayout.LEFT_OF, R!!.id)
        IBR!!.setBackgroundResource(com.yunjishi.lixiang.yunjishi.R.drawable.ic_rb)
        IBR!!.layoutParams = IBRL
        mMapRoot!!.addView(IBR)

        IBL = AppCompatImageView(activity)
        val IBLL = RelativeLayout.LayoutParams(IWidth.toInt(), IWidth.toInt())
        IBLL.addRule(RelativeLayout.ABOVE, B!!.id)
        IBLL.addRule(RelativeLayout.RIGHT_OF, L!!.id)
        IBL!!.setBackgroundResource(com.yunjishi.lixiang.yunjishi.R.drawable.ic_lb)
        IBL!!.layoutParams = IBLL
        mMapRoot!!.addView(IBL)

        S = AppCompatImageView(activity)
        val SL = RelativeLayout.LayoutParams(SWidth.toInt(), SHeight.toInt())
        SL.addRule(RelativeLayout.LEFT_OF, R!!.id)
        SL.addRule(RelativeLayout.BELOW, T!!.id)
        SL.addRule(RelativeLayout.RIGHT_OF, L!!.id)
        SL.setMargins(10, 0, 10, 0)
        S!!.setBackgroundResource(com.yunjishi.lixiang.yunjishi.R.drawable.img_scan)
        S!!.layoutParams = SL
        mMapRoot!!.addView(S)

        task = Runnable {
            // TODO Auto-generated method stub
            val animator: ObjectAnimator = ObjectAnimator.ofFloat(S!!, "translationY", 0F, SQUARE, 0F)
            animator.duration = 5000
            animator.repeatCount = ValueAnimator.INFINITE//无限循环
            animator.repeatMode = ValueAnimator.INFINITE
            animator.start()
        }

        handler.post(task)

        Z = AppCompatImageView(activity)
        Z!!.id = 8
        val ZL = RelativeLayout.LayoutParams(ZWidth.toInt(), ZHeight.toInt())
        ZL.addRule(RelativeLayout.CENTER_VERTICAL)
        ZL.setMargins(47, 0, 0, 0)
        Z!!.setBackgroundResource(com.yunjishi.lixiang.yunjishi.R.drawable.ic_zoom)
        Z!!.layoutParams = ZL
        R!!.addView(Z)

        ZIB = AppCompatButton(activity)
        ZIB!!.id = 9
        val ZIBL = RelativeLayout.LayoutParams(ZWidth.toInt(), ZWidth.toInt())
        ZIBL.addRule(RelativeLayout.ALIGN_TOP, Z!!.id)
        ZIBL.setMargins(41, -10, 0, 0)
        ZIB!!.setBackgroundColor(Color.parseColor("#00000000"))
        ZIB!!.layoutParams = ZIBL
        R!!.addView(ZIB)
        ZIB!!.setOnClickListener(this)

        ZOB = AppCompatButton(activity)
        val ZOBL = RelativeLayout.LayoutParams(ZWidth.toInt(), ZWidth.toInt())
        ZOBL.addRule(RelativeLayout.BELOW, ZIB!!.id)
        ZOBL.setMargins(41, 0, 0, 0)
        ZOB!!.setBackgroundColor(Color.parseColor("#00000000"))
        ZOB!!.layoutParams = ZOBL
        R!!.addView(ZOB)
        ZOB!!.setOnClickListener(this)

    }

    private fun initMap() {
        val mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL
        mMapView = mMapFragmentMapView
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
        mUiSettings.isOverlookingGesturesEnabled = false

        val listener: BaiduMap.OnMapStatusChangeListener = object : BaiduMap.OnMapStatusChangeListener {
            override fun onMapStatusChangeStart(p0: MapStatus?) {}
            override fun onMapStatusChangeStart(p0: MapStatus?, p1: Int) {}
            override fun onMapStatusChange(p0: MapStatus?) {}
            override fun onMapStatusChangeFinish(p0: MapStatus?) {

                if (context != null) {


                    if (mBaiduMap!!.projection != null) {

                        val ltp = Point()
                        ltp.x = DimenUtil().dip2px(context!!, 356.5F)
                        ltp.y = DimenUtil().dip2px(context!!, 105F)
                        val lt = mBaiduMap!!.projection.fromScreenLocation(ltp)

                        val rbp = Point()
                        rbp.x = DimenUtil().dip2px(context!!, 782.5F)
                        rbp.y = DimenUtil().dip2px(context!!, 531F)
                        val rb = mBaiduMap!!.projection.fromScreenLocation(rbp)


                        geoString = String.format("%s,%s,%s,%s", lt.longitude, lt.latitude, rb.longitude, rb.latitude)
                        println("geoString$geoString")
                        scopeGeo = geoFormat(geoString)
                        demandGeo = scopeGeo
                        var mSharedPreferences: SharedPreferences? = null
                        mSharedPreferences = activity!!.getSharedPreferences("GEO", Context.MODE_PRIVATE)
                        val editor = mSharedPreferences!!.edit()
                        editor.putString("geo", demandGeo)
                        editor.commit()
                        println("scopeGeo$scopeGeo")
                        center = String.format("%s,%s", (lt.longitude + rb.longitude) / 2, (lt.latitude + rb.latitude) / 2)
                        val leftTop = LatLng(lt.latitude, lt.longitude)
                        val rightBottom = LatLng(rb.latitude, rb.longitude)
                        val leftBottom = LatLng(rb.latitude, lt.longitude)
                        val rightTop = LatLng(lt.latitude, rb.longitude)
                        var list: MutableList<LatLng> = mutableListOf()
                        list.add(leftTop)
                        list.add(rightTop)
                        list.add(rightBottom)
                        list.add(leftBottom)

                        Logger.d(GetArea.getArea(list))
                        println(GetArea.getArea(list))
                        area = DistanceUtil.getDistance(leftTop, rightBottom) * DistanceUtil.getDistance(leftTop, rightBottom) / 2000000
                        val areaString = area.toString()
                        val temp = areaString.substring(0, areaString.indexOf(".") + 3)
                        if (areaString.contains("E")) {
                            if (areaString.contains("-")) {
                                TV!!.text = String.format("当前面积：小于 0.01平方公里", temp)
                            } else
                                TV!!.text = String.format("当前面积：%s 亿平方公里", temp)
                        } else {
                            TV!!.text = String.format("当前面积：%s 平方公里", temp)
                        }
                    }
                }
            }
        }
        mMapView!!.map.setOnMapStatusChangeListener(listener)
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

    fun geoFormat(geo: String): String {
        val prefix = "{\"type\":\"Polygon\",\"coordinates\":[["
        val suffix = "]]}"
        val geoArray = geo.split(",".toRegex())
        val data = "[" + geoArray[0] + "," + geoArray[1] +
                "],[" + geoArray[2] + "," + geoArray[1] +
                "],[" + geoArray[2] + "," + geoArray[3] +
                "],[" + geoArray[0] + "," + geoArray[3] +
                "],[" + geoArray[0] + "," + geoArray[1] +
                "]"
        return String.format("%s%s%s", prefix, data, suffix)
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView!!.onDestroy()
        handler.removeCallbacks(task)
    }

    override fun onResume() {
        super.onResume()
        mMapView!!.onResume()
        handler.post(task)
    }

    override fun onPause() {
        super.onPause()
        mMapView!!.onPause()
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
//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//        }
    }
}
