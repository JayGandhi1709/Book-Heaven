package com.example.BookHeaven.Utils;

//import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

public class JsonResponseUtils {

    // private static final Gson gson = new Gson();

    // Method to generate JSON response for any type of object
    // public static <T> String toJson(ResponseMessage<T> responseMessage) {
    // Map<String, Object> responseMap = new HashMap<>();
    // responseMap.put("success", responseMessage.isSuccess());
    // responseMap.put("message", responseMessage.getMessage());
    // responseMessage.getData().ifPresent(data -> responseMap.put("data", data));
    // return gson.toJson(responseMap);
    // }

    // Method to generate JSON response for any type of object
    public static <T> Map<String, Object> toJson(ResponseMessage<T> responseMessage) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", responseMessage.isSuccess());
        responseMap.put("message", responseMessage.getMessage());
        responseMessage.getData().ifPresent(data -> responseMap.put("data", data));
        return responseMap;
    }
}
