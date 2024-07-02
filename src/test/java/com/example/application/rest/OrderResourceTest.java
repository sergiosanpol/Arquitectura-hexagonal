package com.example.application.rest;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class OrderResourceTest {

    @Test
    public void testCreateOrderEndpoint() {
        Order order = new Order(LocalDateTime.now(), "PENDING");
        given()
          .contentType("application/json")
          .body(order)
          .when().post("/orders")
          .then()
             .statusCode(201)
                .body("status", is("PENDING"));
    }

    @Test
    public void testAddItemToOrderEndpoint() {
        Order order = new Order(LocalDateTime.now(), "PENDING");
        given()
          .contentType("application/json")
          .body(order)
          .when().post("/orders")
          .then()
             .statusCode(201);

        OrderItem item = new OrderItem("Product", 2, new BigDecimal("50.0"));
        given()
          .contentType("application/json")
          .body(item)
          .when().post("/orders/{id}/items", order.getId())
          .then()
             .statusCode(200);
    }

    @Test
    public void testUpdateOrderStatusEndpoint() {
        Order order = new Order(LocalDateTime.now(), "PENDING");
        given()
          .contentType("application/json")
          .body(order)
          .when().post("/orders")
          .then()
             .statusCode(201);

        given()
          .contentType("application/json").body("CONFIRMED")
          .when().put("/orders/{id}/status", order.getId())
          .then()
             .statusCode(200)
                .body("status", is("CONFIRMED"));
    }

}
