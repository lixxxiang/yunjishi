package com.yunjishi.lixiang.yunjishi.view.fragment

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Point
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.*
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.widget.*
import com.android.lixiang.base.database.UserProfile
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.base.utils.view.DimenUtil
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.baidu.location.*
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.DistanceUtil
import com.android.lixiang.base.database.DatabaseManager
import com.github.ikidou.fragmentBackHandler.BackHandlerHelper
import com.github.ikidou.fragmentBackHandler.FragmentBackHandler
import com.github.lzyzsd.jsbridge.BridgeHandler
import com.github.lzyzsd.jsbridge.DefaultHandler
import com.orhanobut.logger.Logger
import com.yunjishi.lixiang.yunjishi.GetArea

import com.yunjishi.lixiang.yunjishi.R
import com.yunjishi.lixiang.yunjishi.presenter.MissionPresenter
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.SubmitOrderBean
import com.yunjishi.lixiang.yunjishi.presenter.injection.component.DaggerMissionComponent
import com.yunjishi.lixiang.yunjishi.presenter.injection.module.MissionModule
import com.yunjishi.lixiang.yunjishi.presenter.view.MissionView
import com.yunjishi.lixiang.yunjishi.view.activity.*
import com.yunjishi.lixiang.yunjishi.view.adapter.SelectParamsAdapter
import kotlinx.android.synthetic.main.fragment_mission.*

class MissionFragment : BaseMvpFragment<MissionPresenter>(), MissionView, SensorEventListener, FragmentBackHandler {

    var mUploadMessage: ValueCallback<Uri>? = null
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
    private var center = String()
    private var geoString = String()
    private var scopeGeo = String()
    private var area: Double? = 0.0
    private var userId: String? = null
    private var demandType: String? = null
    private var demandGeo: String? = null
    private var resolution: String? = null
    private var startTime: String? = null
    private var endTime: String? = null
    private var timesParam: String? = null
    private var handleBackPressed = true
    var mSharedPreferences: SharedPreferences? = null

    var titleList: MutableList<String>? = mutableListOf()
    var subTitleList: MutableList<String>? = mutableListOf()
    var detailList: MutableList<String>? = mutableListOf()
    var typeList: MutableList<String>? = mutableListOf()
    var ratioList: MutableList<String>? = mutableListOf()
    var typeIndex = -1
    var ratioIndex = -1
    var time = ""
    var times = ""
    var TIMES = ""
    var adapter: SelectParamsAdapter? = null
    var flag1 = false
    var flag2 = false
    var flag3 = false
    var flag4 = false
    var pageIndex = 0

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        userId = (activity as MainActivity).getUserID()
    }

    override fun injectComponent() {
        DaggerMissionComponent.builder().fragmentComponent(fragmentComponent)
                .missionModule(MissionModule())
                .build().inject(this)
    }

    override fun onSubmitOrderResult(res: SubmitOrderBean) {
        println("onSubmitOrderResult$res")
        mOrderWebViewReletiveLayout.visibility = View.VISIBLE
        pageIndex = 3
        mOrderWebView.reload()

    }

    override fun onBackPressed(): Boolean {
        when (pageIndex) {
            0 -> {
                //外理返回键
                println("--0")
//                return BackHandlerHelper.handleBackPress(this)
                activity!!.moveTaskToBack(false)
                return true
            }
            1 -> {
                println("--1")
                pageIndex = 0
                mMapViewRelativeLayout.visibility = View.GONE
                return true
            }
            2 -> {
                println("--2")
                pageIndex = 1
                mSelectParamsRelativeLayout.visibility = View.GONE
                return true
            }
            3 -> {
                println("--3")
                pageIndex = 2
                mOrderWebViewReletiveLayout.visibility = View.GONE
                return true
            }
            else -> {
                // 如果不包含子Fragment
                // 或子Fragment没有外理back需求
                // 可如直接 return false;
                // 注：如果Fragment/Activity 中可以使用ViewPager 代替 this
                //
                println("》》》》")
                return BackHandlerHelper.handleBackPress(this)
            }
        }
    }


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
        initSelectViews()
        initOrder()

        mMissionFeild1.setOnClickListener {
            pageIndex = 1
            if (userId != "-1") {
                mMapViewRelativeLayout.visibility = View.VISIBLE
            } else {
                var mLoginLayout = activity!!.findViewById<LinearLayout>(R.id.mLoginLayout)
                mLoginLayout.visibility = View.VISIBLE
            }

        }

        mMissionFeild2.setOnClickListener {
            pageIndex = 3
            if (userId != "-1")
                mOrderWebViewReletiveLayout.visibility = View.VISIBLE
            else {
                var mLoginLayout = activity!!.findViewById<LinearLayout>(R.id.mLoginLayout)
                mLoginLayout.visibility = View.VISIBLE
            }

        }

        mCameraImageView.setOnClickListener {
            pageIndex = 2
            if (scopeGeo == "") {
                Toast.makeText(activity, "请选择区域", Toast.LENGTH_SHORT).show()
            } else
                mSelectParamsRelativeLayout.visibility = View.VISIBLE
        }

        mZoomInButton.setOnClickListener {
            val zoomIn: MapStatusUpdate? = MapStatusUpdateFactory.zoomIn()
            mBaiduMap!!.animateMapStatus(zoomIn)
        }

        mZoomOutButton.setOnClickListener {
            val zoomOut: MapStatusUpdate? = MapStatusUpdateFactory.zoomOut()
            mBaiduMap!!.animateMapStatus(zoomOut)
        }

