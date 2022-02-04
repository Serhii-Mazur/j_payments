package dao;

import application.domain.Template;
import application.port.TemplateRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

public class SqlTemplateRepository implements TemplateRepository {
    private final Connection dbConnection;

    public SqlTemplateRepository(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Template> getTemplatesByUser(UUID userID) {
        return null;
    }

    @Override
    public List<Template> getTemplatesByAddress(UUID addressID) {
        return null;
    }

    @Override
    public boolean addTemplate(Template template) {
        String id = template.getTemplateID().toString();
        String template_name = template.getTemplateName();
        String address_id = template.getAddressID().toString();
        String payment_purpose = template.getPaymentPurpose();
        String iban = template.getIban();

        String ADD_TEMPLATE_QUERY = String.format("INSERT INTO mono.templates (id, template_name, address_id, payment_purpose, iban) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s');", id, template_name, address_id, payment_purpose, iban);

        boolean result;

        try (
                Statement stmt = dbConnection.createStatement()
        ) {
            stmt.executeUpdate(ADD_TEMPLATE_QUERY);
            result = true;

        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}
