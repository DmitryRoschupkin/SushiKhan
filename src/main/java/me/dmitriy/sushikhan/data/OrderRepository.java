package me.dmitriy.sushikhan.data;

import me.dmitriy.sushikhan.SushiOrder;

public interface OrderRepository {
    SushiOrder save(SushiOrder order);
}
