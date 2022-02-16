import api.ScriptInterpreter;
import application.port.AddressRepository;
import application.port.PaymentRepository;
import application.port.TemplateRepository;
import application.port.UserRepository;
import application.service.*;
import application.utils.ContextContainer;
import application.utils.PaymentStatusGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) {
        final Properties properties = new Properties();
        String userDir = System.getProperty("user.dir");

        final String srcDirPath = Path.of(
                        userDir,
                        "src\\main\\resources")
                .toAbsolutePath()
                .toString();
        final String dstDirPath = Path.of(
                        userDir,
                        "src\\main\\output")
                .toAbsolutePath()
                .toString();
        final String configFileName = "config.application.properties";

        ContextContainer context = null;
        try {
            context = ContextContainer.init(srcDirPath, dstDirPath, configFileName);
        } catch (ContextContainer.ContextContainerException e) {
            e.printStackTrace();
        }

        File inputFile = context.getInputFile();
        File outputFile = context.getOutputFile();
        Connection connection = context.getConnection();
        UserRepository userRepository = context.getUserRepository();
        AddressRepository addressRepository = context.getAddressRepository();
        TemplateRepository templateRepository = context.getTemplateRepository();
        PaymentRepository paymentRepository = context.getPaymentRepository();
        PaymentStatusGenerator psGenerator = context.getPsGenerator();
        PaymentExecutor paymentExecutor = context.getPaymentExecutor();

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        ScriptInterpreter interpreter = new ScriptInterpreter(
                inputFile,
                new UserServiceImpl(userRepository),
                new AddressServiceImpl(addressRepository),
                new TemplateServiceImpl(addressRepository, templateRepository),
                new PaymentServiceImpl(addressRepository, templateRepository, paymentRepository),
                new PaymentExecutor(paymentRepository, psGenerator),
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
/*
        try (Connection connection = DatabaseConnector.getDbConnection(new DatabaseConfig())) {
            UserRepository userRepository = new SqlUserRepository(connection);
            AddressRepository addressRepository = new SqlAddressRepository(connection);
            TemplateRepository templateRepository = new SqlTemplateRepository(connection);
            PaymentRepository paymentRepository = new SqlPaymentRepository(connection);
            ExecutorService executorService = Executors.newFixedThreadPool(1);

            ScriptInterpreter interpreter = new ScriptInterpreter
                    (
                            inputFile,
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

 */
    }
}
