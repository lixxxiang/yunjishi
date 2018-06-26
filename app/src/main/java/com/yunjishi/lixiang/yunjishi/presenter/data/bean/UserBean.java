package com.yunjishi.lixiang.yunjishi.presenter.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserBean {
    @Id
    private String userId;
    private String userName;
    private String userTel;
    private String userPassword;
    @Generated(hash = 1794157267)
    public UserBean(String userId, String userName, String userTel,
            String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userTel = userTel;
        this.userPassword = userPassword;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
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
    public String getUserTel() {
        return this.userTel;
    }
    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }
    public String getUserPassword() {
        return this.userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
