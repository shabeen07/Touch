package com.devcods.touch.models;

import java.util.Date;

/**
 * Created by devCods on 20-11-2020 at 02:10 PM.
 **/
public class User {

    private String userId;
    private String userName;
    private String userMail;
    private String userPhone;
    private long createdAt;
    private int unseen_count;
    private String onlineStatus;
    private String countryCodeWithPlus;
    private String country;

    public User(String userId, String userName, String userMail, String userPhone, String onlineStatus, String countryCodeWithPlus, String country) {
        this.userId = userId;
        this.userName = userName;
        this.userMail = userMail;
        this.userPhone = userPhone;
        this.createdAt = new Date().getTime();
        this.unseen_count = 0;
        this.onlineStatus = onlineStatus;
        this.countryCodeWithPlus = countryCodeWithPlus;
        this.country = country;
    }

    public User() {
    }

    public int getUnseen_count() {
        return unseen_count;
    }

    public void setUnseen_count(int unseen_count) {
        this.unseen_count = unseen_count;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getCountryCodeWithPlus() {
        return countryCodeWithPlus;
    }

    public void setCountryCodeWithPlus(String countryCodeWithPlus) {
        this.countryCodeWithPlus = countryCodeWithPlus;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", createdAt=" + createdAt +
                ", unseen_count=" + unseen_count +
                ", onlineStatus='" + onlineStatus + '\'' +
                ", countryCodeWithPlus='" + countryCodeWithPlus + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
