package dal;

import application.domain.Address;
import application.port.AddressRepository;

import java.sql.*;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class SqlAddressRepository implements AddressRepository {
    private final Connection dbConnection;
    private final Logger logger;

    public SqlAddressRepository(Logger logger, Connection dbConnection) {
        this.logger= logger;
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Address> getAddresses(String userEmail) {
        return null;
    }

    @Override
    public UUID getAddressID(String address) {
        String GET_ADDRESS_ID_QUERY = String.format("SELECT id FROM mono.addresses " +
                "WHERE address = '%s';", address);

        UUID result;
        try (
                Statement stmt = dbConnection.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY
                )
        ) {
            ResultSet rs = stmt.executeQuery(GET_ADDRESS_ID_QUERY);
            rs.last();
            if (rs.getRow() == 1) {
                result = rs.getObject("id", java.util.UUID.class);
            } else {
                throw new SQLException("ResultSet contains more than the only one record!\n" +
                        "CHeck source data.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    @Override
    public void addAddress(Address paymentAddress) {
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
            e.printStackTrace();
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
}