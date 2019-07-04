package com.example.anuj.appointmentrequest.models;

/**
 * Created by Anuj on 20-07-2018.
 */

public class doctors {
    private int id;
    private String title;
    private String shortdesc;
    private String rating;
    private String price;
    private String image;
    private String longdesc;

    public doctors(int id, String title, String shortdesc, String rating, String price, String image,String longdesc) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.rating = rating;
        this.price = price;
        this.image = image;
        this.longdesc=longdesc;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public String getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }


    public String getImage() {
        return image;
    }
    public String getLongdesc() {
        return longdesc;
    }
}
