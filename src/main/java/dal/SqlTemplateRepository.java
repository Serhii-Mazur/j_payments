package dal;

import application.domain.Template;
import application.port.TemplateRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public UUID getTemplateIdByAddressAndTemplateName(String address, String templateName) {
        String GET_TEMPLATE_ID_QUERY =
//                "SELECT id FROM mono.addresses WHERE address = ?";
                "SELECT templ.id FROM (" +
                "SELECT * FROM mono.addresses WHERE address = ?) AS addr " +
                "LEFT JOIN (" +
                "SELECT * FROM mono.templates t WHERE template_name = ?) AS templ " +
                "ON addr.id = templ.address_id";

        UUID result = null;
        ResultSet resultSet = null;
        try (
                PreparedStatement preparedStatement = dbConnection.prepareStatement(GET_TEMPLATE_ID_QUERY)
        ) {
            int pos = 0;
            preparedStatement.setString(++pos, address);
            preparedStatement.setString(++pos, templateName);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = (UUID) resultSet.getObject(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void addTemplate(Template template) {
        UUID id = template.getTemplateID();
        UUID addressID = template.getAddressID();
        String templateName = template.getTemplateName();
        String paymentPurpose = template.getPaymentPurpose();
        String iban = template.getIban();

//        String ADD_TEMPLATE_QUERY = String.format("INSERT INTO mono.templates (id, template_name, address_id, payment_purpose, iban) " +
//                "VALUES ('%s', '%s', '%s', '%s', '%s');", id, template_name, address_id, payment_purpose, iban);
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
            e.printStackTrace();
        }
    }
}