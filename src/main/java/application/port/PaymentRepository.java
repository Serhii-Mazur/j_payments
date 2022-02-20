package application.port;

import application.constants.PaymentStatus;
import application.domain.Payment;
import dal.SqlPaymentRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository {
    List<Payment> getAllPayments() throws SqlPaymentRepository.SQLPaymentRepositoryExcception;

    List<Payment> getPaymentsByUser(UUID userID) throws SqlPaymentRepository.SQLPaymentRepositoryExcception;
    List<Payment> getPaymentsByAddress(UUID addressID) throws SqlPaymentRepository.SQLPaymentRepositoryExcception;
    List<Payment> getPaymentsByTemplate(UUID templateID) throws SqlPaymentRepository.SQLPaymentRepositoryExcception;
    List<Payment> getPaymentsByStatus(PaymentStatus paymentStatus) throws SqlPaymentRepository.SQLPaymentRepositoryExcception;

    void addPayment(Payment payment) throws SqlPaymentRepository.SQLPaymentRepositoryExcception;
    void updatePaymentStatus(UUID paymentID, PaymentStatus newStatus) throws SqlPaymentRepository.SQLPaymentRepositoryExcception;
}
