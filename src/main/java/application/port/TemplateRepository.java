package application.port;

import application.domain.Template;
import dal.SqlTemplateRepository;

import java.util.List;
import java.util.UUID;

public interface TemplateRepository {
    List<Template> getAllTemplates() throws SqlTemplateRepository.SQLTemplateRepositoryException;

    List<Template> getTemplatesByUser(UUID userID);
    List<Template> getTemplatesByAddress(UUID addressID);

    void addTemplate(Template template) throws SqlTemplateRepository.SQLTemplateRepositoryException;
    UUID getTemplateIdByAddressAndTemplateName(String address, String templateName) throws SqlTemplateRepository.SQLTemplateRepositoryException;
}
