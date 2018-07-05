package com.yunjishi.lixiang.yunjishi.presenter.database

import android.content.Context
import com.yunjishi.lixiang.yunjishi.presenter.data.bean.UserBean2
import org.greenrobot.greendao.database.Database

class DataBaseManager {
    var mDaoSession: DaoSession? = null
    var userBean = UserBean2()

    fun initDao(context: Context): DaoSession? {
        var openHelper: DaoMaster.DevOpenHelper = DaoMaster.DevOpenHelper(context, "USER")
        var db: Database = openHelper.writableDb
        var daoMaster: DaoMaster = DaoMaster(db)
        mDaoSession = daoMaster.newSession()
        return mDaoSession
    }
}