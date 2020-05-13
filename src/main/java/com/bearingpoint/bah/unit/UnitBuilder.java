package com.bearingpoint.bah.unit;

public class UnitBuilder {
    public Unit createInstance(String unit) {
        switch (unit) {
            case "archer":
                return new Archer();
            case "cavalry":
                return new Cavalry();
            case "footman":
                return new Footman();
            default:
                return null;
        }
    }
}
