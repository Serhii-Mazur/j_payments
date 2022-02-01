package application.port;

import application.domain.Address;
import application.domain.Template;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface PaymentRepository {
    List<Template> getPaymentsByUser(UUID userID);
    List<Template> getPaymentsByAddress(UUID addressID);
    List<Template> getPaymentsByTemplate(UUID templateID);

    boolean addTemplate(@NotNull Template template);
}
