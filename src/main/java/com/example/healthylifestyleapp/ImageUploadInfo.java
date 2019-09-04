package com.example.healthylifestyleapp;

public class ImageUploadInfo {
    public String imageName;

    public String imageURL;
    public String Email;
    public String Mobile;


    public ImageUploadInfo() {

    }

    public ImageUploadInfo(String name, String email, String mobile, String url) {

        this.imageName = name;
        this.imageURL= url;
        this.Email=email;
        this.Mobile=mobile;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageURL() {
        return imageURL;
    }
}
