package dk.cphbusiness.flightdemo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightReaderTest {
    FlightReader instance = new FlightReader();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test the Flight API reader")
    void reader() {
        try {
            List<FlightReader.FlightDTO> flight = instance.reader("https://api.aviationstack.com/v1/flights?access_key=%s&limit=%s&offset=%s", 1, 10);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("")
    void jsonToFile() {
    }

    @Test
    @DisplayName("")
    void jsonFromFile() {
    }

    @Test
    @DisplayName("")
    void main() {
    }
}