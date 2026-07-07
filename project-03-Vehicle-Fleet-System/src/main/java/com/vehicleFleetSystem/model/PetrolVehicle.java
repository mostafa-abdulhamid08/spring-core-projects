package com.vehicleFleetSystem.model;

public class PetrolVehicle implements Vehicle{
    private final String name;
    private final double fuelLevel;

    public PetrolVehicle(String name, double fuelLevel) {
        this.name = name;
        this.fuelLevel = fuelLevel;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return "Petrol Vehicle";
    }

    @Override
    public String getFuelType() {
        return "Gasoline";
    }

    @Override
    public void displayInfo() {
        System.out.println("[PETROL] " + name + " | Fuel: " + fuelLevel + "L");

    }
}
