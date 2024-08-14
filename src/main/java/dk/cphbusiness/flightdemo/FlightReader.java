package dk.cphbusiness.flightdemo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.OffsetTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import dk.cphbusiness.persistence.jdbc.Demo;
import jakarta.persistence.EntityManagerFactory;
import lombok.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class FlightReader {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private final String FLIGHT_URL = "https://api.aviationstack.com/v1/flights?access_key=%s&limit=%s&offset=%s";
    // jackson objectmapper

    public List<FlightDTO> reader(String urlString,  int numberOfRequests, int limit) throws IOException{
        List<FlightDTO> flightDTOList = new ArrayList();
        Properties props = new Properties();
        props.load(Demo.class.getClassLoader().getResourceAsStream("config.properties"));
        int offset = 0;
        FlightCollectionDTO flights = null;
        List<FlightCollectionDTO> flightCollectionDTOList = new ArrayList<>();

        for(int i = 0; i < numberOfRequests; i++){
            urlString = String.format(urlString,  props.getProperty("aviation.key"), limit, offset );
            String jsonStr = "";
            URL url = new URL(urlString);
            flights = objectMapper.readValue(url, FlightCollectionDTO.class );
            flightCollectionDTOList.add(flights);
            offset += limit;
        }
        System.out.println("Finished reading\n"+flightCollectionDTOList);
        flightCollectionDTOList.forEach(flightCollectionDTO -> {
            for(FlightDTO flightDTO : flightCollectionDTO.getData()){
                flightDTOList.add(flightDTO);
            }
        });
        return flightDTOList;
    }
    public void jsonToFile(List<FlightDTO> flightCollection, String fileName) throws IOException {
        objectMapper.writeValue(Paths.get(fileName).toFile(), flightCollection);
    }

    public List<FlightDTO> jsonFromFile(String fileName) throws IOException {
        List<FlightDTO> flights = objectMapper.readValue(Paths.get(fileName).toFile(), List.class);
        return flights;
    }

    public static void main(String[] args) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.writer(new DefaultPrettyPrinter());
        FlightReader flightReader = new FlightReader();
        try {
//            List<FlightDTO> result = flightReader.reader(flightReader.FLIGHT_URL, 50, 100);
//            flightReader.jsonToFile(result, "flightsfile.json");
//            System.out.println(result);
//
            // read FlightCollectionDTO from file
            List<FlightDTO> flights = objectMapper.readValue(Paths.get("flights.json").toFile(), List.class);
            System.out.println("FlIGHT COLLECTION: "+flights);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    private static class FlightCollectionDTO{
        private FlightDTO[] data;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    private static class FlightDTO{
        private String flight_date;
        private String flight_status;
        private AirportTime departure;
        private AirportTime arrival;
        private AirplaneDTO flight;
        private AirlineDTO airline;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    private static class AirportTime{
        private String airport;
        private String timezone;
        private String iata;
//        @JsonSerialize(using = OffsetDateTimeSerializer.class)
//        @JsonDeserialize(using = OffsetTimeDeserializer.class)
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private OffsetDateTime scheduled;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    private static class AirlineDTO{
        private String name;
        private String iata;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    private static class AirplaneDTO{
        private String number;
        private String iata;
    }
}
