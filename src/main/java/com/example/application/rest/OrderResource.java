package com.example.application.rest;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.service.OrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @POST
    public Response createOrder(Order order) {
        orderService.createOrder(order);
        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    @POST
    @Path("/{orderId}/items")
    public Response updateOrderStatus(@PathParam("orderId") Long orderId, OrderItem item) {
        orderService.addItemToOrder(orderId,item);
        return Response.ok().build();
    }

    @PUT
    @Path("/{orderId}/status")
    public Response updateOrderStatus(@PathParam("orderId") Long orderId, String status) {
        orderService.updateOrderStatus(orderId, status);
        return Response.ok().build();
    }

    @GET
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GET
    @Path("/{orderId}")
    public Order getOrderById(@PathParam("orderId") Long orderId) {
        return orderService.findOrderById(orderId);
    }

}
