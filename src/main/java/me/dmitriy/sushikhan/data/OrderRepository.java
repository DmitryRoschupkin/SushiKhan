package me.dmitriy.sushikhan.data;

import me.dmitriy.sushikhan.SushiOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<SushiOrder, Long> {

}
