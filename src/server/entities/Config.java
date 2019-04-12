package server.entities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final String PROPERTIES_FILE = "server.properties";

    public static int
            PORT,
            HISTORY_LENGTH,
            DELAY;
    public static String HELLO_MESSAGE;

    static {
        Properties properties = new Properties();
        try(FileInputStream propertiesFile = new FileInputStream(PROPERTIES_FILE)){
            properties.load(propertiesFile);
            PORT = Integer.parseInt(properties.getProperty("PORT"));
            HISTORY_LENGTH = Integer.parseInt(properties.getProperty("HISTORY_LENGTH"));
            DELAY = Integer.parseInt(properties.getProperty("DELAY"));
            HELLO_MESSAGE = properties.getProperty("HELLO_MESSAGE");
        } catch (FileNotFoundException e) {
            System.err.println("Can`t find server.properties!");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("System.properties seems broken");
            e.printStackTrace();
        }
    }

}
