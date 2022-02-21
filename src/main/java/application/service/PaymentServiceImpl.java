package application.service;

import application.domain.Payment;
import application.port.PaymentRepository;
import application.port.TemplateRepository;
import dal.SqlPaymentRepository;
import dal.SqlTemplateRepository;

import java.util.UUID;

public class PaymentServiceImpl implements PaymentService {
    private final TemplateRepository templateRepository;
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(
            TemplateRepository templateRepository,
            PaymentRepository paymentRepository
    ) {
        this.templateRepository = templateRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void addNewPayment(long cardNumber, float paymentAmount, String templateName, String address)
            throws
            SqlTemplateRepository.SQLTemplateRepositoryException,
            SqlPaymentRepository.SQLPaymentRepositoryExcception {
        UUID templateID = templateRepository.getTemplateIdByAddressAndTemplateName(address, templateName);
        Payment newPayment = new Payment(templateID, cardNumber, paymentAmount);

        paymentRepository.addPayment(newPayment);
    }
}
