package com.example.hellofx;

public class Cell {
    private boolean isShipPlaced;
    private Ship ship;
    private boolean isShot;
    private int row, col;

    public Cell(int row, int col) {
        this.isShipPlaced = false;
        this.ship = null;
        this.isShot = false;
        this.row = row;
        this.col = col;
    }

    public boolean isShipPlaced() {
        return isShipPlaced;
    }

    public void placeShip(Ship ship) {
        this.ship = ship;
        this.isShipPlaced = true;
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

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}