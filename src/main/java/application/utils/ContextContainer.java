package application.utils;

import application.port.AddressRepository;
import application.port.PaymentRepository;
import application.port.TemplateRepository;
import application.port.UserRepository;
import application.service.PaymentExecutor;
import dal.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class ContextContainer {
    private static ContextContainer instance;

    private final Connection connection;
    private final File inputFile;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final TemplateRepository templateRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentExecutor paymentExecutor;
    private final PaymentStatusGenerator psGenerator;

    private ContextContainer(File inputFile,
                             Connection connection,
                             UserRepository userRepository,
                             AddressRepository addressRepository,
                             TemplateRepository templateRepository,
                             PaymentRepository paymentRepository,
                             PaymentExecutor paymentExecutor,
                             PaymentStatusGenerator psGenerator) {
        this.inputFile = inputFile;
        this.connection = connection;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.templateRepository = templateRepository;
        this.paymentRepository = paymentRepository;
        this.paymentExecutor = paymentExecutor;
        this.psGenerator = psGenerator;
    }

    public static ContextContainer init(Logger logger, String srcDirPath, String configFileName) throws ContextContainerException {
        if (instance == null) {
            Properties properties = new Properties();
            try {
                properties.load(Files.newBufferedReader(Path.of(srcDirPath, configFileName).toAbsolutePath()));
            } catch (IOException e) {
                throw new ContextContainerException("Can't open file. ", e);
            }
            String inputFilePath = Path.of(srcDirPath, properties.getProperty("input_file_name")).toAbsolutePath().toString();
            String dbConfigFilePath = Path.of(srcDirPath, properties.getProperty("dbconfig_file_name")).toAbsolutePath().toString();
            File inputFile;
            Connection connection;
            UserRepository userRepository;
            AddressRepository addressRepository;
            TemplateRepository templateRepository;
            PaymentRepository paymentRepository;
            PaymentExecutor paymentExecutor;
            PaymentStatusGenerator psGenerator;
            try {
                inputFile = createInputFile(inputFilePath);
                connection = createConnection(dbConfigFilePath);
                userRepository = createUserRepository(logger, connection);
                addressRepository = createAddressRepository(logger, connection);
                templateRepository = createTemplateRepository(logger, connection);
                paymentRepository = createPaymentRepository(logger, connection);
                psGenerator = createPaymentStatusGenerator();
                paymentExecutor = createPaymentExecutor(logger, paymentRepository, psGenerator);
            } catch (ContextContainerException e) {
                throw new ContextContainerException("Can't create context. ", e);
            }
            instance = new ContextContainer(
                    inputFile,
                    connection,
                    userRepository,
                    addressRepository,
                    templateRepository,
                    paymentRepository,
                    paymentExecutor,
                    psGenerator);
        }
        return instance;
    }

    public File getInputFile() {
        return inputFile;
    }

    public Connection getConnection() {
        return connection;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public AddressRepository getAddressRepository() {
        return addressRepository;
    }

    public TemplateRepository getTemplateRepository() {
        return templateRepository;
    }

    public PaymentRepository getPaymentRepository() {
        return paymentRepository;
    }

    public PaymentExecutor getPaymentExecutor() {
        return paymentExecutor;
    }

    public PaymentStatusGenerator getPsGenerator() {
        return psGenerator;
    }

    private static File createInputFile(String inputFilePath) throws ContextContainerException {
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists()) {
            throw new ContextContainerException(String.format("File %s does not exists!", inputFile.getAbsolutePath()));
        }
        return inputFile;
    }



    private static Connection createConnection(String dbConfigFilePath) throws ContextContainerException {
        Connection connection;
        try {
            connection = DatabaseConnector.getPooledConnection(DatabaseConfig.init(dbConfigFilePath));
        } catch (DatabaseConfig.ConfigDBException
                | ClassNotFoundException
                | SQLException e) {
            throw new ContextContainerException("Can't create connection to DB: ", e);
        }
        return connection;
    }

    private static UserRepository createUserRepository(Logger logger, Connection connection) {
        return new SqlUserRepository(logger, connection);
    }

    private static AddressRepository createAddressRepository(Logger logger, Connection connection) {
        return new SqlAddressRepository(logger, connection);
    }

    private static TemplateRepository createTemplateRepository(Logger logger, Connection connection) {
        return new SqlTemplateRepository(logger, connection);
    }

    private static PaymentRepository createPaymentRepository(Logger logger, Connection connection) {
        return new SqlPaymentRepository(logger, connection);
    }

    private static PaymentExecutor createPaymentExecutor(Logger logger, PaymentRepository paymentRepository, PaymentStatusGenerator psGenerator) {
        return new PaymentExecutor(logger, paymentRepository, psGenerator);
    }

    private static PaymentStatusGenerator createPaymentStatusGenerator() {
        return new PaymentStatusGenerator();
    }

    public static class ContextContainerException extends Exception {
        public ContextContainerException() {
            super();
        }

        public ContextContainerException(String message) {
            super(message);
        }

        public ContextContainerException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
