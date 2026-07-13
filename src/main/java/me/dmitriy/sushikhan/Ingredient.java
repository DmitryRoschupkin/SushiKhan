package me.dmitriy.sushikhan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class Ingredient{
    private String id;
    private String name;
    private Type type;


    public Ingredient(String id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
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
}
