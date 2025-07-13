package org.example;

import java.util.Set;

public class Customer {
    private final Long id;
    private final String name;
    private final Long level;
    private final Set<Order> orders;

    public Customer(Long id, String name, Long level, Set<Order> orders) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.orders = orders;
    }

    @Override
    public String toString(){
        return "Customer{ " + "\n" +
                "id: " + id + "\n" +
                "name: " + name + "\n" +
                "level: "+ level + "\n" +
                "orders: " +orders +  "\n"+
                "}";
    }
}
