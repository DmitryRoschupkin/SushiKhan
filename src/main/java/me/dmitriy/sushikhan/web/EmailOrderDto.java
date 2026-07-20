package me.dmitriy.sushikhan.web;

import lombok.Builder;
import lombok.Data;
import me.dmitriy.sushikhan.Sushi;

import java.util.List;

@Data
@Builder
public class EmailOrderDto {
    private String customerEmail;
    private List<Sushi> sushiList;
    private String status;
}
