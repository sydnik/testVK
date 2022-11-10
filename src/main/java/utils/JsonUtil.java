package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

public class JsonUtil {
    private static ISettingsFile dataForTest = new JsonSettingsFile("dataForTestVK.json");
    private static ISettingsFile configuration = new JsonSettingsFile("config.json");

    public static String getString(String key){
        return dataForTest.getValue("/" + key).toString();
    }

    public static int getInt(String key){
        return (int) dataForTest.getValue("/" + key);
    }

    public static String getConfiguration(String key){
        return configuration.getValue("/" + key).toString();
    }
}
