package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Product product1 = new Product(1L, "Harry Potter1", "Books", new BigDecimal("110.00"));
        Product product2 = new Product(2L, "Atomic habits", "Books", new BigDecimal("90.00"));
        Product product3 = new Product(3L, "Yogurt", "Children's products", new BigDecimal("15.00"));
        Product product4 = new Product(4L, "LEGO", "Toys", new BigDecimal("90.00"));
        Product product5 = new Product(5L, "Harry Potter2", "Books", new BigDecimal("100.00"));
        Product product6 = new Product(6L, "Rich dad poor dad", "Books", new BigDecimal("130.00"));
        Product product7 = new Product(7L, "Ice cream", "Children's products", new BigDecimal("20.00"));
        Product product8 = new Product(8L, "Ball", "Toys", new BigDecimal("80.00"));
        List<Product> listOfProducts = new ArrayList<>(Arrays.asList(product1, product2, product3, product4, product5, product6, product7, product8));


        Order order1 = new Order(1L, LocalDate.of(2021, 3, 14), LocalDate.of(2021, 4, 1), "delivered", Set.of(product1, product2));
        Order order2 = new Order(2L, LocalDate.of(2021, 3, 14), LocalDate.of(2021, 4, 1), "delivered", Set.of(product3));
        Order order3 = new Order(3L, LocalDate.of(2021, 4, 1), LocalDate.of(2023, 4, 1), "delivered", Set.of(product4));
        Order order4 = new Order(4L, LocalDate.of(2021, 2, 3), LocalDate.of(2021, 3, 15), "delivered", Set.of(product5));
        Order order5 = new Order(5L, LocalDate.of(2021, 2, 1), LocalDate.of(2021, 4, 3), "delivered", Set.of(product6));
        Order order6 = new Order(6L, LocalDate.of(2023, 4, 1), LocalDate.of(2024, 4, 1), "delivered", Set.of(product7));
        Order order7 = new Order(7L, LocalDate.of(2024, 4, 1), LocalDate.of(2025, 4, 1), "delivered", Set.of(product1, product5));
        Order order8 = new Order(8L, LocalDate.of(2021, 3, 1), LocalDate.of(2021, 3, 15), "delivered", Set.of(product8));
        Set<Order> setOfOrders = new HashSet<>(Arrays.asList(order1, order2, order3, order4, order5, order6, order7, order8));


        Customer customer1 = new Customer(1L, "Alex", 1L, Set.of(order1, order2));
        Customer customer2 = new Customer(2L, "Brad", 2L, Set.of(order3, order4));
        Customer customer3 = new Customer(3L, "Tim", 1L, Set.of(order5, order6));
        Customer customer4 = new Customer(4L, "Phil", 2L, Set.of(order7, order8));
        Customer customer5 = new Customer(5L, "Aaron", 1L, Set.of(order1, order8));
        List<Customer> listOfCustomers = new ArrayList<>(Arrays.asList(customer1, customer2, customer3, customer4, customer5));

        List<Product> expensiveBooks;
        List<Order> childrensProducts;
        BigDecimal toysDiscount;
        List<Product> levelTwoCustomer;
        List<Product> cheapProducts;
        List<Order> lastThreeOrders;
        List<Product> March15Orders;
        BigDecimal sumOfAll;

        List<BigDecimal> sizeOfMarchProducts;
        BigDecimal sumMarch;
        BigDecimal halfOfSumMarch;

        List<BigDecimal> booksPriceInfo;
        BigDecimal sum;
        BigDecimal max;
        BigDecimal min;
        BigDecimal avg;
        long count;

        Map<Long, Integer> orderInfo;
        Map<Customer,ArrayList<Order>> customerListMap;
        Map<Order,Double> orderDoubleMap;
        Map<String, List<String>> categoryList;
        Map<String, Product> expensiveProductByGroup;

        // 1. Получите список продуктов из категории "Books" с ценой более 100.
        expensiveBooks = listOfProducts.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase("Books"))
                .filter(product -> product.getPrice().compareTo(new BigDecimal("100")) > 0)
                .toList();

        //2. Получите список заказов с продуктами из категории "Children's products".
        childrensProducts = setOfOrders.stream()
                .filter(order -> order.getProducts().stream()
                        .anyMatch(product -> product.getCategory().equalsIgnoreCase("Children's products")))
                .toList();

        //3. Получите список продуктов из категории "Toys" и примените скидку 10% и получите сумму всех
        //продуктов
        toysDiscount = listOfProducts.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase("toys"))
                .map(product -> product.getPrice().multiply(new BigDecimal("0.9")))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //4. Получите список продуктов, заказанных клиентом второго уровня между 01-фев-2021 и 01-апр-2021.
        levelTwoCustomer = listOfCustomers.stream()
                .filter(customer -> customer.getLevel() == 2L)
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> !order.getOrderDate().isBefore(LocalDate.of(2021, 2, 1)) &&
                        !order.getOrderDate().isAfter(LocalDate.of(2021, 4, 1)))
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .toList();

        //5. Получите топ 2 самые дешевые продукты из категории "Books".
        cheapProducts = listOfProducts.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase("Books"))
                .sorted((p1, p2) -> p1.getPrice().compareTo(p2.getPrice()))
                .limit(2)
                .toList();

        //6. Получите 3 самых последних сделанных заказа.
        lastThreeOrders = setOfOrders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .toList();

        //7. Получите список заказов, сделанных 15-марта-2021, выведите id заказов в консоль и затем верните
        //список их продуктов
        March15Orders = setOfOrders.stream()
                .filter(order -> order.getDeliveryDate().isEqual(LocalDate.of(2021, 3, 15)))
                .peek(order -> System.out.println("Task 7: /// Order ID: " + order.getId()))
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.collectingAndThen(Collectors.toList(), products -> {
                    System.out.println(products);
                    return products;
                }));

        //8. Рассчитайте общую сумму всех заказов, сделанных в феврале 2021.
        sumOfAll = setOfOrders.stream()
                .filter(order -> order.getOrderDate().getMonthValue() == 2 && order.getOrderDate().getYear() == 2021)
                .flatMap(order -> order.getProducts().stream())
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //9. Рассчитайте средний платеж по заказам, сделанным 14-марта-2021.
        sizeOfMarchProducts = setOfOrders.stream()
                .filter(order -> order.getOrderDate().getDayOfMonth() == 3 && order.getOrderDate().getMonthValue() == 2 && order.getOrderDate().getYear() == 2021)
                .flatMap(order -> order.getProducts().stream())
                .map(Product::getPrice)
                .toList();

        sumMarch = sizeOfMarchProducts.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        halfOfSumMarch = sumMarch.divide(BigDecimal.valueOf(sizeOfMarchProducts.size()), 2, RoundingMode.HALF_UP);


        //10. Получите набор статистических данных (сумма, среднее, максимум, минимум, количество) для всех
        //продуктов категории "Книги".
        booksPriceInfo = listOfProducts.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase("Books"))
                .map(Product::getPrice)
                .toList();

        sum = booksPriceInfo.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        max = booksPriceInfo.stream()
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
        min = booksPriceInfo.stream()
                .min(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
        avg = booksPriceInfo.isEmpty()
                ? BigDecimal.ZERO
                : sum.divide(BigDecimal.valueOf(booksPriceInfo.size()));
        count = booksPriceInfo.size();

//11. Получите данные Map<Long, Integer> → key - id заказа, value - кол-во товаров в заказе
        orderInfo = setOfOrders.stream()
                .collect(Collectors.toMap(
                        Order::getId,
                        order ->order.getProducts().size()
                ));

        //12. Создайте Map<Customer, List<Order>> → key - покупатель, value - список его заказов
        customerListMap = listOfCustomers.stream()
                .collect(Collectors.toMap(
                      customer -> customer,
                        customer -> new ArrayList(customer.getOrders())
                ));

        //13. Создайте Map<Order, Double> → key - заказ, value - общая сумма продуктов заказа.
        orderDoubleMap = setOfOrders.stream()
                .collect(Collectors.toMap(
                        order -> order,
                        order -> order.getProducts().stream()
                                .map(Product::getPrice)
                                .mapToDouble(BigDecimal::doubleValue)
                                .sum()
                        ));

        //14. Получите Map<String, List<String>> → key - категория, value - список названий товаров в категории
        categoryList = listOfProducts.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.mapping(Product::getName, Collectors.toList())
                ));

        //15. Получите Map<String, Product> → самый дорогой продукт по каждой категории.
        expensiveProductByGroup = listOfProducts.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(Product::getPrice)),
                                Optional::get
                        )
                ));



        // 1. Получите список продуктов из категории "Books" с ценой более 100.
        System.out.println("1. Получите список продуктов из категории \"Books\" с ценой более 100.");
        System.out.println(expensiveBooks);

        System.out.println("======================================================================");
        // 2. Получите список заказов с продуктами из категории "Children's products"
        System.out.println("2. Получите список заказов с продуктами из категории \"Children's products\"");
        System.out.println(childrensProducts);

        System.out.println("======================================================================");
        //3. Получите список продуктов из категории "Toys" и примените скидку 10% и получите сумму всех
        //продуктов
        System.out.println("3. Получите список продуктов из категории \"Toys\" и примените скидку 10% и получите сумму всех\n" +
                "продуктов");
        System.out.println(toysDiscount);

        System.out.println("======================================================================");
        //4. Получите список продуктов, заказанных клиентом второго уровня между 01-фев-2021 и 01-апр-2021.
        System.out.println("4. Получите список продуктов, заказанных клиентом второго уровня между 01-фев-2021 и 01-апр-2021.\n");
        System.out.println(levelTwoCustomer);

        System.out.println("======================================================================");
        //5. Получите топ 2 самые дешевые продукты из категории "Books".
        System.out.println("5. Получите топ 2 самые дешевые продукты из категории \"Books\".\n");
        System.out.println(cheapProducts);

        System.out.println("======================================================================");
        //6. Получите 3 самых последних сделанных заказа.
        System.out.println("6. Получите 3 самых последних сделанных заказа.");
        System.out.println(lastThreeOrders);

        System.out.println("======================================================================");
        //7. Получите список заказов, сделанных 15-марта-2021, выведите id заказов в консоль и затем верните
        //список их продуктов.
        System.out.println("7. Получите список заказов, сделанных 15-марта-2021, выведите id заказов в консоль и затем верните\n" +
                "список их продуктов.\n  Order ID в самом начале");
        System.out.println(March15Orders);

        System.out.println("======================================================================");
        //8. Рассчитайте общую сумму всех заказов, сделанных в феврале 2021.
        System.out.println("8. Рассчитайте общую сумму всех заказов, сделанных в феврале 2021.");
        System.out.println(sumOfAll);


        System.out.println("======================================================================");
        //9. Рассчитайте средний платеж по заказам, сделанным 14-марта-2021.
        System.out.println("9. Рассчитайте средний платеж по заказам, сделанным 14-марта-2021.");
        System.out.println(halfOfSumMarch);

        System.out.println("======================================================================");
        //10. Получите набор статистических данных (сумма, среднее, максимум, минимум, количество) для всех
        //продуктов категории "Книги"
        System.out.println("10. Получите набор статистических данных (сумма, среднее, максимум, минимум, количество) для всех\n" +
                "продуктов категории \"Книги\"");
        System.out.println(sum + "\n" +
                avg + "\n" +
                max + "\n" +
                min + "\n" +
                count);

        System.out.println("======================================================================");
        //11. Получите данные Map<Long, Integer> → key - id заказа, value - кол-во товаров в заказе
        System.out.println("11. Получите данные Map<Long, Integer> → key - id заказа, value - кол-во товаров в заказе");
        System.out.println(orderInfo);

        System.out.println("======================================================================");
        //12. Создайте Map<Customer, List<Order>> → key - покупатель, value - список его заказов
        System.out.println("12. Создайте Map<Customer, List<Order>> → key - покупатель, value - список его заказов\n");
        System.out.println(customerListMap);

        System.out.println("======================================================================");
        //13. Создайте Map<Order, Double> → key - заказ, value - общая сумма продуктов заказа.
        System.out.println("13. Создайте Map<Order, Double> → key - заказ, value - общая сумма продуктов заказа.\n");
        System.out.println(orderDoubleMap);

        System.out.println("======================================================================");
        //14. Получите Map<String, List<String>> → key - категория, value - список названий товаров в категории
        System.out.println("14. Получите Map<String, List<String>> → key - категория, value - список названий товаров в категории\n");
        System.out.println(categoryList);

        System.out.println("======================================================================");
        //15. Получите Map<String, Product> → самый дорогой продукт по каждой категории.
        System.out.println("15. Получите Map<String, Product> → самый дорогой продукт по каждой категории.\n");
        System.out.println(expensiveProductByGroup);
    }
}