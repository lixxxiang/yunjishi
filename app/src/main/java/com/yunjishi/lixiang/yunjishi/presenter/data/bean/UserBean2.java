package com.yunjishi.lixiang.yunjishi.presenter.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserBean2 {
    @Id
    private String userId;
    private String userName;
    private String tel;
    @Generated(hash = 1715935067)
    public UserBean2(String userId, String userName, String tel) {
        this.userId = userId;
        this.userName = userName;
        this.tel = tel;
    }
    @Generated(hash = 928079600)
    public UserBean2() {
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getTel() {
        return this.tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
}
