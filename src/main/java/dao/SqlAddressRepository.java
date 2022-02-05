package dao;

import application.domain.Address;
import application.port.AddressRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

public class SqlAddressRepository implements AddressRepository {
    private final Connection dbConnection;

    public SqlAddressRepository(Connection dbConnection) {
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
    public boolean addAddress(Address paymentAddress) {
        String user_email = paymentAddress.getUserEmail();
        String address = paymentAddress.getAddress();
        String id = paymentAddress.getAddressID().toString();

        String ADD_ADDRESS_QUERY = String.format("INSERT INTO mono.addresses (id, address, user_email) " +
                "VALUES ('%s', '%s', '%s');", id, address, user_email);

        boolean result;

        try (
                Statement stmt = dbConnection.createStatement()
        ) {
            stmt.executeUpdate(ADD_ADDRESS_QUERY);
            result = true;

        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}
