package com.android.lixiang.base.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by lixiang on 08/02/2018.
 */

@Entity(nameInDb = "user_profile")
public class UserProfile {
    @Id
    private String userId = null;
    private String username = null;
    private String level = null;
    private String label = null;
    private String userTelephone = null;
    private String userPassword = null;
    private String imagePath = null;
    private String gmtCreated = null;

    @Generated(hash = 1323035424)
    public UserProfile(String userId, String username, String level, String label,
                       String userTelephone, String userPassword, String imagePath,
                       String gmtCreated) {
        this.userId = userId;
        this.username = username;
        this.level = level;
        this.label = label;
        this.userTelephone = userTelephone;
        this.userPassword = userPassword;
        this.imagePath = imagePath;
        this.gmtCreated = gmtCreated;
    }
    @Generated(hash = 968487393)
    public UserProfile() {
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getLevel() {
        return this.level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getLabel() {
        return this.label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getUserTelephone() {
        return this.userTelephone;
    }
    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone;
    }
    public String getUserPassword() {
        return this.userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getImagePath() {
        return this.imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getGmtCreated() {
        return this.gmtCreated;
    }
    public void setGmtCreated(String gmtCreated) {
        this.gmtCreated = gmtCreated;
    }
}
