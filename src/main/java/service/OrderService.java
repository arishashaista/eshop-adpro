package service;

import model.Order;
import java.util.List;
import java.util.NoSuchElementException;

import repository.OrderRepository;

public interface OrderService {

    public Order createOrder(Order order);

    public Order updateStatus(String orderId, String status);

    public Order findById(String orderId);

    public List<Order> findAllByAuthor(String author);
}