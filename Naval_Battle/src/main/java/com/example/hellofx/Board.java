package com.example.hellofx;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game board for a Battleship game. This board is used to manage the cells, ships,
 * and check win conditions.
 */
public class Board {
    private int size;
    private List<List<Cell>> cells;
    private List<Ship> ships;

    /**
     * Constructs a new Board with a specified size. Initializes the cells and ships lists.
     *
     * @param size the size of the board (width and height)
     */
    public Board(int size) {
        this.size = size;
        this.cells = new ArrayList<>();
        this.ships = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Cell> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                Cell cell = new Cell(i, j);
                row.add(cell);
            }
            cells.add(row);
        }
    }

    /**
     * Returns the size of the board.
     *
     * @return the size of the board
     */
    public int getSize() {
        return size;
    }

    /**
     * Retrieves a specific cell from the board.
     *
     * @param x the row index of the cell
     * @param y the column index of the cell
     * @return the cell at the specified location
     */
    public Cell getCell(int x, int y) {
        return cells.get(x).get(y);
    }

    /**
     * Returns the list of ships on the board.
     *
     * @return a list of ships
     */
    public List<Ship> getShips() {
        return ships;
    }

    /**
     * Returns the grid of cells.
     *
     * @return a list of lists of cells, representing the grid
     */
    public List<List<Cell>> getCells() {
        return cells;
    }

    /**
     * Adds a ship to the list of ships on the board.
     *
     * @param ship the ship to be added to the board
     */
    public void addShip(Ship ship) {
        ships.add(ship);
    }

    /**
     * Checks if all ships on the board have been destroyed, which would indicate a win condition.
     *
     * @return true if all ships are destroyed, otherwise false
     */
    public boolean isWinCondition() {
        int commonHp = 0;
        for (Ship ship : ships) {
            commonHp += ship.getHitPoints();
        }
        return commonHp <= 0;
    }

}