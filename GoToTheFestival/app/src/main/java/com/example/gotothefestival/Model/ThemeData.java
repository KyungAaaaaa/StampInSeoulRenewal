package com.example.gotothefestival.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ThemeData implements Parcelable {
     private String title;
    private String firstImage;
    private String addr1;
    private String tel;
    private String overView;
    private String picture;
    private String content_pola;
    private String content_title;
    private String contents;
    private int complete;
    private double mapX;
    private double mapY;
    private boolean hart = false;

    public ThemeData(double mapX, double mapY) {
        this.mapX = mapX;
        this.mapY = mapY;
    }

    public ThemeData(String title) {
        this.title = title;
    }

    public ThemeData(String title, String addr, double mapX, double mapY, String firstImage) {
        this.title = title;
        this.addr1 = addr;
        this.mapX = mapX;
        this.mapY = mapY;
        this.firstImage = firstImage;
    }

    public ThemeData(String title, String firstImage, String addr1, String picture, String content_pola, String content_title, String contents) {
        this.title = title;
        this.firstImage = firstImage;
        this.addr1 = addr1;
        this.picture = picture;
        this.content_pola = content_pola;
        this.content_title = content_title;
        this.contents = contents;
    }

    public ThemeData(String title, String picture, String content_pola, String content_title, String contents, int complete) {

        this.title = title;
        this.picture = picture;
        this.content_pola = content_pola;
        this.content_title = content_title;
        this.contents = contents;
        this.complete = complete;
    }

    public ThemeData(String title, String firstImage, String picture, String content_pola, String content_title, String contents, int complete) {
        this.title = title;
        this.firstImage = firstImage;
        this.picture = picture;
        this.content_pola = content_pola;
        this.content_title = content_title;
        this.contents = contents;
        this.complete = complete;
    }

    public ThemeData() {

    }

    public ThemeData(String title, String addr, double mapX, double mapY) {
        this.title = title;
        this.addr1 = addr;
        this.mapX = mapX;
        this.mapY = mapY;
    }


    protected ThemeData(Parcel in) {
        title = in.readString();
        firstImage = in.readString();
        addr1 = in.readString();
        tel = in.readString();
        overView = in.readString();
        picture = in.readString();
        content_pola = in.readString();
        content_title = in.readString();
        contents = in.readString();
        complete = in.readInt();
        mapX = in.readDouble();
        mapY = in.readDouble();
        hart = in.readByte() != 0;
        if (in.readByte() == 0) {
            contentsID = null;
        } else {
            contentsID = in.readInt();
        }
    }

    public static final Creator<ThemeData> CREATOR = new Creator<ThemeData>() {
        @Override
        public ThemeData createFromParcel(Parcel in) {
            return new ThemeData(in);
        }

        @Override
        public ThemeData[] newArray(int size) {
            return new ThemeData[size];
        }
    };

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContent_pola() {
        return content_pola;
    }

    public void setContent_pola(String content_pola) {

        this.content_pola = content_pola;

    }

    public String getContent_title() {

        return content_title;

    }

    public void setContent_title(String content_title) {

        this.content_title = content_title;

    }


    public String getContents() {

        return contents;

    }


    public void setContents(String contents) {

        this.contents = contents;

    }


    public int getComplete() {

        return complete;

    }


    public void setComplete(int complete) {

        this.complete = complete;

    }


    public boolean isHart() {

        return hart;

    }


    public void setHart(boolean hart) {

        this.hart = hart;

    }


    private Integer contentsID;


    public Integer getContentsID() {

        return contentsID;

    }


    public void setContentsID(Integer contentsID) {

        this.contentsID = contentsID;

    }


    public String getTel() {

        return tel;

    }


    public void setTel(String tel) {

        this.tel = tel;

    }


    public double getMapX() {

        return mapX;

    }


    public void setMapX(double mapX) {

        this.mapX = mapX;

    }


    public double getMapY() {

        return mapY;

    }


    public void setMapY(double mapY) {

        this.mapY = mapY;

    }


    public String getAddr() {

        return addr1;

    }


    public void setAddr(String addr) {

        this.addr1 = addr;

    }


    public String getOverView() {

        return overView;

    }


    public void setOverView(String overView) {

        this.overView = overView;

    }


    public String getTitle() {

        return title;

    }


    public void setTitle(String title) {

        this.title = title;

    }


    public String getFirstImage() {

        return firstImage;

    }

    public void setFirstImage(String firstImage) {

        this.firstImage = firstImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(firstImage);
        parcel.writeString(addr1);
        parcel.writeString(tel);
        parcel.writeString(overView);
        parcel.writeString(picture);
        parcel.writeString(content_pola);
        parcel.writeString(content_title);
        parcel.writeString(contents);
        parcel.writeInt(complete);
        parcel.writeDouble(mapX);
        parcel.writeDouble(mapY);
        parcel.writeByte((byte) (hart ? 1 : 0));
        if (contentsID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(contentsID);
        }
    }
}