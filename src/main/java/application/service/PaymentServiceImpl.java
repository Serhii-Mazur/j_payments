package application.service;

import application.port.PaymentRepository;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public boolean addNewPayment(long cardNumber, float paymentAmount, String templateName, String userEmail) {
        return false;
    }
}
