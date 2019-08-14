package com.xw.bmobdemo;

import cn.bmob.v3.BmobObject;

public class UserData extends BmobObject {

    private String userName;
    private String passWord;

    public UserData() {
    }

    public UserData(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
