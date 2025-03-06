package service;

import model.Order;
import model.Payment;
import repository.OrderRepository;

import repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentDetails) {
        return null;
    }
    @Override
    public Payment setStatus(Payment payment, String status) {
        return null;
    }
    @Override
    public Payment getPayment(String paymentId) {
        return null;
    }
    @Override
    public List<Payment> getAllPayments() {
        return null;
    }
}