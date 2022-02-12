package application.port;

import application.domain.Payment;
import application.domain.Template;
import dal.SqlPaymentRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository {
    List<Template> getPaymentsByUser(UUID userID) throws SqlPaymentRepository.SQLPaymentRepositoryExcception;
    List<Template> getPaymentsByAddress(UUID addressID) throws SqlPaymentRepository.SQLPaymentRepositoryExcception;
    List<Template> getPaymentsByTemplate(UUID templateID) throws SqlPaymentRepository.SQLPaymentRepositoryExcception;

    boolean addPayment(Payment payment);
}
