package com.example.hellofx;

import javafx.scene.control.Button;
import java.util.Arrays;
import java.util.List;
import javafx.geometry.Point2D;
import java.util.function.Predicate;

/**
 * The Player class contains the logic for player interactions in a game,
 * such as placing ships on the board and shooting at the opponent's board.
 */
public class Player {
    private static Board playerBoard = HelloController.playerBoard;
    private static Board computerBoard = HelloController.computerBoard;

    /**
     * Checks if a ship can be placed at a specified location on the board.
     *
     * @param x The x-coordinate where the ship placement begins.
     * @param y The y-coordinate where the ship placement begins.
     * @param deck The number of decks the ship has.
     * @param rotation The orientation of the ship (0 for horizontal, 1 for vertical).
     * @param cells The grid of cells representing the game board.
     * @return true if the ship can be placed; false otherwise.
     */
    public static boolean isAvailable(int x, int y, int deck, int rotation, List<List<Cell>> cells) {
        // Lambda to check the presence of a ship in a given cell
        Predicate<Point2D> isOccupied = point -> {
            int px = (int) point.getX();
            int py = (int) point.getY();
            // Make sure that the coordinates do not go beyond the list boundaries before checking
            if (px >= 0 && px < cells.size() && py >= 0 && py < cells.get(px).size()) {
                return cells.get(px).get(py).isShipPlaced();
            }
            return false;
        };

        // Check each segment of the ship based on its deck count and orientation
        for (int i = 0; i < deck; i++) {
            int xi = (rotation == 1) ? 0 : i;
            int yi = (rotation == 1) ? i : 0;

            if (x + xi >= cells.size() || y + yi >= cells.get(x + xi).size()) {
                // Ship would be out of bounds
                return false;
            }

            // List of all points to be checked (current position, vertically and horizontally side by side, diagonally)
            List<Point2D> pointsToCheck = Arrays.asList(
                    new Point2D(x + xi, y + yi),
                    new Point2D(x + 1 + xi, y + yi), new Point2D(x - 1 + xi, y + yi),
                    new Point2D(x + xi, y + 1 + yi), new Point2D(x + xi, y - 1 + yi),
                    new Point2D(x + 1 + xi, y + 1 + yi), new Point2D(x - 1 + xi, y - 1 + yi),
                    new Point2D(x + 1 + xi, y - 1 + yi), new Point2D(x - 1 + xi, y + 1 + yi)
            );

            // Check all defined points for any ship placement violations
            if (pointsToCheck.stream().anyMatch(point ->
                    point.getX() >= 0 && point.getX() < cells.size() &&
                            point.getY() >= 0 && point.getY() < cells.get((int)point.getX()).size() &&
                            isOccupied.test(point))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Places a ship on the game board.
     *
     * @param cells The game board's grid of cells.
     * @param row The starting row for the ship placement.
     * @param col The starting column for the ship placement.
     * @param direction The orientation of the ship (0 for horizontal, 1 for vertical).
     * @param deck The number of decks the ship has.
     */
    public void placeShipFx(List<List<Cell>> cells, int row, int col, int direction, int deck) {
        Ship ship = new Ship(deck, playerBoard);
        playerBoard.addShip(ship);
        for (int i = 0; i < deck; i++) {
            if (direction == 1) {
                cells.get(row).get(col + i).placeShip(ship);
                ship.placeShip(cells.get(row).get(col + i));

            } else {
                cells.get(row + i).get(col).placeShip(ship);
                ship.placeShip(cells.get(row + i).get(col));
            }
        }
    }

    /**
     * Attempts to shoot at a specified location on the computer's board.
     *
     * @param row The row to target.
     * @param col The column to target.
     * @param button The button representing the cell on the UI.
     * @return true if the shot hit a ship; false otherwise.
     */
    public static boolean shootAtFx(int row, int col, Button button) {
        Cell target = computerBoard.getCell(row, col);
        if (!target.isShot()) {
            target.hit();
            button.setText("O");
            button.setOpacity(1.0);
            if (target.isShipPlaced()) {
                target.getShip().hit();
                button.setText("X");
                button.setOpacity(1.0);
                return true;
            }
        }
        return false;
    }

}
