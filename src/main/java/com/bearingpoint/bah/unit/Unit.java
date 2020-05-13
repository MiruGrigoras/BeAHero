package com.bearingpoint.bah.unit;

public abstract class Unit {
    String name;
    int price;
    int hitPoints;
    int meleeDamage;
    int warriorsNumber;
    int movementRadius;

    public abstract void move();

    public abstract void attack();

    @Override
    public String toString() {
        return "Unit{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", warriorsNumber=" + warriorsNumber +
                "}";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getMeleeDamage() {
        return meleeDamage;
    }

    public void setMeleeDamage(int meleeDamage) {
        this.meleeDamage = meleeDamage;
    }

    public int getWarriorsNumber() {
        return warriorsNumber;
    }

    public void setWarriorsNumber(int warriorsNumber) {
        this.warriorsNumber = warriorsNumber;
    }

    public int getMovementRadius() {
        return movementRadius;
    }

    public void setMovementRadius(int movementRadius) {
        this.movementRadius = movementRadius;
    }

}
