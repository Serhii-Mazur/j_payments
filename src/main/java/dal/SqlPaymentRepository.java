package dal;

import application.constants.PaymentStatus;
import application.domain.Payment;
import application.port.PaymentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class SqlPaymentRepository implements PaymentRepository {
    private final Logger logger;
    private final Connection dbConnection;

    public SqlPaymentRepository(Logger logger, Connection dbConnection) {
        this.logger = logger;
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Payment> getAllPayments() throws SQLPaymentRepositoryExcception {
        String GET_ALL_PAYMENTS_QUERY = "SELECT * FROM mono.payments";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(GET_ALL_PAYMENTS_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return getPaymentsFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new SQLPaymentRepositoryExcception("Can't execute: ", e);
        }
    }

    @Override
    public List<Payment> getPaymentsByUser(UUID userID) throws SQLPaymentRepositoryExcception {
        throw new SQLPaymentRepositoryExcception(String.format("Method <%s> not implemented yet!", "getPaymentByUser"));
    }

    @Override
    public List<Payment> getPaymentsByAddress(UUID addressID) throws SQLPaymentRepositoryExcception {
        throw new SQLPaymentRepositoryExcception(String.format("Method <%s> not implemented yet!", "getPaymentsByAddress"));
    }

    @Override
    public List<Payment> getPaymentsByTemplate(UUID templateID) throws SQLPaymentRepositoryExcception {
        throw new SQLPaymentRepositoryExcception(String.format("Method <%s> not implemented yet!", "getPaymentsByTemplate"));
    }

    @Override
    public List<Payment> getPaymentsByStatus(PaymentStatus paymentStatus) throws SQLPaymentRepositoryExcception {
        String status = paymentStatus.name();
        String GET_NEW_PAYMENTS =
                "SELECT * FROM mono.payments WHERE payment_status = ?";

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(GET_NEW_PAYMENTS)) {
            int pos = 0;
            preparedStatement.setString(++pos, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getPaymentsFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new SQLPaymentRepositoryExcception("Can't execute: ", e);
        }
    }

    @Override
    public void addPayment(Payment payment) throws SQLPaymentRepositoryExcception {
        long start = System.nanoTime();
        UUID id = payment.getPaymentID();
        UUID templateID = payment.getTemplateID();
        long cardNumber = payment.getCardNumber();
        float paymentAmount = payment.getPaymentAmount();
        String paymentStatus = payment.getPaymentStatus().toString();
        LocalDateTime createdDateTime = payment.getCreatedDateTime();
        LocalDateTime etlDateTime = payment.getEtlDateTime();

        String ADD_PAYMENT_QUERY =
                "INSERT INTO mono.payments " +
                        "(id, template_id, card_number, payment_amount, payment_status, created_date_time, etl_date_time) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(ADD_PAYMENT_QUERY)) {
            int pos = 0;
            preparedStatement.setObject(++pos, id);
            preparedStatement.setObject(++pos, templateID);
            preparedStatement.setLong(++pos, cardNumber);
            preparedStatement.setFloat(++pos, paymentAmount);
            preparedStatement.setString(++pos, paymentStatus);
            preparedStatement.setObject(++pos, createdDateTime);
            preparedStatement.setObject(++pos, etlDateTime);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new SQLPaymentRepositoryExcception("Can't add payment. ", e);
        }
        long end = System.nanoTime();
        String report = String.format("Payment added:%n" +
                        "Payment ID     : %s%n" +
                        "Template ID    : %s%n" +
                        "Card number    : %s%n" +
                        "Payment amount : %.2f%n" +
                        "Payment status : %s%n" +
                        "Created        : %s%n" +
                        "Updated        : %s%n",
                payment.getPaymentID(),
                payment.getTemplateID(),
                payment.getCardNumber(),
                payment.getPaymentAmount(),
                payment.getPaymentStatus().name(),
                payment.getCreatedDateTime(),
                payment.getEtlDateTime());
        logger.info(report + "Operation time: " + ((end - start) / 1000) + " milliseconds.");
    }

    @Override
    public void updatePaymentStatus(UUID paymentID, PaymentStatus newStatus) throws SQLPaymentRepositoryExcception {
        String newPaymentStatus = newStatus.name();
        String UPDATE_PAYMENT_STATUS_QUERY =
                "UPDATE mono.payments " +
                        "SET " +
                        "payment_status = ?, " +
                        "etl_date_time = now() " +
                        "WHERE id = ?";

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(UPDATE_PAYMENT_STATUS_QUERY)) {
            int pos = 0;
            preparedStatement.setString(++pos, newPaymentStatus);
            preparedStatement.setObject(++pos, paymentID);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SQLPaymentRepositoryExcception("Can't update payment status. ", e);
        }
    }

    private List<Payment> getPaymentsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Payment> result = new ArrayList<>();
        while (resultSet.next()) {
            UUID id = (UUID) resultSet.getObject("id");
            UUID templateID = (UUID) resultSet.getObject("template_id");
            long cardNumber = resultSet.getLong("card_number");
            float paymentAmount = resultSet.getFloat("payment_amount");
            LocalDateTime createdDateTime =
                    LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(resultSet.getTimestamp("created_date_time").getTime()),
                            ZoneOffset.of("+02:00")
                    );
            LocalDateTime etlDateTime =
                    LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(resultSet.getTimestamp("etl_date_time").getTime()),
                            ZoneOffset.of("+02:00")
                    );
            PaymentStatus paymentStatus = null;
            String status = resultSet.getString("payment_status");
            for (PaymentStatus pmntStatus : PaymentStatus.values()) {
                if (pmntStatus.name().equals(status)) {
                    paymentStatus = pmntStatus;
                }
            }
            Payment payment = new Payment(id, templateID, cardNumber, paymentAmount, paymentStatus, createdDateTime, etlDateTime);
            result.add(payment);
        }
        return result;
    }

    public static class SQLPaymentRepositoryExcception extends Exception {

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
