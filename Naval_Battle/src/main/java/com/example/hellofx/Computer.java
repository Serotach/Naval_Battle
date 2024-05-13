package com.example.hellofx;

import java.util.List;
import javafx.scene.control.Button;
import java.util.Random;

/**
 * Represents the computer's strategy and actions in a Battleship game.
 * This class extends Player and handles ship placement and attack decisions made by the computer.
 */
public class Computer extends Player {
    private static Board computerBoard = HelloController.computerBoard;

    /**
     * Randomly places ships on the computer's grid. This method tries to place ships of decreasing sizes
     * from 4 decks to 1 deck and updates the GUI to reflect these placements.
     *
     * @param cells The grid of cells representing the computer's game board.
     * @param compButtons A 2D array of buttons representing the computer's side of the GUI.
     */
    public static void placeShipFx(List<List<Cell>> cells, Button[][] compButtons) {
        Random random = new Random();
        int deck = 4; // Start with the largest ship of 4 decks
        int count = 0;
        while (deck >= 1) {
            int x = random.nextInt(0, 10);
            int y = random.nextInt(0, 10);
            int direction = random.nextInt(1, 3); // 1 for horizontal, 2 for vertical
            if (!Player.isAvailable(x, y, deck, direction, cells)) {
                continue;  // Skip to the next iteration if placement is not possible
            }
            // Creating a new ship and connect cells that contains it with this ship.
            // Changing text on buttons, that represents enemies ships on "X"
            Ship ship = new Ship(deck, computerBoard);
            computerBoard.addShip(ship);
            for (int i = 0; i < deck; i++) {
                if (direction == 1) {
                    Cell targetCell = cells.get(x).get(y + i);
                    compButtons[x][y + i].setText("X");
                    compButtons[x][y + i].setOpacity(0.0);
                    targetCell.placeShip(ship);
                } else {
                    Cell targetCell = cells.get(x + i).get(y);
                    compButtons[x + i][y].setOpacity(0.0);
                    compButtons[x + i][y].setText("X");
                    targetCell.placeShip(ship);
                }
            }
            count++;
            if (count == 1) deck--;
            if (count == 3) deck--;
            if (count == 6) deck--;
            if (count == 10) break;
        }
    }

    /**
     * Executes a shooting action at the specified cell on the player's board.
     * Updates the GUI to show the result of the shot (hit or miss).
     *
     * @param target The cell that the computer decides to shoot at.
     * @param playerButtons A 2D array of buttons representing the player's side of the GUI.
     * @return true if a ship is hit, false if it is a miss or the cell was already shot.
     */
    public static boolean shootAtFx(Cell target, Button[][] playerButtons) {
        int row = target.getRow();
        int col = target.getCol();
        if (!target.isShot()) {
            target.hit(); // Mark the cell as shot
            playerButtons[row][col].setText("M"); // Mark the GUI to indicate a miss
            playerButtons[row][col].setOpacity(1.0);
            if (target.isShipPlaced()) {
                target.getShip().hit(); // Reduce the hit points of the ship
                playerButtons[row][col].setText("X"); // Mark the GUI to indicate a hit
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
