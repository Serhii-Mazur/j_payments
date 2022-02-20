import api.ScriptInterpreter;
import application.port.AddressRepository;
import application.port.PaymentRepository;
import application.port.TemplateRepository;
import application.port.UserRepository;
import application.service.*;
import application.utils.ContextContainer;
import application.utils.PaymentStatusGenerator;
import application.utils.ShowAllUtil;
import dal.SqlAddressRepository;
import dal.SqlPaymentRepository;
import dal.SqlTemplateRepository;
import dal.SqlUserRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class Main {
    static Logger logger;

    public static void main(String[] args) {
        String userDir = System.getProperty("user.dir");

        final String srcDirPath = Path.of(
                        userDir,
                        "src\\main\\resources")
                .toAbsolutePath()
                .toString();
        final String appConfigFileName = "config.application.properties";
        final String loggerConfigFileName = "config.logger.properties";
        final String loggerConfigFilePath = Path.of(
                        srcDirPath,
                        loggerConfigFileName)
                .toAbsolutePath()
                .toString();

        try (FileInputStream fileInputStream = new FileInputStream(loggerConfigFilePath)) {
            LogManager.getLogManager().readConfiguration(fileInputStream);
            logger = Logger.getLogger(Main.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ContextContainer context = null;
        try {
            context = ContextContainer.init(logger, srcDirPath, appConfigFileName);
        } catch (ContextContainer.ContextContainerException e) {
            e.printStackTrace();
        }

        assert context != null;
        File inputFile = context.getInputFile();
        UserRepository userRepository = context.getUserRepository();
        AddressRepository addressRepository = context.getAddressRepository();
        TemplateRepository templateRepository = context.getTemplateRepository();
        PaymentRepository paymentRepository = context.getPaymentRepository();
        PaymentStatusGenerator psGenerator = context.getPsGenerator();

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        ScriptInterpreter interpreter = new ScriptInterpreter(
                inputFile,
                new UserServiceImpl(userRepository),
                new AddressServiceImpl(addressRepository),
                new TemplateServiceImpl(addressRepository, templateRepository),
                new PaymentServiceImpl(templateRepository, paymentRepository),
                new PaymentExecutor(logger, paymentRepository, psGenerator),
                executorService
        );

        try {
            try {
                interpreter.execute();
            } catch (SqlTemplateRepository.SQLTemplateRepositoryException
                    | SqlAddressRepository.SQLAddressRepositoryException
                    | SqlUserRepository.SQLUserRepositoryException
                    | SqlPaymentRepository.SQLPaymentRepositoryExcception e) {
                e.printStackTrace();
            }
            executorService.shutdown();
            executorService.awaitTermination(10, TimeUnit.SECONDS);
            ShowAllUtil showAll = new ShowAllUtil(logger, userRepository, addressRepository, templateRepository, paymentRepository);
            showAll.writeToLog();
        } catch (IOException | InterruptedException | ShowAllUtil.UtilException e) {
            e.printStackTrace();
        }
    }
}
