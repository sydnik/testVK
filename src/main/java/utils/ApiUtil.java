package utils;

import aquality.selenium.core.logging.Logger;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.File;

public class ApiUtil {
    public static JsonNode get(String url){
        try {
            return Unirest.get(url).asJson().getBody();
        } catch (UnirestException e) {
            Logger.getInstance().error("couldn't request \"GET\"" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode post(String url, String filePath, String fileType){
        try {
            return Unirest.post(url).field(fileType, new File(filePath)).asJson().getBody();
        } catch (UnirestException e) {
            Logger.getInstance().error("couldn't request \"POST\"" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
