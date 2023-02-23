package com.example.marsRoverPhotos.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Getter
public class RoverService {

    private static final String API_KEY = "4KyyHycAvnywP1DvJgKWjnJ7kc18GCygZTf9MpNr";

    private Map<String, List<String>> validCameras = new HashMap<>();

    public RoverService() {
        validCameras.put("Opportunity", Arrays.asList("FHAZ", "RHAZ", "NAVCAM", "PANCAM", "MINITES"));
        validCameras.put("Curiosity", Arrays.asList("FHAZ", "RHAZ", "MAST", "CHEMCAM", "MAHLI", "MARDI", "NAVCAM"));
        validCameras.put("Spirit", Arrays.asList("FHAZ", "RHAZ", "NAVCAM", "PANCAM", "MINITES"));
    }

}
