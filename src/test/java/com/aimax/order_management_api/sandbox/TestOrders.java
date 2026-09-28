package com.aimax.order_management_api.sandbox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Made-up list of orders shared by all the tests.
 *
 * <pre>
 * id  customer  date        status     lines                                     total
 * 1   Ana       2026-09-01  PAID       2 Keyboard x 25.00 + 1 Mouse x 15.50       65.50
 * 2   Luis      2026-09-03  SHIPPED    1 Monitor x 180.00                        180.00
 * 3   Ana       2026-08-20  DELIVERED  3 Mouse x 15.50                            46.50
 * 4   Marta     2026-09-05  CANCELLED  10 Keyboard x 25.00                       250.00
 * 5   Luis      2026-09-10  PENDING    4 HDMI cable x 7.25 + 1 Mouse x 15.50      44.50
 * 6   Ana       2026-09-15  PAID       (no lines)                                  0.00
 * </pre>
 */
final class TestOrders {

    private TestOrders() {
    }

    static OrderLine line(String product, int quantity, String price) {
        return new OrderLine(product, quantity, new BigDecimal(price));
    }

    static Order order1() {
        return new Order(1, "Ana", LocalDate.of(2026, 9, 1), OrderStatus.PAID,
                List.of(line("Keyboard", 2, "25.00"), line("Mouse", 1, "15.50")));
    }

    static Order order6() {
        return new Order(6, "Ana", LocalDate.of(2026, 9, 15), OrderStatus.PAID, List.of());
    }

    static List<Order> all() {
        return List.of(
                order1(),
                new Order(2, "Luis", LocalDate.of(2026, 9, 3), OrderStatus.SHIPPED,
                        List.of(line("Monitor", 1, "180.00"))),
                new Order(3, "Ana", LocalDate.of(2026, 8, 20), OrderStatus.DELIVERED,
                        List.of(line("Mouse", 3, "15.50"))),
                new Order(4, "Marta", LocalDate.of(2026, 9, 5), OrderStatus.CANCELLED,
                        List.of(line("Keyboard", 10, "25.00"))),
                new Order(5, "Luis", LocalDate.of(2026, 9, 10), OrderStatus.PENDING,
                        List.of(line("HDMI cable", 4, "7.25"), line("Mouse", 1, "15.50"))),
                order6()
        );
    }
}
