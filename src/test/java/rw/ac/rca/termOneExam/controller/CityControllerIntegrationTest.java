package rw.ac.rca.termOneExam.controller;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAll_Success() throws JSONException {
        String response = this.restTemplate.getForObject("/api/cities/all", String.class);
        System.out.println(response);
        JSONAssert.assertEquals("[{\"id\":101,\"name\":\"Kigali\",\"weather\":24,\"fahrenheit\":75.2},{\"id\":102,\"name\":\"Musanze\",\"weather\":18,\"fahrenheit\":64.4},{\"id\":103,\"name\":\"Rubavu\",\"weather\":20,\"fahrenheit\":68.0},{\"id\":104,\"name\":\"Nyagatare\",\"weather\":28,\"fahrenheit\":82.4}]", response, true);
    }


    @Test
    public void create_testSuccess() {
        City theCity = new City("Kirehe",23);
        ResponseEntity<City> response = restTemplate.postForEntity("/api/cities/add", theCity, City.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Kirehe", Objects.requireNonNull(response.getBody()).getName());
        assertEquals(0.0, Objects.requireNonNull(response.getBody()).getFahrenheit(),8.9);

    }


    @Test
    public void create_BadRequest() {
        City theCity = new City("Musanze",26);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/cities/add", theCity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void findById_Success() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/cities/id/104", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findById_NotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/cities/id/1", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}