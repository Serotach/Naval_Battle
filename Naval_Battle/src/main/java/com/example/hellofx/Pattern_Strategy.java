package com.example.hellofx;

import java.util.List;
import java.util.Random;

/**
 * A strategy for choosing cells to attack based on a pattern logic.
 * It selects a cell based on the longest ship that has not yet been sunk,
 * or uses a random strategy as a fallback.
 */
public class Pattern_Strategy implements Strategy {
    private Board board = HelloController.playerBoard;
    static Random random = new Random();

    /**
     * Chooses a cell to attack based on the current state of the board and the longest ship available.
     *
     * @return The chosen cell to attack.
     */
    public Cell chooseCell() {
        List<Ship> ships = board.getShips();

        int maxSizeOfShip = 0;
        Ship longestShip = null;
        for (Ship ship : ships) {
            if (ship.getSize() > maxSizeOfShip && !ship.isSunk()) {
                maxSizeOfShip = ship.getSize();
                longestShip = ship;
            }
        }

        //  Error handling if no ship was found
        if (maxSizeOfShip == 0) {
            return new Random_Strategy().chooseCell(); // Fallback to random strategy if no ships are available
        }

        // Use a random strategy if the longest ship has size 1
        if (maxSizeOfShip == 1) {
            Strategy strategy = new Random_Strategy();
            return strategy.chooseCell();

        }

        // Choose a cell from the damaged longest ship's coordinates that has not yet been shot at
        if (longestShip.getHitPoints() != longestShip.getSize()) {
            List<Cell> availableForShoot = longestShip.getCoordinates();
            int choose = random.nextInt(0, availableForShoot.size());
            while (availableForShoot.get(choose).isShot()) {
                choose = random.nextInt(0, availableForShoot.size());
            }
            return availableForShoot.get(choose);
        } else {
            // A pattern-based method for cell selection based on the ship size
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    int sum = i + j;
                    if (sum == 0) sum++;
                    if (((sum) % maxSizeOfShip == maxSizeOfShip - 1) && !board.getCell(i, j).isShot()) {
                        return board.getCell(i, j);
                    }
                }
            }
        }
        // Safety check for unhandled logic paths
        System.out.println("You are not allowed to be here..."); // Debugging message indicating logic error
        return new Random_Strategy().chooseCell(); // Fallback if no valid cells found
    }
}
