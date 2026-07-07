package com.vehicleFleetSystem.repository;

import com.vehicleFleetSystem.model.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FleetRepository {

    private final List<Vehicle> fleet = new ArrayList<>();

    public void save(Vehicle vehicle)
    {
        fleet.add(vehicle);
        System.out.println("FleetRepository: saved → " + vehicle.getType());

    }
    public List<Vehicle> findAll()               { return fleet; }
    public int count()                           { return fleet.size(); }

    public List<Vehicle> findByType (String type)
    {
       return fleet.stream()
                .filter(v -> v.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public int countByType(String type)
    {
        return findByType(type).size();
    }

}
