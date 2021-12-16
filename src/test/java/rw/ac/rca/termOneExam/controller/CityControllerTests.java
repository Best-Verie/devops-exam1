package rw.ac.rca.termOneExam.controller;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.service.CityService;
import rw.ac.rca.termOneExam.utils.APICustomResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
//import rw.ac.rca.termOneExam.utils.JsonUtil;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
public class CityControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;

    @Test
    public void getAll_test() throws Exception {
        when(cityService.getAll()).thenReturn(Arrays.asList(new City(101, "Kigali", 24.0, 75.2), new City(102, "Musanze", 18.0, 64.4), new City(103, "Rubavu", 20.0, 68.0), new City(104, "Nyagatare", 28.0, 82.4)));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/cities/all").accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void findById_test() throws Exception {
        City city =new City(101,"Kigali",23,23.90);

        when(cityService.getById(city.getId())).thenReturn(java.util.Optional.of(city));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/cities/id/101")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":101, \"name\":\"Kigali\",\"weather\":23,\"fahrenheit\":23.90}"))
                .andReturn();
    }

    @Test
    public void findById_NotFound_test() throws Exception {
        City city =new City(101,"Kigali",23,23.90);
        when(cityService.getById(city.getId())).thenReturn(java.util.Optional.of(city));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/cities/id/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":false,\"message\":\"City not found with id 1\"}"))
                .andReturn();
    }
    @Test
    public void create_test() throws Exception {
        when(cityService.save(any(CreateCityDTO.class))).thenReturn(new City("Aboudhabi", 25));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/cities/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Aboudhabi\",\"weather\":25}");

        mockMvc.perform(request).andExpect(status().isCreated()).andExpect(content().json(" {\"id\":0,\"name\": \"Aboudhabi\",\"weather\":25,\"fahrenheit\":0.0}")).andReturn();
    }


    @Test
    public void create_test_duplicateName() throws Exception {
        City city =new City("Kigali",23);
        when(cityService.save(any(CreateCityDTO.class))).thenReturn(city);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/cities/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("\"{\\\"name\\\": \\\"Kigali\\\",\\\"weather\\\":23}\"");

        mockMvc.perform(request).andExpect(status().isBadRequest()).andExpect(content().string("")).andReturn();
    }

}
