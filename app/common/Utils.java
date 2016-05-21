package common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    public static String convertObjectToJsonString(Object myObj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(myObj);

    }
    public static String convertObjectToJsonString(Object myObj, ObjectMapper mapper) throws JsonProcessingException {
        return mapper.writeValueAsString(myObj);
    }
}
