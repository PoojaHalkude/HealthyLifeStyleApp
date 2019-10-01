package com.example.healthylifestyleapp;

public class UserUploadInfo {
    public String UserName;

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getMyemail() {
        return Myemail;
    }

    public void setMyemail(String email) {
        Myemail = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String imageURL;
    public String Myemail;
    public String Mobile;


    public UserUploadInfo() {

    }

    public UserUploadInfo(String name, String Myemail, String mobile, String url) {

        this.UserName = name;
        this.imageURL= url;
        this.Myemail=Myemail;
        this.Mobile=mobile;
    }

    public String getUserName() {
        return UserName;
    }

    public String getImageURL() {
        return imageURL;
    }
}
