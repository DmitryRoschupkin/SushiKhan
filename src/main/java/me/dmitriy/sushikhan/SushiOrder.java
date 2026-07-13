package me.dmitriy.sushikhan;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SushiOrder {
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryHouse;
    private String deliveryCity;
    private String deliveryRegion;
    private String deliveryFlat;
    private String deliveryEntrance;
    private String deliveryFloor;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    private List<Sushi> sushiList = new ArrayList<>();

    public List<Sushi> getSushiList() {
        return sushiList;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryStreet() {
        return deliveryStreet;
    }

    public void setDeliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;
    }

    public String getDeliveryHouse() {
        return deliveryHouse;
    }

    public void setDeliveryHouse(String deliveryHouse) {
        this.deliveryHouse = deliveryHouse;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryRegion() {
        return deliveryRegion;
    }

    public void setDeliveryRegion(String deliveryRegion) {
        this.deliveryRegion = deliveryRegion;
    }

    public String getDeliveryFlat() {
        return deliveryFlat;
    }

    public void setDeliveryFlat(String deliveryFlat) {
        this.deliveryFlat = deliveryFlat;
    }

    public String getDeliveryEntrance() {
        return deliveryEntrance;
    }

    public void setDeliveryEntrance(String deliveryEntrance) {
        this.deliveryEntrance = deliveryEntrance;
    }

    public String getDeliveryFloor() {
        return deliveryFloor;
    }

    public void setDeliveryFloor(String deliveryFloor) {
        this.deliveryFloor = deliveryFloor;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCcExpiration() {
        return ccExpiration;
    }

    public void setCcExpiration(String ccExpiration) {
        this.ccExpiration = ccExpiration;
    }

    public String getCcCVV() {
        return ccCVV;
    }

    public void setCcCVV(String ccCVV) {
        this.ccCVV = ccCVV;
    }

    public void setSushiList(List<Sushi> sushiList) {
        this.sushiList = sushiList;
    }

    public void addSushi(Sushi sushi) {
        this.sushiList.add(sushi);
    }
}
