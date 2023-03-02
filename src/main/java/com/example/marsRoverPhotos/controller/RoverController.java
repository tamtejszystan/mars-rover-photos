package com.example.marsRoverPhotos.controller;

import com.example.marsRoverPhotos.dto.RoverDTO;
import com.example.marsRoverPhotos.response.MarsRoverApiResponse;
import com.example.marsRoverPhotos.service.RoverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.lang.reflect.InvocationTargetException;

@Controller
public class RoverController {

    private final RoverService roverService;

    public RoverController(RoverService roverService) {
        this.roverService = roverService;
    }

    @GetMapping("/")
    public String getHomeView(ModelMap model, Long userId, Boolean createUser) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        RoverDTO roverDTO = createDefaultRoverDto(userId);

        if (Boolean.TRUE.equals(createUser) && userId == null) {
            roverDTO = roverService.save(roverDTO);
        } else {
            roverDTO = roverService.findByUserId(userId);
            if (roverDTO == null) {
                roverDTO = createDefaultRoverDto(userId);
            }
        }
        MarsRoverApiResponse roverData = roverService.getRoverData(roverDTO);
        model.put("roverData", roverData);
        model.put("roverDTO", roverDTO);
        model.put("validCameras", roverService.getValidCameras().get(roverDTO.getRoverType()));
        if (!Boolean.TRUE.equals(roverDTO.getRememberPreferences()) && userId != null) {
            RoverDTO defaultRoverDto = createDefaultRoverDto(userId);
            roverService.save(defaultRoverDto);
        }

        return "index";
    }

    @GetMapping("/savedPreferences")
    @ResponseBody
    public RoverDTO getSavedPreferences (Long userId) {
        return userId != null ? roverService.findByUserId(userId) : createDefaultRoverDto(userId);
    }


    private RoverDTO createDefaultRoverDto(Long userId) {
        RoverDTO roverDTO = new RoverDTO();
        roverDTO.setRoverType("Opportunity");
        roverDTO.setMarsSol(1);
        roverDTO.setUserId(userId);
        return roverDTO;
    }

    @PostMapping("/")
    public String postHomeView(RoverDTO roverDTO) {
        roverDTO = roverService.save(roverDTO);
        return "redirect:/?userId=" + roverDTO.getUserId();
    }
}
