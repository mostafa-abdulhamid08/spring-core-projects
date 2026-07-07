package com.vehicleFleetSystem.service;

import com.vehicleFleetSystem.model.Vehicle;
import com.vehicleFleetSystem.repository.FleetRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FleetService {
    // ======================================================
    // Constructor Injection — Spring بيحقن تلقائياً (Autowiring)
    // final = ضمان إن الـ dependencies مش هتتغير
    // مش محتاج @Autowired في Spring Boot الحديث
    // ======================================================

    private final FleetRepository fleetRepository;
    private final FleetLogger fleetLogger;

    public FleetService(FleetRepository fleetRepository, FleetLogger fleetLogger) {
        this.fleetRepository = fleetRepository;
        this.fleetLogger = fleetLogger;
        System.out.println("FleetService: Constructor → dependencies injected via Autowiring ✅");
    }
    @PostConstruct
    public void initFleet() {
        fleetLogger.log("@PostConstruct → FleetService initialized 🚗");
    }
    public void addVehicle(Vehicle vehicle)
    {
        if(vehicle ==null) {
            fleetLogger.logError("Cannot add null vehicle!");
            return;
        }
        fleetRepository.save(vehicle);
        fleetLogger.logSuccess("Vehicle added: " + vehicle.getType());
    }
    public List<Vehicle> getAllVehicles()
    {
        return fleetRepository.findAll();
    }
    public List<Vehicle>  getVehiclesByType(String type)
    {
        List<Vehicle> result =fleetRepository.findByType(type);
        fleetLogger.log("Found " + result.size() + " " + type + " vehicles");
        return result;
    }
    public void printFleetSummary() {
        System.out.println("\n=== Fleet Summary ===");
        System.out.println("Total:    " + fleetRepository.count());
        System.out.println("Electric: " + fleetRepository.countByType("Electric Vehicle"));
        System.out.println("Petrol:   " + fleetRepository.countByType("Petrol Vehicle"));
        System.out.println("====================");
    }
    public void displayAllVehicles()
    {
        fleetLogger.log("Displaying all vehicles:");
        fleetRepository.findAll().forEach(Vehicle::displayInfo);
    }

}
