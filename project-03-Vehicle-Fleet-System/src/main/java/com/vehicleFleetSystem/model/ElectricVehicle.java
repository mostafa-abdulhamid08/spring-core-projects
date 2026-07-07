package com.vehicleFleetSystem.model;

public class ElectricVehicle implements Vehicle{
    private final String name;
    private final int batteryLevel;

    public ElectricVehicle(String name, int batteryLevel) {
        this.name = name;
        this.batteryLevel = batteryLevel;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return "Electric Vehicle";
    }

    @Override
    public String getFuelType() {
        return "Battery";
    }

    @Override
    public void displayInfo() {
        System.out.println("[ELECTRIC] " + name + " | Battery: " + batteryLevel + "%");
    }
}
