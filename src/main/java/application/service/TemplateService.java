package application.service;

import dal.SqlAddressRepository;
import dal.SqlTemplateRepository;

public interface TemplateService {
    void addNewTemplate(String templateName, String address, String paymentPurpose, String iban) throws SqlAddressRepository.SQLAddressRepositoryException, SqlTemplateRepository.SQLTemplateRepositoryException;
}
