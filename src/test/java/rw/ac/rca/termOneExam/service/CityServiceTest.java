package rw.ac.rca.termOneExam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class CityServiceTest {
    @Mock
    private ICityRepository cityRepository;

    @InjectMocks
    private CityService cityService;


    @Test
    public void findById_test() {
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(new City(109, "Busanza", 34,98.3)));
        assertEquals("Busanza", cityService.getById(109).get().getName());
    }
    @Test
    public void findById_NotFoundtest() {
        when(cityRepository.findById(anyLong())).thenReturn(null);
        assertEquals(null, cityService.getById(109));
    }
    @Test
    public void all_test() {
        when(cityRepository.findAll()).thenReturn(Arrays.asList(new City(101,"Kigali",24.0,75.2),new City(102,"Musanze",18.0,64.4),new City(103,"Rubavu",20.0,68.0),new City(104,"Nyagatare",28.0,82.4)));
        assertEquals(64.4, cityRepository.findAll().get(1).getFahrenheit());
    }

    @Test
    public void existByName_test() {
        when(cityRepository.existsByName(anyString())).thenReturn(true);
        assertEquals(true, cityService.existsByName("Musanze"));
    }

    @Test
    public void existByName_testNotFound() {
        when(cityRepository.existsByName(anyString())).thenReturn(false);
        assertEquals(false, cityService.existsByName("kigeli"));
    }


    @Test
    public void create_test() {
        when(cityRepository.save(any(City.class))).thenReturn(new City("Aboudhabi", 45));
        assertEquals("Aboudhabi", cityService.save(new CreateCityDTO()).getName());
    }

}
