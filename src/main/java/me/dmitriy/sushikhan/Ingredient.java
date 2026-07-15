package me.dmitriy.sushikhan;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@Data
@Entity
@Table(name = "ingredient")
public class Ingredient{
    @Id
    @Column(name = "ingredient_id")
    private String id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;


    public Ingredient(String id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Ingredient() {

    }

    public Type getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public enum Type{
        BASE, SEAFOOD, MEAT, VEGETABLE, CHEESE, SAUCE, TOPPING, SEAWEED, SEASONING
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
