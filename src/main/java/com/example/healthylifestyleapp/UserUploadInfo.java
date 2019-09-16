package com.example.healthylifestyleapp;

public class UserUploadInfo {
    public String UserName;

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String imageURL;
    public String Email;
    public String Mobile;


    public UserUploadInfo() {

    }

    public UserUploadInfo(String name, String email, String mobile, String url) {

        this.UserName = name;
        this.imageURL= url;
        this.Email=email;
        this.Mobile=mobile;
    }

    public String getUserName() {
        return UserName;
    }

    public String getImageURL() {
        return imageURL;
    }
}
