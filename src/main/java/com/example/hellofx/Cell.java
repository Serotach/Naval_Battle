package com.example.hellofx;

public class Cell {
    private boolean isShipPlaced;
    private Ship ship;
    private boolean isShot;

    public Cell() {
        this.isShipPlaced = false;
        this.ship = null;
        this.isShot = false;
    }

    public boolean isShipPlaced() {
        return isShipPlaced;
    }

    public void placeShip(Ship ship) {
        this.ship = ship;
        this.isShipPlaced = true;
    }

    public void removeShip() {
        this.ship = null;
        this.isShipPlaced = false;
    }

    public boolean isShot() {
        return isShot;
    }

    public void hit() {
        this.isShot = true;
    }

    public Ship getShip() {
        return ship;
    }
}