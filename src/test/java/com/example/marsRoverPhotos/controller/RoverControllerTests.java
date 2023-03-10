package com.example.marsRoverPhotos.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.marsRoverPhotos.dto.RoverDTO;
import com.example.marsRoverPhotos.service.RoverService;

@ExtendWith(MockitoExtension.class)
class RoverControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RoverService roverService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RoverController(roverService)).build();
    }

    @Test
    @DisplayName("Test 1")
    public void testPostHomeView() throws Exception {
        // Given
        RoverDTO roverDTO = new RoverDTO();
        roverDTO.setRoverType("Opportunity");
        roverDTO.setMarsSol(1);
        roverDTO.setUserId(1L);
        when(roverService.save(any())).thenReturn(roverDTO);

        // When & then
        mockMvc.perform(post("/")
                        .flashAttr("roverDTO", roverDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?userId=1"));
    }

    @Test
    @DisplayName("Test 2")
    public void testGetSavedPreferences() throws Exception {
        // Given
        RoverDTO roverDTO = new RoverDTO();
        roverDTO.setRoverType("Opportunity");
        roverDTO.setMarsSol(1);
        roverDTO.setUserId(1L);
        when(roverService.findByUserId(1L)).thenReturn(roverDTO);

        // When & then
        mockMvc.perform(get("/savedPreferences")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roverType").value("Opportunity"))
                .andExpect(jsonPath("$.marsSol").value(1))
                .andExpect(jsonPath("$.userId").value(1));
    }
}
