package org.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Customer> listOfCustomers = new ArrayList<>();
        List<Product> listOfProducts = new ArrayList<>();
        Set<Order> setOfOrders = new HashSet<>();

        Product product1 = new Product(1L, "Harry Potter1", "Books", new BigDecimal("110.00"));
        Product product2 = new Product(2L, "Atomic habits", "Books", new BigDecimal("90.00"));
        Product product3 = new Product(3L, "Yogurt", "Children's products", new BigDecimal("15.00"));
        Product product4 = new Product(4L, "LEGO", "Toys", new BigDecimal("80.00"));
        Product product5 = new Product(1L, "Harry Potter2", "Books", new BigDecimal("110.00"));
        Product product6 = new Product(2L, "Rich dad poor dad", "Books", new BigDecimal("130.00"));
        Product product7 = new Product(3L, "Ice cream", "Children's products", new BigDecimal("15.00"));
        Product product8 = new Product(4L, "Ball", "Toys", new BigDecimal("80.00"));
        listOfProducts.add(product1);
        listOfProducts.add(product2);
        listOfProducts.add(product3);
        listOfProducts.add(product4);
        listOfProducts.add(product5);
        listOfProducts.add(product6);
        listOfProducts.add(product7);
        listOfProducts.add(product8);

        Order order1 = new Order(1L, LocalDate.of(2024, 4, 1), LocalDate.of(2025, 4, 1), "delivered", Set.of(product1, product2));
        Order order2 = new Order(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2025, 4, 1), "delivered", Set.of(product3));
        Order order3 = new Order(1L, LocalDate.of(2024, 4, 1), LocalDate.of(2025, 4, 1), "delivered", Set.of(product4));
        Order order4 = new Order(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2025, 4, 1), "delivered", Set.of(product5));
        Order order5 = new Order(1L, LocalDate.of(2024, 4, 1), LocalDate.of(2025, 4, 1), "delivered", Set.of(product6));
        Order order6 = new Order(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2025, 4, 1), "delivered", Set.of(product7));
        Order order7 = new Order(1L, LocalDate.of(2024, 4, 1), LocalDate.of(2025, 4, 1), "delivered", Set.of(product1, product5));
        Order order8 = new Order(2L, LocalDate.of(2024, 4, 1), LocalDate.of(2025, 4, 1), "delivered", Set.of(product8));
        setOfOrders.add(order1);
        setOfOrders.add(order2);
        setOfOrders.add(order3);
        setOfOrders.add(order4);
        setOfOrders.add(order5);
        setOfOrders.add(order6);
        setOfOrders.add(order7);
        setOfOrders.add(order8);


        Customer customer1 = new Customer(1L, "Alex", 1L, Set.of(order1,order2));
        Customer customer2 = new Customer(2L, "Alex", 1L, Set.of(order3,order4));
        Customer customer3 = new Customer(3L, "Alex", 1L, Set.of(order5,order6));
        Customer customer4 = new Customer(4L, "Alex", 1L, Set.of(order7,order8));
        Customer customer5 = new Customer(5L, "Alex", 1L, Set.of(order1,order8));

        listOfCustomers.add(customer1);
        listOfCustomers.add(customer2);
        listOfCustomers.add(customer3);
        listOfCustomers.add(customer4);
        listOfCustomers.add(customer5);

        List<Product> productStream = listOfProducts.stream()
            .filter(product -> product.getCategory().equalsIgnoreCase("Books"))
            .filter(product-> product.getPrice().compareTo(new BigDecimal("100")) > 0)
            .toList();

        productStream.forEach(System.out::println);
        Stream<Object> combinedStream = Stream.of(
                listOfProducts.stream()
                        .filter(product -> product.getCategory().equalsIgnoreCase("Books"))
                        .filter(product-> product.getPrice().compareTo(new BigDecimal("100")) > 0)
                        .collect(Collectors.toList()),

                setOfOrders.stream()
                        .filter(order -> order.getProducts().stream()
                                .anyMatch(product -> product.getCategory().equalsIgnoreCase("Children's products")))
                        .toList()
                ,
                listOfCustomers.stream()

        ); // Распаковка внутренних стримов

        combinedStream.forEach(System.out::println);
    }
}