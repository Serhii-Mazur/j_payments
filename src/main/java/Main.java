import api.ScriptInterpreter;
import application.port.AddressRepository;
import application.port.PaymentRepository;
import application.port.TemplateRepository;
import application.port.UserRepository;
import application.service.*;
import dal.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnector.getDbConnection(new DatabaseConfig())) {
            UserRepository userRepository = new SqlUserRepository(connection);
            AddressRepository addressRepository = new SqlAddressRepository(connection);
            TemplateRepository templateRepository = new SqlTemplateRepository(connection);
            PaymentRepository paymentRepository = new SqlPaymentRepository(connection);

            ExecutorService executorService = Executors.newFixedThreadPool(1);
            ScriptInterpreter interpreter = new ScriptInterpreter
                    (
                            new UserServiceImpl(userRepository),
                            new AddressServiceImpl(addressRepository),
                            new TemplateServiceImpl(addressRepository, templateRepository),
                            new PaymentServiceImpl(addressRepository, templateRepository, paymentRepository),
                            new PaymentExecutor(paymentRepository),
                            executorService
                    );
            try {
                interpreter.execute();
                executorService.shutdown();
                executorService.awaitTermination(15, TimeUnit.SECONDS);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (DatabaseConfig.ConfigDBException e) {
            e.printStackTrace();
        }
    }
}
