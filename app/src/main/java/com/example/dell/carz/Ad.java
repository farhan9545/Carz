package com.example.dell.carz;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ad")
public class Ad {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo
    String vehicleName;
    @ColumnInfo
    String city;
    @ColumnInfo
    String mileage;
    @ColumnInfo
    String price;
    @ColumnInfo
    String descrip;
    @ColumnInfo
    String name;
    @ColumnInfo
    String phone;
    @ColumnInfo
    String date;
    @ColumnInfo
    String image;
    @ColumnInfo
    String user;
    @ColumnInfo
    Double longitude;
    @ColumnInfo
    Double latitude;
    @ColumnInfo
    String mapName;


    public Ad()
    {

    }


    public Ad(String vehicleName, String city, String mileage, String price, String descrip, String name, String phone, String date, String image, String user, double longitude,double latitude,String mapName) {
        this.vehicleName = vehicleName;
        this.city = city;
        this.mileage = mileage;
        this.price = price;
        this.descrip = descrip;
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.image = image;
        this.user=user;
        this.latitude=latitude;
        this.longitude=longitude;
        this.mapName=mapName;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
