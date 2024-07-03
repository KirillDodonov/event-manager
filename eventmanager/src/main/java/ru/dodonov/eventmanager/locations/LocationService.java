package ru.dodonov.eventmanager.locations;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationEntityMapper entityMapper;
    private final LocationRepository repository;

    public LocationService(LocationEntityMapper entityMapper, LocationRepository repository) {
        this.entityMapper = entityMapper;
        this.repository = repository;
    }

    public List<Location> getAllLocations() {
        return repository.findAll()
                .stream()
                .map(entityMapper::toDomain)
                .toList();
    }

    public Location createLocation(Location locationToCreate) {
        if (locationToCreate.id() != null) {
            throw new IllegalArgumentException("Can not create location with provided id. Id must be empty");
        }
        LocationEntity createdLocation = repository.save(entityMapper.toEntity(locationToCreate));
        return entityMapper.toDomain(createdLocation);
    }

    public Location deleteLocation(Long locationId) {
        LocationEntity locationToDelete = repository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Can not find location with id = %s"
                        .formatted(locationId)));
        repository.deleteById(locationId);
        return entityMapper.toDomain(locationToDelete);
    }

    public Location getLocationById(Long locationId) {
        LocationEntity foundLocation = repository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Can not find location with id = %s"
                        .formatted(locationId)));
        return entityMapper.toDomain(foundLocation);
    }

    public Location updateLocation(
            Location newLocation,
            Long locationId
    ) {
        if (newLocation.id() != null) {
            throw new IllegalArgumentException("Can not create location with provided id. Id must be empty");
        }
        LocationEntity locationToUpdate = repository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Can not find location with id = %s"
                        .formatted(locationId)));
        locationToUpdate.setName(newLocation.name());
        locationToUpdate.setAddress(newLocation.address());
        locationToUpdate.setCapacity(newLocation.capacity());
        locationToUpdate.setDescription(newLocation.description());

        LocationEntity updatedLocation = repository.save(locationToUpdate);

        return entityMapper.toDomain(updatedLocation);
    }
}
