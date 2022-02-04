package application.service;

import application.port.TemplateRepository;

public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;

    public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public boolean addNewTemplate(String templateName, String address, String paymentPurpose, String iban) {
        return false;
    }
}
