package application.service;

public interface PaymentService {
    boolean addNewPayment(long cardNumber, float paymentAmount, String templateName, String userEmail);
}
