package application.service;

import dal.SqlPaymentRepository;
import dal.SqlTemplateRepository;

public interface PaymentService {
    void addNewPayment(long cardNumber, float paymentAmount, String templateName, String address) throws SqlTemplateRepository.SQLTemplateRepositoryException, SqlPaymentRepository.SQLPaymentRepositoryExcception;
}
