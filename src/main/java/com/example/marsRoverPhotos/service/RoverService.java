package com.example.marsRoverPhotos.service;

import com.example.marsRoverPhotos.dto.RoverDTO;
import com.example.marsRoverPhotos.repository.PreferencesRepository;
import com.example.marsRoverPhotos.response.MarsPhoto;
import com.example.marsRoverPhotos.response.MarsRoverApiResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Service
@Getter
public class RoverService {

    private static final String API_KEY = "4KyyHycAvnywP1DvJgKWjnJ7kc18GCygZTf9MpNr";

    private Map<String, List<String>> validCameras = new HashMap<>();

    @Autowired
    private PreferencesRepository preferencesRepository;

    public RoverService() {
        validCameras.put("Opportunity", Arrays.asList("FHAZ", "RHAZ", "NAVCAM", "PANCAM", "MINITES"));
        validCameras.put("Curiosity", Arrays.asList("FHAZ", "RHAZ", "MAST", "CHEMCAM", "MAHLI", "MARDI", "NAVCAM"));
        validCameras.put("Spirit", Arrays.asList("FHAZ", "RHAZ", "NAVCAM", "PANCAM", "MINITES"));
    }

    public MarsRoverApiResponse getRoverData(RoverDTO roverDTO) throws InvocationTargetException, IllegalAccessException {
        RestTemplate rt = new RestTemplate();

        List<String> apiUrlEndpoints = getApiUrlEndpoints(roverDTO);
        List<MarsPhoto> photos = new ArrayList<>();
        MarsRoverApiResponse resp = new MarsRoverApiResponse();
        apiUrlEndpoints.stream()
                .forEach(url -> {
                    MarsRoverApiResponse apiResp = rt.getForObject(url, MarsRoverApiResponse.class);
                    photos.addAll(apiResp.getPhotos());
                });

        resp.setPhotos(photos);

        return resp;
    }


    public List<String> getApiUrlEndpoints(RoverDTO roverDTO) throws InvocationTargetException, IllegalAccessException {
        List<String> urls = new ArrayList<>();

        Method[] methods = roverDTO.getClass().getMethods();

        /*
        Iterate through all "getCamera" methods in the RoverDto class.
        Generate a list of API endpoint URLs based on the input parameters.
        Use the generated URLs to fetch pictures for a given Mars rover, camera, and sol.
        */

        for (Method method : methods) {
            if (method.getName().contains("getCamera") && Boolean.TRUE.equals(method.invoke(roverDTO))) {
                String cameraName = method.getName().substring(9).toUpperCase();
                if (validCameras.get(roverDTO.getRoverType()).contains(cameraName)) {
                    urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/" + roverDTO.getRoverType() + "/photos?sol=" + roverDTO.getMarsSol() + "&api_key=" + API_KEY + "&camera=" + cameraName);
                }
            }
        }
        return urls;
    }

    public RoverDTO save(RoverDTO roverDTO) {
        return preferencesRepository.save(roverDTO);
    }

    public RoverDTO findByUserId(Long userId) {
        return preferencesRepository.findByUserId(userId);
    }
}
