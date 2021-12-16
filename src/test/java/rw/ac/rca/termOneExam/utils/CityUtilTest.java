package rw.ac.rca.termOneExam.utils;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CityUtilTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ICityRepository cityRepository;

    @Test
    public void noCityWithWeather_GreaterThan40_DegreeCelcius(){
        int citiesWithWeatherGreaterThan40DegreeCelciuss = 0;
        List<City> cities = cityRepository.findAll();
        for (City city: cities){
            if(city.getWeather() > 40){
                citiesWithWeatherGreaterThan40DegreeCelciuss ++;
            }
        }
        assertEquals(0, citiesWithWeatherGreaterThan40DegreeCelciuss);
    }

    @Test
    public void noCityWithWeather_LessThan10_DegreeCelcius(){
        int citiesWithWeatherLessThan10DegreeCelciuss = 0;
        List<City> cities = cityRepository.findAll();
        for (City city: cities){
            if(city.getWeather() < 10)
                citiesWithWeatherLessThan10DegreeCelciuss ++;
        }
        assertEquals(0, citiesWithWeatherLessThan10DegreeCelciuss);
    }

    @Test
    public void citiesContainKigaliAndMusanze(){
        List<City> cities = cityRepository.findAll();
        boolean isMusanzePresent = false;
        boolean iskigaliPresent = false;

        for (City city : cities){
            if(city.getName().equals("Kigali") ){
                iskigaliPresent = true;
            }
            if(city.getName().equals("Musanze")){
                isMusanzePresent = true;
            }
        }

        assertTrue(iskigaliPresent&&isMusanzePresent);
    }



    @Test
    public void testMocking() {
        List<City> mockedList = Mockito.mock(ArrayList.class);
        City city = new City("Musanze", 18);
        mockedList.add(city);
        Mockito.verify(mockedList).add(city);

        assertEquals(0, mockedList.size());
    }

    @Test
    public void testSpying() {
        List<City> spyList = Mockito.spy(ArrayList.class);
        City city = new City("Musanze", 18);
        spyList.add(city);
        Mockito.verify(spyList).add(city);

        assertEquals(1, spyList.size());
    }
}
