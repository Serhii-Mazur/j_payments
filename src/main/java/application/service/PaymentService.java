package application.service;

public interface PaymentService {
    void addNewPayment(long cardNumber, float paymentAmount, String templateName, String address);
}
