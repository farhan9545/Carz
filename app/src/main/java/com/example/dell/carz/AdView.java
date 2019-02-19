package com.example.dell.carz;

import android.graphics.Bitmap;

import java.io.Serializable;

public class AdView implements Serializable {
    String vehicleName;
    String city;
    String mileage;
    String price;
    String descrip;
    String namee;
    String phonee;
    String datee;
    Bitmap pic;
    Double longitude;
    Double latitude;
    String mapName;
    String user;

    public AdView(String vehicleName, String city, String mileage, String price, String descrip, String namee, String phonee, String datee, Bitmap pic,Double longitude,Double latitude,String mapName,String user) {
        this.vehicleName = vehicleName;
        this.city = city;
        this.mileage = mileage;
        this.price = price;
        this.descrip = descrip;
        this.namee = namee;
        this.phonee = phonee;
        this.datee = datee;
        this.pic = pic;
        this.longitude=longitude;
        this.latitude=latitude;
        this.mapName=mapName;
        this.user=user;
    }
    public AdView()
    {}

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getNamee() {
        return namee;
    }

    public void setNamee(String namee) {
        this.namee = namee;
    }

    public String getPhonee() {
        return phonee;
    }

    public void setPhonee(String phonee) {
        this.phonee = phonee;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }
}
