package application.service;

import application.domain.Template;
import application.port.AddressRepository;
import application.port.TemplateRepository;

import java.util.UUID;

public class TemplateServiceImpl implements TemplateService {
    private final AddressRepository addressRepository;
    private final TemplateRepository templateRepository;

    public TemplateServiceImpl(AddressRepository addressRepository,
                               TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public void addNewTemplate(String templateName, String address, String paymentPurpose, String iban) {
        UUID addressID = addressRepository.getAddressID(address);
        Template newTemplate = new Template(addressID, templateName, paymentPurpose, iban);
        templateRepository.addTemplate(newTemplate);
    }
}