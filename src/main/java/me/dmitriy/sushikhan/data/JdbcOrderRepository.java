package me.dmitriy.sushikhan.data;

import me.dmitriy.sushikhan.Ingredient;
import me.dmitriy.sushikhan.Sushi;
import me.dmitriy.sushikhan.SushiOrder;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    private final JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public SushiOrder save(SushiOrder order) {
        System.out.println("SAVE ORDER CALLED");
        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                        "INSERT INTO sushi_order "+
                            "(delivery_name, delivery_street, delivery_house, "+
                            "delivery_city, delivery_region, delivery_flat, "+
                            "delivery_entrance, cc_number, cc_expiration, cc_cvv, placed_at) "+
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                            Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                            Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                            Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                            Types.VARCHAR, Types.TIMESTAMP
                );

        pscf.setReturnGeneratedKeys(true);
        pscf.setGeneratedKeysColumnNames("sushi_order_id");

        order.setPlacedAt(new Date());

        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                order.getDeliveryName(),
                                order.getDeliveryStreet(),
                                order.getDeliveryHouse(),
                                order.getDeliveryCity(),
                                order.getDeliveryRegion(),
                                order.getDeliveryFlat(),
                                order.getDeliveryEntrance(),
                                order.getCcNumber(),
                                order.getCcExpiration(),
                                order.getCcCVV(),
                                order.getPlacedAt()
                        )
                );

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long orderId =  keyHolder.getKey().longValue();
        order.setId(orderId);

        List<Sushi> sushiList = order.getSushiList();
        int i = 0;
        for (Sushi sushi : sushiList) {
            saveSushi(orderId, i++, sushi);
        }
        return order;
    }

    private long saveSushi(long orderId, int orderKey, Sushi sushi) {
        sushi.setCreatedAt(new Date());
        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                        "INSERT INTO sushi "+
                            "(name, created_at, sushi_order_id, sushi_order_key) "+
                            "VALUES (?, ?, ?, ?)",
                        Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT, Types.BIGINT
                );
        pscf.setReturnGeneratedKeys(true);
        pscf.setGeneratedKeysColumnNames("sushi_id");

        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                sushi.getName(),
                                sushi.getCreatedAt(),
                                orderId,
                                orderKey
                        )
                );
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long sushiId = keyHolder.getKey().longValue();
        sushi.setId(sushiId);

        saveIngredientRefs(sushiId, sushi.getIngredients());

        return sushiId;
    }

    private void saveIngredientRefs(
            long sushiId, List<Ingredient> ingredients) {
        int key = 0;
        for (Ingredient ingredient: ingredients) {
            jdbcOperations.update(
                    "INSERT INTO ingredient_ref (ingredient_id, sushi_id, sushi_key) "+
                        "VALUES (?, ?, ?)",
                    ingredient.getId(), sushiId, key++
            );
        }
    }
}
