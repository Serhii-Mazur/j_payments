package dal;

import application.domain.Payment;
import application.domain.Template;
import application.port.PaymentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class SqlPaymentRepository implements PaymentRepository {
    private final Connection dbConnection;

    public SqlPaymentRepository(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Template> getPaymentsByUser(UUID userID) throws SQLPaymentRepositoryExcception {
        throw new SQLPaymentRepositoryExcception(String.format("Method <%s> not implemented yet!", "getPaymentByUser"));
    }

    @Override
    public List<Template> getPaymentsByAddress(UUID addressID) throws SQLPaymentRepositoryExcception {
        throw new SQLPaymentRepositoryExcception(String.format("Method <%s> not implemented yet!", "getPaymentsByAddress"));
    }

    @Override
    public List<Template> getPaymentsByTemplate(UUID templateID) throws SQLPaymentRepositoryExcception {
        throw new SQLPaymentRepositoryExcception(String.format("Method <%s> not implemented yet!", "getPaymentsByTemplate"));
    }

    @Override
    public boolean addPayment(Payment payment) {
        UUID id = payment.getPaymentID();
        UUID templateID = payment.getTemplateID();
        long cardNumber = payment.getCardNumber();
        float paymentAmount = payment.getPaymentAmount();
        String paymentStatus = payment.getPaymentStatus().toString();   // TODO: modify according to 3NF of DB
        LocalDateTime createdDateTime = payment.getCreatedDateTime();
        LocalDateTime etlDateTime = payment.getEtlDateTime(); // TODO: method's name could be changed

        String ADD_PAYMENT_QUERY =
                "INSERT INTO mono.payments " +
                        "(id, template_id, card_number, payment_amount, payment_status, created_date_time, etl_date_time) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        boolean result = false;
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(ADD_PAYMENT_QUERY)) {
            int pos = 0;
            preparedStatement.setObject(++pos, id);
            preparedStatement.setObject(++pos, templateID);
            preparedStatement.setLong(++pos, cardNumber);
            preparedStatement.setFloat(++pos, paymentAmount);
            preparedStatement.setString(++pos, paymentStatus);
            preparedStatement.setObject(++pos, createdDateTime);
            preparedStatement.setObject(++pos, etlDateTime);

            result = preparedStatement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public class SQLPaymentRepositoryExcception extends Exception {

        public SQLPaymentRepositoryExcception() {
            super();
        }

        public SQLPaymentRepositoryExcception(String message) {
            super(message);
        }

        public SQLPaymentRepositoryExcception(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
