package com.example.marsRoverPhotos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.marsRoverPhotos.dto.RoverDTO;
import com.example.marsRoverPhotos.repository.PreferencesRepository;


@ExtendWith(MockitoExtension.class)
public class RoverServiceTest {

    @Mock
    private PreferencesRepository preferencesRepository;

    @InjectMocks
    private RoverService roverService;

    private RoverDTO roverDTO;

    @BeforeEach
    public void setup() {
        roverDTO = new RoverDTO();
        roverDTO.setRoverType("Curiosity");
        roverDTO.setCameraMast(true);
        roverDTO.setMarsSol(1000);
    }

    @Test
    public void testGetApiUrlEndpointsWithValidCamera() throws IllegalAccessException, InvocationTargetException {
        // Given
        String expectedUrl = "https://api.nasa.gov/mars-photos/api/v1/rovers/Curiosity/photos?sol=1000&api_key=4KyyHycAvnywP1DvJgKWjnJ7kc18GCygZTf9MpNr&camera=MAST";

        // When
        var urls = roverService.getApiUrlEndpoints(roverDTO);

        // Then
        assertEquals(Arrays.asList(expectedUrl), urls);
    }

}
