package application.service;

import application.domain.Payment;
import application.domain.Template;
import application.port.AddressRepository;
import application.port.PaymentRepository;
import application.port.TemplateRepository;

import java.util.UUID;

public class PaymentServiceImpl implements PaymentService {
    private final AddressRepository addressRepository;
    private final TemplateRepository templateRepository;
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(
            AddressRepository addressRepository,
            TemplateRepository templateRepository,
            PaymentRepository paymentRepository
    ) {
        this.addressRepository = addressRepository;
        this.templateRepository = templateRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public boolean addNewPayment(long cardNumber, float paymentAmount, String templateName, String address) {
        UUID templateID = templateRepository.getTemplateIdByAddressAndTemplateName(address, templateName);  // It works!
        Payment newPayment = new Payment(templateID, cardNumber, paymentAmount);

        return paymentRepository.addPayment(newPayment);
    }
}
