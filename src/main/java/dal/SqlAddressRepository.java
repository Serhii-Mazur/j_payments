package dal;

import application.domain.Address;
import application.port.AddressRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class SqlAddressRepository implements AddressRepository {
    private final Connection dbConnection;
    private final Logger logger;

    public SqlAddressRepository(Logger logger, Connection dbConnection) {
        this.logger = logger;
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Address> getAddresses(String userEmail) {
        return null;
    }

    @Override
    public List<Address> getAllAddresses() throws SQLAddressRepositoryException {
        List<Address> result = new ArrayList<>();
        String GET_ALL_ADDRESSES_QUERY = "SELECT * FROM mono.addresses";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(GET_ALL_ADDRESSES_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UUID addressID = resultSet.getObject("id", java.util.UUID.class);
                String addr = resultSet.getString("address");
                String userEmail = resultSet.getString("user_email");
                Address address = new Address(addressID, addr, userEmail);
                result.add(address);
            }
        } catch (SQLException e) {
            throw new SQLAddressRepositoryException("Can't execute: ", e);
        }
        return result;
    }

    @Override
    public UUID getAddressID(String address) throws SQLAddressRepositoryException {
        String GET_ADDRESS_ID_QUERY = "SELECT id FROM mono.addresses WHERE address = ?";

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(GET_ADDRESS_ID_QUERY,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
            int pos = 0;
            preparedStatement.setString(++pos, address);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getObject("id", java.util.UUID.class);

        } catch (SQLException e) {
            throw new SQLAddressRepositoryException("Can't execute. ", e);
        }
    }

    @Override
    public void addAddress(Address paymentAddress) throws SQLAddressRepositoryException {
        long start = System.nanoTime();
        String user_email = paymentAddress.getUserEmail();
        String address = paymentAddress.getAddress();
        UUID id = paymentAddress.getAddressID();

        String ADD_ADDRESS_QUERY = "INSERT INTO mono.addresses (id, address, user_email) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(ADD_ADDRESS_QUERY)) {
            int pos = 0;
            preparedStatement.setObject(++pos, id);
            preparedStatement.setString(++pos, address);
            preparedStatement.setString(++pos, user_email);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new SQLAddressRepositoryException("Can't execute. ", e);
        }
        long end = System.nanoTime();
        String report = String.format("Payment address added:%n" +
                        "Address ID     : %s%n" +
                        "Address        : %s%n" +
                        "User e-mail    : %s%n",
                paymentAddress.getAddressID(),
                paymentAddress.getAddress(),
                paymentAddress.getUserEmail());
        logger.info(report + "Operation time: " + ((end - start) / 1000) + " milliseconds.");
    }

    public static class SQLAddressRepositoryException extends Exception {
        public SQLAddressRepositoryException() {
            super();
        }

        public SQLAddressRepositoryException(String message) {
            super(message);
        }

        public SQLAddressRepositoryException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}