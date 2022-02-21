package dal;

import application.domain.Template;
import application.port.TemplateRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class SqlTemplateRepository implements TemplateRepository {
    private final Logger logger;
    private final Connection dbConnection;

    public SqlTemplateRepository(Logger logger, Connection dbConnection) {
        this.logger = logger;
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Template> getAllTemplates() throws SQLTemplateRepositoryException {
        List<Template> result = new ArrayList<>();
        String GET_ALL_TEMPLATES_QUERY = "SELECT * FROM mono.templates";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(GET_ALL_TEMPLATES_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UUID templateID = resultSet.getObject("id", java.util.UUID.class);
                UUID addressID = resultSet.getObject("address_id", java.util.UUID.class);
                String templateName = resultSet.getString("template_name");
                String paymentPurpose = resultSet.getString("payment_purpose");
                String iban = resultSet.getString("iban");
                Template template = new Template(templateID, addressID, templateName, paymentPurpose, iban);
                result.add(template);
            }
        } catch (SQLException e) {
            throw new SQLTemplateRepositoryException("Can't execute: ", e);
        }
        return result;
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
    public UUID getTemplateIdByAddressAndTemplateName(String address, String templateName) throws SQLTemplateRepositoryException {
        String GET_TEMPLATE_ID_QUERY =
                "SELECT templ.id FROM (" +
                        "SELECT * FROM mono.addresses WHERE address = ?) AS addr " +
                        "LEFT JOIN (" +
                        "SELECT * FROM mono.templates t WHERE template_name = ?) AS templ " +
                        "ON addr.id = templ.address_id";

        ResultSet resultSet;
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(GET_TEMPLATE_ID_QUERY,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
            int pos = 0;
            preparedStatement.setString(++pos, address);
            preparedStatement.setString(++pos, templateName);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getObject("id", java.util.UUID.class);
        } catch (SQLException e) {
            throw new SQLTemplateRepositoryException("Can't execute. ", e);
        }
    }

    @Override
    public void addTemplate(Template template) throws SQLTemplateRepositoryException {
        long start = System.nanoTime();
        UUID id = template.getTemplateID();
        UUID addressID = template.getAddressID();
        String templateName = template.getTemplateName();
        String paymentPurpose = template.getPaymentPurpose();
        String iban = template.getIban();

        String ADD_TEMPLATE_QUERY =
                "INSERT INTO mono.templates (id, template_name, address_id, payment_purpose, iban) VALUES (?, ?, ?, ?, ?)";

        try (
                PreparedStatement preparedStatement = dbConnection.prepareStatement(ADD_TEMPLATE_QUERY)
        ) {
            int pos = 0;
            preparedStatement.setObject(++pos, id);
            preparedStatement.setString(++pos, templateName);
            preparedStatement.setObject(++pos, addressID);
            preparedStatement.setString(++pos, paymentPurpose);
            preparedStatement.setString(++pos, iban);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new SQLTemplateRepositoryException("Can't insert into mono.templates. ", e);
        }
        long end = System.nanoTime();
        String report = String.format("Template added:%n" +
                        "Template ID    : %s%n" +
                        "Template name  : %s%n" +
                        "Address ID     : %s%n" +
                        "Payment purpose: %s%n" +
                        "IBAN           : %s%n",
                template.getTemplateID(),
                template.getTemplateName(),
                template.getAddressID(),
                template.getPaymentPurpose(),
                template.getIban());
        logger.info(report + "Operation time: " + ((end - start) / 1000) + " milliseconds.");
    }

    public static class SQLTemplateRepositoryException extends Exception {

        public SQLTemplateRepositoryException() {
            super();
        }

        public SQLTemplateRepositoryException(String message) {
            super(message);
        }

        public SQLTemplateRepositoryException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}