package com.example.hellofx;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private boolean sunk;
    private int size;
    private int hitPoints;
    private Board board;
    private List<Cell> coordinates;

    public Ship(int size, Board board) {
        this.sunk = false;
        this.size = size;
        this.hitPoints = size;
        this.board = board;
        this.coordinates = new ArrayList<>(20);
    }

    public void hit() {
        hitPoints--;
        if (hitPoints == 0) sunk = true;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getSize() {
        return size;
    }

    public boolean isSunk() {
        return sunk;
    }

    public void placeShip(Cell coordinate) {
        coordinate.placeShip(this);
        coordinates.add(coordinate);
    }

    public List<Cell> getCoordinates() {
        return coordinates;
    }
}