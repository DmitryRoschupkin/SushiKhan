package me.dmitriy.sushikhan.data;

import me.dmitriy.sushikhan.SushiOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<SushiOrder, Long> {
    @Query("SELECT DISTINCT o FROM SushiOrder o LEFT JOIN FETCH o.sushiList")
    List<SushiOrder> findAllWithSushis();

    @EntityGraph(attributePaths = {"sushiList", "sushiList.ingredients", "user"})
    List<SushiOrder> findAll();
}
