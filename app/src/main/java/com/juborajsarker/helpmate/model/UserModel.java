package com.juborajsarker.helpmate.model;

public class UserModel {

    String userID;
    String email;
    String userName;
    String fullName;
    String phone;
    String city;
    String country;
    String password;
    String url;
    boolean isExpert;
    boolean accountIsActivate;
    String expertLIst;

    public UserModel() {

    }


    public UserModel(String userID, String email, String userName, String fullName, String phone, String city, String country,
                     String password, String url, boolean isExpert, boolean accountIsActivate, String expertLIst) {
        this.userID = userID;
        this.email = email;
        this.userName = userName;
        this.fullName = fullName;
        this.phone = phone;
        this.city = city;
        this.country = country;
        this.password = password;
        this.url = url;
        this.isExpert = isExpert;
        this.accountIsActivate = accountIsActivate;
        this.expertLIst = expertLIst;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isExpert() {
        return isExpert;
    }

    public void setExpert(boolean expert) {
        isExpert = expert;
    }

    public boolean isAccountIsActivate() {
        return accountIsActivate;
    }

    public void setAccountIsActivate(boolean accountIsActivate) {
        this.accountIsActivate = accountIsActivate;
    }

    public String getExpertLIst() {
        return expertLIst;
    }

    public void setExpertLIst(String expertLIst) {
        this.expertLIst = expertLIst;
    }
}
