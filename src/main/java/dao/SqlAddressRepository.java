package dao;

import application.domain.Address;
import application.port.AddressRepository;

import java.sql.Connection;
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
    public List<Address> getAddresses(UUID userID) {
        return null;
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
