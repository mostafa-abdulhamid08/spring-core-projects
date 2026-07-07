package com.vehicleFleetSystem.config;

import com.vehicleFleetSystem.model.ElectricVehicle;
import com.vehicleFleetSystem.model.PetrolVehicle;
import org.springframework.beans.factory.BeanRegistrar;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.core.env.Environment;

public class FleetBeanRegistrar implements BeanRegistrar {
    @Override
    public void register(BeanRegistry registry, Environment env) {
        String fleetType= env.getProperty("fleet.type", "petrol");
        System.out.println("FleetBeanRegistrar: fleet.type = " + fleetType);

        if("electric".equalsIgnoreCase(fleetType))
        {
            System.out.println("FleetBeanRegistrar: Registering Electric Vehicles 🔋");
            registry.registerBean("vehicle 1", ElectricVehicle.class,
                    spec -> spec.supplier(ctx -> new ElectricVehicle("Tesla Model 5",95))
            );
            registry.registerBean("vehicle 2", ElectricVehicle.class,
                    spec-> spec.supplier(ctx -> new ElectricVehicle("BMW i4", 86))
            );
            registry.registerBean("vehicle 3", ElectricVehicle.class,
                    spec-> spec.supplier(ctx-> new ElectricVehicle("Rivian R1T", 92)));

        }
        else
        {
            System.out.println("FleetBeanRegistrar: Registering Petrol Vehicles ⛽");
            registry.registerBean("vehicle 1", PetrolVehicle.class,
                    spec -> spec.supplier(ctx -> new PetrolVehicle("Toyota Camry", 45.5))
            );

            registry.registerBean("vehicle 2", PetrolVehicle.class,
                    spec -> spec.supplier(ctx -> new PetrolVehicle("BMW 3 Series", 52.0))
            );

            registry.registerBean("vehicle 3", PetrolVehicle.class,
                    spec -> spec.supplier(ctx -> new PetrolVehicle("Mercedes C-Class", 60.0))
            );

        }

    }
}
