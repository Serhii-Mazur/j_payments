package application.utils;

import application.domain.Address;
import application.domain.Payment;
import application.domain.Template;
import application.domain.User;
import application.port.AddressRepository;
import application.port.PaymentRepository;
import application.port.TemplateRepository;
import application.port.UserRepository;
import dal.*;

import java.util.List;
import java.util.logging.Logger;

public class ShowAllUtil {
    private final Logger logger;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final TemplateRepository templateRepository;
    private final PaymentRepository paymentRepository;

    public ShowAllUtil(
            Logger logger,
            UserRepository userRepository,
            AddressRepository addressRepository,
            TemplateRepository templateRepository,
            PaymentRepository paymentRepository) {
        this.logger = logger;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.templateRepository = templateRepository;
        this.paymentRepository = paymentRepository;
    }

    public void writeToLog() throws UtilException {
        try {
            List<User> userList = userRepository.getAllUsers();
            StringBuilder outputBlock = new StringBuilder();
            for (User user : userList) {
                outputBlock.append(String.format("%n| %-20s | %-30s | %-19s |", user.getFullName(), user.getEmail(), user.getPhoneNumber()));
            }
            logger.info(String.format("Users %n| %-20s | %-30s | %-19s |", "full_name", "email", "phone_number")
                    + outputBlock);
        } catch (SqlUserRepository.SQLUserRepositoryException e) {
            throw new UtilException("Can't get data from DB. ", e);
        }
        try {
            List<Address> addressList = addressRepository.getAllAddresses();
            StringBuilder outputBlock = new StringBuilder();
            for (Address address : addressList) {
                outputBlock.append(String.format("%n| %-36s | %-36s | %-30s |",
                        address.getAddressID(), address.getAddress(), address.getUserEmail()));
            }
            logger.info(String.format("Addresses %n| %-36s | %-36s | %-30s |", "id", "address", "user_email")
                    + outputBlock);
        } catch (SqlAddressRepository.SQLAddressRepositoryException e) {
            throw new UtilException("Can't get data from DB. ", e);
        }
        try {
            List<Template> templateList = templateRepository.getAllTemplates();
            StringBuilder outputBlock = new StringBuilder();
            for (Template template : templateList) {
                outputBlock.append(String.format("%n| %-36s | %-36s | %-20s | %-50s | %-30s |",
                        template.getTemplateID(), template.getAddressID(), template.getTemplateName(), template.getPaymentPurpose(), template.getIban()));
            }
            logger.info(String.format("Templates %n| %-36s | %-36s | %-20s | %-50s | %-30s |", "id", "address_id", "template_name", "payment_purpose", "iban")
                    + outputBlock);
        } catch (SqlTemplateRepository.SQLTemplateRepositoryException e) {
            throw new UtilException("Can't get data from DB. ", e);
        }
        try {
            List<Payment> paymentList = paymentRepository.getAllPayments();
            StringBuilder outputBlock = new StringBuilder();
            for (Payment payment : paymentList) {
                outputBlock.append(String.format("%n| %-36s | %-36s | %-20s | %-16s | %-8s | %-20s | %-20s |",
                        payment.getPaymentID(),
                        payment.getTemplateID(),
                        payment.getCardNumber(),
                        payment.getPaymentAmount(),
                        payment.getPaymentStatus().name(),
                        payment.getCreatedDateTime(),
                        payment.getEtlDateTime()));
            }
            logger.info(String.format("Payments %n| %-36s | %-36s | %-20s | %-16s | %-8s | %-20s | %-20s |",
                    "id", "template_id", "card_number", "payment_amount", "status", "created_date_time", "etl_date_time")
                    + outputBlock);
        } catch (SqlPaymentRepository.SQLPaymentRepositoryExcception e) {
            throw new UtilException("Can't get data from DB. ", e);
        }
    }

    public static class UtilException extends Exception {
        public UtilException() {
            super();
        }

        public UtilException(String message) {
            super(message);
        }

        public UtilException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
