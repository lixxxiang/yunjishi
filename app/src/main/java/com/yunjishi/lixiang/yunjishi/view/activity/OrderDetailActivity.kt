package com.yunjishi.lixiang.yunjishi.view.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ZoomControls
import com.android.lixiang.base.ui.activity.BaseMvpActivity
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.blankj.utilcode.util.TimeUtils
import com.yunjishi.lixiang.yunjishi.R
import com.yunjishi.lixiang.yunjishi.presenter.OrderDetailPresenter
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.DemandDetailBean
import com.yunjishi.lixiang.yunjishi.presenter.injection.component.DaggerOrderDetailComponent
import com.yunjishi.lixiang.yunjishi.presenter.injection.module.OrderDetailModule
import com.yunjishi.lixiang.yunjishi.presenter.view.OrderDetailView
import kotlinx.android.synthetic.main.activity_order_detail.*
import java.lang.Double
import java.util.ArrayList

class OrderDetailActivity : BaseMvpActivity<OrderDetailPresenter>(), OrderDetailView {

    var typeList: MutableList<String> = mutableListOf()
    var statusList: MutableList<String> = mutableListOf()
    var mBaiduMap: BaiduMap? = null

    var demandDetailBean = DemandDetailBean()
    override fun injectComponent() {
        DaggerOrderDetailComponent.builder().activityComponent(activityComponent)
                .orderDetailModule(OrderDetailModule())
                .build().inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onGetDemandDetailResult(res: DemandDetailBean) {
        demandDetailBean = res
        println(res)
        mTypeTextView.text = typeList[demandDetailBean.data.demandType]
        mRatioTextView.text = demandDetailBean.data.resolution
        mTimeTextView.text = TimeUtils.millis2String(demandDetailBean.data.startTime)
                .split(" ")[0]
                .replace("-", ".") + " - " + TimeUtils.millis2String(demandDetailBean.data.endTime)
                .split(" ")[0]
                .replace("-", ".")
        mTimesTextView.text = demandDetailBean.data.times.toString()
        mStateTextView.text = statusList[demandDetailBean.data.demandStatus]
        var temp = (demandDetailBean.data.demandArea / 1000000).toString()
        mAreaTextView.text = "面积 " + temp.substring(0, temp.indexOf(".") + 3) + " 平方公里"
        drawMap(getGeo(demandDetailBean.data.geo))

    }

    private fun drawMap(geo: MutableList<Array<String>>) {
        println(geo)
        val pts = ArrayList<LatLng>()
        for (i in 0 until geo.size) {
            val pt = LatLng(Double.parseDouble(geo[i][1]), Double.parseDouble(geo[i][0]))
            pts.add(pt)
        }
        val polygonOption = PolygonOptions()
                .points(pts)
                .stroke(Stroke(1, Color.parseColor("#F56161")))
                .fillColor(Color.parseColor("#00000000"))

        mBaiduMap!!.addOverlay(polygonOption)


        val latlng = LatLng((Double.parseDouble(geo[1][1]) + Double.parseDouble(geo[2][1])) / 2, (Double.parseDouble(geo[3][0]) + Double.parseDouble(geo[2][0])) / 2)
        val mMapStatus: MapStatus = MapStatus.Builder().target(latlng).zoom(12F).build()
        val mapStatusUpdate: MapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus)
        mBaiduMap!!.setMapStatus(mapStatusUpdate)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        StatusBarUtil.setColor(this, Color.parseColor("#333333"), 0)

        mFakeBackButton.setOnClickListener {
            this.finish()
        }

        mPresenter.mView = this
        mPresenter.getDemandDetail("123456", "1806111631000424304")

        initView()

    }

    private fun initView() {

        typeList.add("标准卫星图")
        typeList.add("夜光卫星图")
        typeList.add("卫星视频")

        statusList.add("审核中")
        statusList.add("生产中")
        statusList.add("审核未通过")
        statusList.add("订单取消")
        statusList.add("订单完成")
        statusList.add("部分完成")

        mBaiduMap = mOrderDetailMapView!!.map
        val child = mOrderDetailMapView!!.getChildAt(1)
        if (child != null && (child is ImageView || child is ZoomControls)) {
            child.visibility = View.INVISIBLE
        }
        mOrderDetailMapView!!.showScaleControl(false)
        mOrderDetailMapView!!.showZoomControls(false)
    }

    private fun getGeo(geo: String): MutableList<Array<String>> {
        val geos: MutableList<Array<String>> = mutableListOf()
        val temp3 = geo.substring(geo.indexOf("[[") + 1, geo.indexOf("]}"))
        val temp2 = temp3.replace("[", "")
        val temp = temp2.replace("]", "")
        val array = temp.split(',')
        for (i in 0 until array.size step 2) {
            geos.add(arrayOf(array[i], array[i + 1]))
        }
        return geos
    }
}
