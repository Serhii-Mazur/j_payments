package api;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ScriptInterpreter {
    //    AbsPath -> G:\MONO_IT_School\payments\src\main\resources\config.application.properties
    private final Properties properties = new Properties();
    private final String srcDirPath = Path.of(
            System.getProperty("user.dir"),
            "src\\main\\resources").toAbsolutePath().toString();
    private final String configFileName = "config.application.properties";

    public void execute() {
        try (BufferedReader reader = new BufferedReader(new FileReader(getScriptFile()))) {
            reader.lines();
            String line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(getScriptFile()))) {

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean readLine() {
        return false;
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
}
