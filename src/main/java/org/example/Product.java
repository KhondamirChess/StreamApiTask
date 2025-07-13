package org.example;

import java.math.BigDecimal;

public class Product {
    private final Long id;
    private final String name;
    private final String category;
    private final BigDecimal price;

    public Product(Long id, String name, String category, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return "    \n{ \n" +
                "       id: " + id + "; " +
                "name: " + name + "; " +
                "category: " + category + "; " +
                "price: " + price +
                "}";

    }
}
