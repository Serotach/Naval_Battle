package com.example.hellofx;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Implements a strategy for choosing cells to attack based on randomness.
 * This strategy selects a random cell from the list of unshot cells on the board.
 */
public class Random_Strategy implements Strategy{
    private Board board = HelloController.playerBoard;

    /**
     * Chooses a random cell to attack from all available unshot cells on the board.
     *
     * @return The randomly selected cell to attack, or null if no cells are available.
     */
    public Cell chooseCell() {
        List<Cell> availableCells = new ArrayList<>(); // List to hold all unshot cells
        // Loop through each cell on the board to find unshot cells
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                Cell cell = board.getCell(i, j); // Get the cell at coordinates (i, j)
                if (!cell.isShot()) { // Check if the cell has not been shot at
                    availableCells.add(cell); // Add unshot cell to the list
                }
            }
        }
        if (availableCells.isEmpty()) { // Check if there are any unshot cells left
            return null; // Return null if no available cells
        }

        Random random = new Random(); // Create a Random object for selecting a cell
        // Return a random cell from the list of unshot cells
        return availableCells.get(random.nextInt(availableCells.size()));
    }
}
