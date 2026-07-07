package com.vehicleFleetSystem;

import com.vehicleFleetSystem.config.FleetConfig;
import com.vehicleFleetSystem.model.Vehicle;
import com.vehicleFleetSystem.service.FleetService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FleetApp {
    public static void main(String[] args) {
        //System.setProperty("fleet.type", "electric");
        System.out.println("========== Starting Spring Context ==========");
        var context = new AnnotationConfigApplicationContext(FleetConfig.class);
        System.out.println("========== Context Ready ==========\n");
        FleetService fleetService = context.getBean(FleetService.class);

        System.out.println("\n--- Adding Vehicles to Fleet ---");
        Vehicle v1= context.getBean("vehicle 1", Vehicle.class);
        Vehicle v2= context.getBean("vehicle 2", Vehicle.class);
        Vehicle v3= context.getBean("vehicle 3", Vehicle.class);

        fleetService.addVehicle(v1);
        fleetService.addVehicle(v2);
        fleetService.addVehicle(v3);

        System.out.println("\n--- Display All ---");
        fleetService.displayAllVehicles();
        System.out.println("\n--- Get By Type ---");
        String type = "Electric Vehicle";
        fleetService.getVehiclesByType(type);

        fleetService.printFleetSummary();

        // Singleton Proof
        System.out.println("--- Singleton Proof ---");
        FleetService fs1 = context.getBean(FleetService.class);
        FleetService fs2 = context.getBean(FleetService.class);
        System.out.println("fs1 == fs2 ? " + (fs1 == fs2)); // true

        Vehicle v1Again = context.getBean("vehicle 1", Vehicle.class);
        System.out.println("v1 == v1Again ? " + (v1 == v1Again)); // true

        System.out.println("\n========== Closing Context ==========");
        context.close();







    }
}
