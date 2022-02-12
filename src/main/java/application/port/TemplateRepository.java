package application.port;

import application.domain.Template;

import java.util.List;
import java.util.UUID;

public interface TemplateRepository {
    List<Template> getTemplatesByUser(UUID userID);
    List<Template> getTemplatesByAddress(UUID addressID);

    boolean addTemplate(Template template);
    UUID getTemplateIdByAddressAndTemplateName(String address, String templateName);
}
