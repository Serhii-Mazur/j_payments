package api;

import application.constants.ScriptCommands;
import application.service.*;
import dal.SqlAddressRepository;
import dal.SqlPaymentRepository;
import dal.SqlTemplateRepository;
import dal.SqlUserRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScriptInterpreter {

    private final File inputFile;
    private final UserService userService;
    private final AddressService addressService;
    private final TemplateService templateService;
    private final PaymentService paymentService;
    private final PaymentExecutor paymentExecutor;
    private final ExecutorService executorService;

    public ScriptInterpreter(
            File inputFile,
            UserService userService,
            AddressService addressService,
            TemplateService templateService,
            PaymentService paymentService,
            PaymentExecutor paymentExecutor,
            ExecutorService executorService) {
        this.inputFile = inputFile;
        this.userService = userService;
        this.addressService = addressService;
        this.templateService = templateService;
        this.paymentService = paymentService;
        this.paymentExecutor = paymentExecutor;
        this.executorService = executorService;
    }

    public void execute() throws IOException,
            SqlTemplateRepository.SQLTemplateRepositoryException,
            SqlAddressRepository.SQLAddressRepositoryException,
            SqlUserRepository.SQLUserRepositoryException,
            SqlPaymentRepository.SQLPaymentRepositoryExcception {
        executorService.execute(paymentExecutor);
        for (String line : getScriptLines(inputFile)) {
            String[] splittedLine = line.split(":");
            String command = splittedLine[0];
            String data = splittedLine[1];
            for (ScriptCommands permittedCommand : ScriptCommands.values()) {
                if (command.equals(permittedCommand.name())) {
                    executeCommand(command, data);
                }
            }
        }
    }

    private void executeCommand(String command, String data)
            throws
            SqlTemplateRepository.SQLTemplateRepositoryException,
            SqlAddressRepository.SQLAddressRepositoryException,
            SqlUserRepository.SQLUserRepositoryException,
            SqlPaymentRepository.SQLPaymentRepositoryExcception {
        switch (command) {
            case "REG_USER": {
                String[] splittedUserData = data.split("\\|");
                String fullName = splittedUserData[0];
                String eMail = splittedUserData[1];
                String phoneNumber = splittedUserData[2];

                userService.addNewUser(fullName, eMail, phoneNumber);
            }
            break;
            case "ADD_ADDR": {
                String[] splittedAddressData = data.split("\\|");
                String address = splittedAddressData[0];
                String userEmail = splittedAddressData[1];

                addressService.addNewAddress(address, userEmail);
            }
            break;
            case "ADD_TMPL": {
                String[] splittedTemplateData = data.split("\\|");
                String templateName = splittedTemplateData[0];
                String paymentPurpose = splittedTemplateData[1];
                String iban = splittedTemplateData[2];
                String address = splittedTemplateData[3];

                templateService.addNewTemplate(templateName, address, paymentPurpose, iban);
            }
            break;
            case "ADD_PMNT": {
                String[] splittedPaymentData = data.split("\\|");
                long cardNumber = Long.parseLong(splittedPaymentData[0]);
                float paymentAmount = Float.parseFloat(splittedPaymentData[1]);
                String templateName = splittedPaymentData[2];
                String address = splittedPaymentData[3];

                paymentService.addNewPayment(cardNumber, paymentAmount, templateName, address);
            }
            break;
        }
    }

    private List<String> getScriptLines(File scriptFile) {
        List<String> scriptLines;
        try (
                BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
                Stream<String> lineStream = reader.lines()
        ) {
            scriptLines = Collections.unmodifiableList(lineStream.collect(Collectors.toList()));
        } catch (IOException e) {
            scriptLines = Collections.emptyList();
        }
        return scriptLines;
    }
}
