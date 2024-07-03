package ru.dodonov.eventmanager.locations;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dodonov.eventmanager.web.GlobalExceptionHandler;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final LocationService locationService;
    private final LocationDtoMapper dtoMapper;

    public LocationController(LocationService locationService, LocationDtoMapper dtoMapper) {
        this.locationService = locationService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        log.info("Get request for get all locations");
        List<Location> locations = locationService.getAllLocations();
        return ResponseEntity.ok().body(locations.stream().map(dtoMapper::toDto).toList());
    }

    @PostMapping("")
    public ResponseEntity<LocationDto> createLocation(
            @RequestBody @Valid LocationDto locationDto
    ) {
        log.info("Get request for location create: locationDto={}", locationDto);
        Location createdLocation = locationService.createLocation(dtoMapper.toDomain(locationDto));
        return ResponseEntity
                .status(201)
                .body(dtoMapper.toDto(createdLocation));
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<LocationDto> deleteLocation(
            @PathVariable Long locationId
    ) {
        log.info("Get request for location delete: locationId={}", locationId);
        Location deletedLocation = locationService.deleteLocation(locationId);
        return ResponseEntity
                .status(204)
                .body(dtoMapper.toDto(deletedLocation));
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<LocationDto> getLocation(
            @PathVariable Long locationId
    ) {
        log.info("Get request for get location: locationId={}", locationId);
        Location foundLocation = locationService.getLocationById(locationId);
        return ResponseEntity
                .ok()
                .body(dtoMapper.toDto(foundLocation));
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<LocationDto> updateLocation(
            @PathVariable Long locationId,
            @RequestBody @Valid LocationDto locationDto
    ) {
        log.info("Get request for location update: locationId={}, locationDto={}", locationId, locationDto);
        Location updatedLocation = locationService.updateLocation(
                dtoMapper.toDomain(locationDto),
                locationId
        );
        return ResponseEntity
                .ok()
                .body(dtoMapper.toDto(updatedLocation));
    }
}
