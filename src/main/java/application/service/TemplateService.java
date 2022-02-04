package application.service;

public interface TemplateService {
    boolean addNewTemplate(String templateName, String address, String paymentPurpose, String iban);
}
