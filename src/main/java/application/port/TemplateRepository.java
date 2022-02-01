package application.port;

import application.domain.Template;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface TemplateRepository {
    List<Template> getTemplatesByUser(UUID userID);
    List<Template> getTemplatesByAddress(UUID addressID);

    boolean addTemplate(@NotNull Template template);
}