//        mOrderWebViewToolbar.setNavigationOnClickListener {
//            mOrderWebViewReletiveLayout.visibility = View.GONE
//        }
    }


    private fun initOrder() {
//        mOrderWebViewToolbar.title = "我的订单"
//        (activity as AppCompatActivity).setSupportActionBar(mOrderWebViewToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mOrderWebView.setBackgroundColor(0);
//        mOrderWebViewToolbar.setNavigationOnClickListener {
//            mOrderWebViewReletiveLayout.visibility = View.INVISIBLE
//        }
        mOrderWebView.setDefaultHandler(DefaultHandler())
        mOrderWebView.webChromeClient = object : WebChromeClient() {

            fun openFileChooser(uploadMsg: ValueCallback<Uri>, AcceptType: String, capture: String) {
                this.openFileChooser(uploadMsg)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>, AcceptType: String) {
                this.openFileChooser(uploadMsg)
            }

            fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
                mUploadMessage = uploadMsg
            }
        }
        com.orhanobut.logger.Logger.d("http://202.111.178.10:12380/demand.html?userId=$userId")
        mOrderWebView.loadUrl("http://202.111.178.10:12380/demand.html?userId=$userId")
        mOrderWebView.registerHandler("demandShow", BridgeHandler { data, function ->
            println("demandId$data")
            if (data != null) {
                val intent = Intent(activity, OrderDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putString("DEMAND_ID", data)
                bundle.putString("USER_ID", userId)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })
        mOrderWebView.registerHandler("productShow", BridgeHandler { data, function ->
            println("productId$data")
            if (data != null) {
                mSharedPreferences = activity!!.getSharedPreferences("XXX", Context.MODE_PRIVATE)
                val editor = mSharedPreferences!!.edit()
                editor.putString("PRODUCT_ID", data)
                editor.commit()
                val intent = activity!!.intent
                activity!!.overridePendingTransition(0, 0)
                activity!!.finish()
                activity!!.overridePendingTransition(0, 0)
                startActivity(intent)
            }
        })
    }


    private fun initSelectViews() {
        StatusBarUtil.setColor(activity, Color.parseColor("#000000"), 0)
        mSelectParamsToolbar.title = "请选择拍摄时的参数"
        (activity as AppCompatActivity).setSupportActionBar(mSelectParamsToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        initView2()

        mSelectParamsToolbar.setNavigationOnClickListener {
            mSelectParamsRelativeLayout.visibility = View.GONE
        }

        mListView.setOnItemClickListener { parent, view, position, id ->
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

        mDoneTextView.setOnClickListener {
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
                detailList!![3] = times
                timesParam = times
                TIMES = times
                flag4 = true
                adapter!!.notifyDataSetChanged()
            } else if (times == "" && TIMES != "") {
                detailList!![3] = TIMES
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

    private fun initView2() {
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

        adapter = SelectParamsAdapter(titleList, subTitleList, detailList, activity)
        mListView.adapter = adapter
    }

    private fun checkDone() {
        if (flag1 && flag2 && flag3 && flag4) {
            mDoneTextView.visibility = View.VISIBLE
        }
    }

    @SuppressLint("ResourceType")
    private fun initView() {

        mMapToolbar.title = "请选择拍摄区域"
        (activity as AppCompatActivity).setSupportActionBar(mMapToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        var width = activity!!.application.resources.displayMetrics.widthPixels
        var height = activity!!.application.resources.displayMetrics.heightPixels
        //1200 1920 px   706 1129 dp   768
        println(DimenUtil().px2dip(activity!!, height.toFloat()))
        println(DimenUtil().px2dip(activity!!, width.toFloat()))

        task = Runnable {
            // TODO Auto-generated method stub
            val animator: ObjectAnimator = ObjectAnimator.ofFloat(mScanImageView!!,
                    "translationY",
                    0F,
                    DimenUtil().dip2px(activity!!, 396F).toFloat(),
                    0F)
            animator.duration = 5000
            animator.repeatCount = ValueAnimator.INFINITE//无限循环
            animator.repeatMode = ValueAnimator.INFINITE//
            animator.start()
        }

        handler.post(task)

        mMapToolbar.setNavigationOnClickListener {
            mMapViewRelativeLayout.visibility = View.GONE
        }

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
        mUiSettings.isOverlookingGesturesEnabled = false

        val listener: BaiduMap.OnMapStatusChangeListener = object : BaiduMap.OnMapStatusChangeListener {
            override fun onMapStatusChangeStart(p0: MapStatus?) {}
            override fun onMapStatusChangeStart(p0: MapStatus?, p1: Int) {}
            override fun onMapStatusChange(p0: MapStatus?) {}
            override fun onMapStatusChangeFinish(p0: MapStatus?) {

                if (context != null) {
                    val ltp = Point()
                    ltp.x = DimenUtil().dip2px(context!!, 356.5F)
                    ltp.y = DimenUtil().dip2px(context!!, 105F)
                    val lt = mBaiduMap!!.projection.fromScreenLocation(ltp)

                    val rbp = Point()
                    rbp.x = DimenUtil().dip2px(context!!, 782.5F)
                    rbp.y = DimenUtil().dip2px(context!!, 531F)
                    val rb = mBaiduMap!!.projection.fromScreenLocation(rbp)

                    if (mBaiduMap!!.projection != null) {
                        geoString = String.format("%s,%s,%s,%s", lt.longitude, lt.latitude, rb.longitude, rb.latitude)
                        println("geoString$geoString")
                        scopeGeo = geoFormat(geoString)
                        demandGeo = scopeGeo
                        println("scopeGeo$scopeGeo")
                        center = String.format("%s,%s", (lt.longitude + rb.longitude) / 2, (lt.latitude + rb.latitude) / 2)
                        val leftTop = LatLng(lt.latitude, lt.longitude)
                        val rightBottom = LatLng(rb.latitude, rb.longitude)
                        val leftBottom = LatLng(rb.latitude, lt.longitude)
                        val rightTop = LatLng(lt.latitude, rb.longitude)
                        var list:MutableList<LatLng> = mutableListOf()
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
                                mAreaTextView!!.text = String.format("当前面积：小于 0.01平方公里", temp)
                            } else
                                mAreaTextView!!.text = String.format("当前面积：%s 亿平方公里", temp)
                        } else {
                            mAreaTextView!!.text = String.format("当前面积：%s 平方公里", temp)
                        }
                    }
                }
            }
        }
        mMapView!!.map.setOnMapStatusChangeListener(listener)


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

    override fun onStart() {
        super.onStart()
        initMap()
    }

}
