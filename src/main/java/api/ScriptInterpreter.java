package api;

import application.constants.ScriptCommands;
import application.service.AddressService;
import application.service.TemplateService;
import application.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScriptInterpreter {
    //    AbsPath -> G:\MONO_IT_School\payments\src\main\resources\config.application.properties
    private final Properties properties = new Properties();
    private final String srcDirPath = Path.of(
            System.getProperty("user.dir"),
            "src\\main\\resources").toAbsolutePath().toString();
    private final String configFileName = "config.application.properties";

    private final UserService userService;
    private final AddressService addressService;
    private final TemplateService templateService;

    public ScriptInterpreter(
            UserService userService,
            AddressService addressService,
            TemplateService templateService
    ) {
        this.userService = userService;
        this.addressService = addressService;
        this.templateService = templateService;
    }

    public void execute() throws IOException {
        for (String line : getScriptLines(getScriptFile())) {
            String command = line.substring(0, line.indexOf(':'));
            String data = line.substring(line.indexOf(':') + 1);
            for (ScriptCommands permittedCommand : ScriptCommands.values()) {
                if (command.equals(permittedCommand.name())) {
                    executeCommand(command, data);
                }
            }
        }
    }

    private File getScriptFile() throws IOException {
        try {
            properties.load(Files.newBufferedReader(Path.of(srcDirPath, configFileName).toAbsolutePath()));
        } catch (IOException e) {
            throw new IOException(e);
        }
        String script = properties.getProperty("script");
        File scriptFile = new File(Path.of(srcDirPath, script).toAbsolutePath().toString());
        if (!scriptFile.exists()) {
            throw new IOException(String.format("File %s does not exists!", scriptFile.getName()));
        }
        return scriptFile;
    }

    private List<String> getScriptLines(File scriptFile) {
        List<String> scriptLines;
        try (
                BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
                Stream<String> lineStream = reader.lines()
        ) {
            scriptLines = Collections.unmodifiableList(lineStream.collect(Collectors.toList()));
//            scriptLines = lineStream.collect(Collectors.toList());
        } catch (IOException e) {
            scriptLines = Collections.emptyList();
        }
        return scriptLines;
    }

    private boolean executeCommand(String command, String data) {
        boolean result = false;
        switch (command) {
            case "REG_USER": {
                String[] splittedUserData = data.split("\\|");
                String fullName = splittedUserData[0];
                String eMail = splittedUserData[1];
                String phoneNumber = splittedUserData[2];
                result = userService.addNewUser(fullName, eMail, phoneNumber);
            }
                break;
            case "ADD_ADDR": {
                String[] splittedAddressData = data.split("\\|");
                String address = splittedAddressData[0];
                String userEmail = splittedAddressData[1];
                result = addressService.addNewAddress(address, userEmail);
            }
                break;
            case "ADD_TMPL": {
                String[] splittedTemplateData = data.split("\\|");
                String templateName = splittedTemplateData[0];
                String paymentPurpose = splittedTemplateData[1];
                String iban = splittedTemplateData[2];
                String address = splittedTemplateData[3];
                result = templateService.addNewTemplate(templateName, address, paymentPurpose, iban);
            }
                break;
            case "ADD_PMNT": {

            }
                break;
        }
        return result;
    }
}
