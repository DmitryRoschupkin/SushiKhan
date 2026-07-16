package me.dmitriy.sushikhan;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "sushi_order")
public class SushiOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "sushi_order_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date placedAt;

    @PrePersist
    protected void onCreate() {
        placedAt = new Date();
    }

    @NotBlank(message = "Required!")
    private String deliveryName;

    @NotBlank(message = "Required!")
    private String deliveryStreet;

    @NotBlank(message = "Required!")
    private String deliveryHouse;

    @NotBlank(message = "Required!")
    private String deliveryCity;

    @NotBlank(message = "Required!")
    private String deliveryRegion;

    @NotBlank(message = "Required!")
    private String deliveryFlat;

    @NotBlank(message = "Required!")
    private String deliveryEntrance;

    @NotBlank(message = "Required!")
    private String deliveryFloor;

    @CreditCardNumber(message = "Invalid card number!")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
            message = "Must be formatted (MM/YY)!")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    @Column(name = "cc_cvv")
    private String ccCVV;

    @OneToMany(cascade =  CascadeType.ALL, mappedBy = "sushiOrder")
    @OrderBy("id ASC")
    private List<Sushi> sushiList = new ArrayList<>();

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
        sushi.setSushiOrder(this);
    }
}
