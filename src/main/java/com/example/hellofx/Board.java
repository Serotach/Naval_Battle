package com.example.hellofx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private int size;
    private List<List<Cell>> cells;
    private List<Ship> ships;

    public Board(int size) {
        this.size = size;
        this.cells = new ArrayList<>();
        this.ships = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Cell> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(new Cell());
            }
            cells.add(row);
        }
    }

    public int getSize() {
        return size;
    }

    public boolean shootAt(int row, int col) {
        Cell target = getCell(row, col);
        if (!target.isShot()) {
            target.hit();
            if (target.isShipPlaced()) {
                target.getShip().hit();
                System.out.println("You wounded the ship!");
                if (target.getShip().isSunk()) {
                    System.out.println("Enemy's ship is down!");
                }
                return true;
            } else {
                System.out.println("You missed the shot :(");
            }
        } else {
            System.out.println("This cell was already hit");
        }
        return false;
    }

    public boolean shootAt(Cell target) {
        if (!target.isShot()) {
            target.hit();
            if (target.isShipPlaced()) {
                target.getShip().hit();
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static void drawField(List<List<Cell>> cells) {
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < cells.size(); i++) {
            System.out.print(i + " ");
            for (int j = 0; j < cells.get(1).size(); j++) {
                if (!cells.get(j).get(i).isShipPlaced() && !cells.get(j).get(i).isShot()) {
                    System.out.print("- ");
                } else if (cells.get(j).get(i).isShipPlaced() && !cells.get(j).get(i).isShot()) {
                    System.out.print("X ");
                } else if (cells.get(j).get(i).isShipPlaced() && cells.get(j).get(i).isShot()) {
                    System.out.print("N ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
    }

    public Cell getCell(int x, int y) {
        return cells.get(x).get(y);
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    public List<Ship> getShips() {
        return ships;
    }

    public List<List<Cell>> getCells() {
        return cells;
    }

    public boolean isWinCondition() {
        int commonHp = 0;
        for (Ship ship : ships) {
            commonHp += ship.getHitPoints();
        }
        if (commonHp > 0) {
            return false;
        } else {
            return true;
        }
    }
}