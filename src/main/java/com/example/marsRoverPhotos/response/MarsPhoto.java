package com.example.marsRoverPhotos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarsPhoto {
    private Long id;
    private Integer sol;
    private MarsCamera camera;
    @JsonProperty("img_src")
    private String imgSrc;
    @JsonProperty("earth_date")
    private String earthDate;
    private MarsRover rover;

}
