package utils;

import org.apache.commons.lang.RandomStringUtils;

public class StringUtil {

    public static String randomText(){
        return RandomStringUtils.randomAlphabetic((int) (Math.random() * 100));
    }

    public static String randomText(int size){
        return RandomStringUtils.randomAlphabetic(size);
    }

    public static String getFirstUrl(String text){
        text = text.substring(text.indexOf("https"), text.indexOf("\","));
        text = text.replaceAll("\\\\", "");
        return text;
    }
}
