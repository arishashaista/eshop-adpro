package repository;

import model.Order;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;

@Repository
public class OrderRepository {

    private List<Order> orderData = new ArrayList<>();

    public Order save(Order order) {
        return null;
    }

    public Order findById(String id) {
        return null;
    }

    public List<Order> findAllByAuthor(String author) {
        return null;
    }
}
