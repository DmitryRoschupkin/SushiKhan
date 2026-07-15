package me.dmitriy.sushikhan;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "sushi")
public class Sushi {

    @NotBlank
    @Size(min = 1, message = "Name must be at least 1 character long!")
    private String name;

    @NotNull
    @Size(min = 1, message = "You have to choose at least 1 ingredient!")
    @ManyToMany()
    @JoinTable(
            name = "ingredient_ref",
            joinColumns = @JoinColumn(name = "sushi_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;

    @Column(name = "sushi_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt = new Date();

    @ManyToOne
    @JoinColumn(name = "sushi_order_id", nullable = false)
    private SushiOrder sushiOrder;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
