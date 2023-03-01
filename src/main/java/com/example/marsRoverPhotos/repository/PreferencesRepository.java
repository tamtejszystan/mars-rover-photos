package com.example.marsRoverPhotos.repository;

import com.example.marsRoverPhotos.dto.RoverDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferencesRepository extends JpaRepository<RoverDTO, Long> {
    RoverDTO findByUserId(Long userId);
}
