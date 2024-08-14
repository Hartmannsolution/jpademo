package dk.cphbusiness.flightdemo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.OffsetDateTime;


public class Tester {
    public static void main(String[] args) throws Exception {
//        String jsonString = "{\"myDateTime\":\"2024-08-14T00:05:00+00:00\"}";
        String jsonString = "{\"myDateTime\":\"2024-08-14T00:50:00+00:00\"}";

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        MyApp myObject = mapper.readValue(jsonString, MyApp.class);

        System.out.println("Deserialized date-time: " + myObject.getMyDateTime());
    }
    private static class MyApp {

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private OffsetDateTime myDateTime;

        // getters and setters
        public OffsetDateTime getMyDateTime() {
            return myDateTime;
        }

        public void setMyDateTime(OffsetDateTime myDateTime) {
            this.myDateTime = myDateTime;
        }
    }
}

