package me.dmitriy.sushikhan.email;

import me.dmitriy.sushikhan.Sushi;

import java.util.ArrayList;
import java.util.List;

public class EmailOrder {
    private final String emal;
    private List<Sushi> sushiList = new ArrayList<>();

    public EmailOrder(String email) {
        this.emal = email;
    }

    public void addSushi(Sushi sushi) {
        sushiList.add(sushi);
    }

    public String getEmal() {
        return emal;
    }

    public List<Sushi> getSushiList() {
        return sushiList;
    }

    public void setSushiList(List<Sushi> sushiList) {
        this.sushiList = sushiList;
    }
}
