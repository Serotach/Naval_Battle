package com.example.hellofx;

import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;

/**
 * This is the controller class for the Battleship game interface using JavaFX.
 * It handles all user interactions and manages the game logic between the player and the computer.
 */
public class HelloController {
    int HowManyShipsArePlaced = 0;
    int direction = 0;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane playerGrid;

    @FXML
    private GridPane compGrid;

    @FXML
    Button horizontalButton;

    @FXML
    Button verticalButton;

    @FXML
    Label whatToDoLabel;

    @FXML
    Label whoWin;
    @FXML
    Label firstShip;
    @FXML
    Label secondShip;
    @FXML
    Label thirdShip;
    @FXML
    Label fourthShip;
    @FXML
    Label fifthShip;
    @FXML
    Label sixthShip;
    @FXML
    Label seventhShip;
    @FXML
    Label eighthShip;
    @FXML
    Label ninthShip;
    @FXML
    Label tenthShip;
    @FXML
    Label tactic;
    public static Board playerBoard = new Board(10);
    public static Board computerBoard = new Board(10);
    public static Player player = new Player();

    /**
     * Initializes the game interface, setting up the grids and ships for both player and computer.
     */
    public void initialize() {

        List<Label> shipLabels = new ArrayList<>();
        shipLabels.add(firstShip);
        shipLabels.add(secondShip);
        shipLabels.add(thirdShip);
        shipLabels.add(fourthShip);
        shipLabels.add(fifthShip);
        shipLabels.add(sixthShip);
        shipLabels.add(seventhShip);
        shipLabels.add(eighthShip);
        shipLabels.add(ninthShip);
        shipLabels.add(tenthShip);
        for (Label label : shipLabels) {
            label.setOpacity(0.0);
        }
        tactic.setOpacity(0.0);

        Button[][] compButtons = new Button[10][10];
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Button button = new Button();
                button.setPrefSize(35, 35);
                button.setUserData(new Point2D(row, col));
                compGrid.add(button, row, col);
                compButtons[row][col] = button;
                button.setOpacity(0.0);
                button.setText("O");
                button.setStyle("-fx-background-color: honeydew;");
                button.setStyle("-fx-border-color: gray");
                button.setMouseTransparent(true);

                button.setOnAction(e -> playerTakeTurnFx(button, compButtons, shipLabels));
            }
        }
        Computer.placeShipFx(computerBoard.getCells(), compButtons);

        Button[][] playerButtons = new Button[10][10];
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Button button = new Button();
                button.setPrefSize(35, 35);
                button.setUserData(new Point2D(row, col));
                playerGrid.add(button, row, col);
                playerButtons[row][col] = button;
                button.setOpacity(0.0);
                button.setStyle("-fx-background-color: honeydew;");
                button.setStyle("-fx-border-color: gray");

                if (HowManyShipsArePlaced <= 9) {
                    button.setOnAction(e -> handleButtonClick(button, playerButtons, whatToDoLabel, compButtons, shipLabels));
                }
            }
        }
    }

    /**
     * Handles the initial button click on the player's grid to start ship placement.
     *
     * @param button The button clicked by the player.
     * @param playerButtons A two-dimensional array of buttons representing the player's grid.
     * @param whatToDoLabel Label indicating next steps.
     * @param compButtons  A two-dimensional array of buttons representing the computer grid.
     * @param shipLabels List of labels for ship status updates.
     */
    private void handleButtonClick(Button button, Button[][] playerButtons, Label whatToDoLabel, Button[][] compButtons, List<Label> shipLabels) {
        horizontalButton.setVisible(true);
        verticalButton.setVisible(true);

        horizontalButton.setOnAction(e -> {
            direction = 2;
            horizontalButton.setVisible(false);
            verticalButton.setVisible(false);
            continueWithPlacingShips(button, playerButtons, whatToDoLabel, compButtons, shipLabels);
        });

        verticalButton.setOnAction(e -> {
            direction = 1;
            horizontalButton.setVisible(false);
            verticalButton.setVisible(false);
            continueWithPlacingShips(button, playerButtons, whatToDoLabel, compButtons, shipLabels);
        });

    }

    /**
     * Continues ship placement after the player selects orientation of the ship.
     *
     * @param button The button representing the starting point of the ship.
     * @param playerButtons A two-dimensional array of buttons representing the player's grid.
     * @param whatToDoLabel Label indicating next steps.
     * @param compButtons  A two-dimensional array of buttons representing the computer grid.
     * @param shipLabels List of labels for ship status updates.
     */
    private void continueWithPlacingShips(Button button, Button[][] playerButtons, Label whatToDoLabel, Button[][] compButtons, List<Label> shipLabels) {
        Point2D point = (Point2D) button.getUserData();
        int row = (int) point.getX();
        int col = (int) point.getY();
        if (HowManyShipsArePlaced == 0) {
            if (player.isAvailable(row, col, 4, direction, playerBoard.getCells())) {
                player.placeShipFx(playerBoard.getCells(), row, col, direction, 4);
                if (direction == 1) {
                    playerButtons[row][col].setText("O");
                    playerButtons[row][col].setOpacity(1.0);
                    playerButtons[row][col + 1].setText("O");
                    playerButtons[row][col + 1].setOpacity(1.0);
                    playerButtons[row][col + 2].setText("O");
                    playerButtons[row][col + 2].setOpacity(1.0);
                    playerButtons[row][col + 3].setText("O");
                    playerButtons[row][col + 3].setOpacity(1.0);
                } else if (direction == 2) {
                    playerButtons[row][col].setText("O");
                    playerButtons[row][col].setOpacity(1.0);
                    playerButtons[row + 1][col].setText("O");
                    playerButtons[row + 1][col].setOpacity(1.0);
                    playerButtons[row + 2][col].setText("O");
                    playerButtons[row + 2][col].setOpacity(1.0);
                    playerButtons[row + 3][col].setText("O");
                    playerButtons[row + 3][col].setOpacity(1.0);
                }
                HowManyShipsArePlaced++;
                whatToDoLabel.setText("Pick a cell for you 3-deck ship");
            } else {
                DialogManager.showAlert(Alert.AlertType.ERROR, "Incorrect placement", "Ship can't be placed here, choose another cell", null, null, null);
                return;
            }
        } else if (HowManyShipsArePlaced >= 1 && HowManyShipsArePlaced <= 2) {
            if (player.isAvailable(row, col, 3, direction, playerBoard.getCells())) {
                player.placeShipFx(playerBoard.getCells(), row, col, direction, 3);
                if (direction == 1) {
                    playerButtons[row][col].setText("O");
                    playerButtons[row][col].setOpacity(1.0);
                    playerButtons[row][col + 1].setText("O");
                    playerButtons[row][col + 1].setOpacity(1.0);
                    playerButtons[row][col + 2].setText("O");
                    playerButtons[row][col + 2].setOpacity(1.0);
                } else if (direction == 2) {
                    playerButtons[row][col].setText("O");
                    playerButtons[row][col].setOpacity(1.0);
                    playerButtons[row + 1][col].setText("O");
                    playerButtons[row + 1][col].setOpacity(1.0);
                    playerButtons[row + 2][col].setText("O");
                    playerButtons[row + 2][col].setOpacity(1.0);
                }
                HowManyShipsArePlaced++;
                if (HowManyShipsArePlaced == 3) whatToDoLabel.setText("Pick a cell for you 2-deck ship");
            } else {
                DialogManager.showAlert(Alert.AlertType.ERROR, "Incorrect placement", "Ship can't be placed here, choose another cell", null, null, null);
                return;
            }
        } else if (HowManyShipsArePlaced >= 3 && HowManyShipsArePlaced <= 5) {
            if (player.isAvailable(row, col, 2, direction, playerBoard.getCells())) {
                player.placeShipFx(playerBoard.getCells(), row, col, direction, 2);
                if (direction == 1) {
                    playerButtons[row][col].setText("O");
                    playerButtons[row][col].setOpacity(1.0);
                    playerButtons[row][col + 1].setText("O");
                    playerButtons[row][col + 1].setOpacity(1.0);
                } else if (direction == 2) {
                    playerButtons[row][col].setText("O");
                    playerButtons[row][col].setOpacity(1.0);
                    playerButtons[row + 1][col].setText("O");
                    playerButtons[row + 1][col].setOpacity(1.0);
                }
                HowManyShipsArePlaced++;
                if (HowManyShipsArePlaced == 6) whatToDoLabel.setText("Pick a cell for you 1-deck ship");
            } else {
                DialogManager.showAlert(Alert.AlertType.ERROR, "Incorrect placement", "Ship can't be placed here, choose another cell", null, null, null);
                return;
            }
        } else if (HowManyShipsArePlaced >= 6 && HowManyShipsArePlaced <= 9) {
            if (player.isAvailable(row, col, 1, direction, playerBoard.getCells())) {
                player.placeShipFx(playerBoard.getCells(), row, col, direction, 1);
                playerButtons[row][col].setText("O");
                playerButtons[row][col].setOpacity(1.0);
                HowManyShipsArePlaced++;
                if (HowManyShipsArePlaced == 10) {
                    whatToDoLabel.setOpacity(0.0);
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            playerButtons[i][j].setStyle("-fx-background-color: honeydew;");
                            playerButtons[i][j].setStyle("-fx-border-color: gray");
                            playerButtons[i][j].setMouseTransparent(true);
                        }
                    }
                    gameFuncFx(button, compButtons, playerButtons, shipLabels);
                    return;
                }
            } else {
                DialogManager.showAlert(Alert.AlertType.ERROR, "Incorrect placement", "Ship can't be placed here, choose another cell", null, null, null);
                return;
            }
        }
    }

    /**
     * Handles the gameplay functionality, switching turns between player and computer.
     *
     * @param button The button clicked during gameplay.
     * @param compButtons  A two-dimensional array of buttons representing the computer grid.
     * @param playerButtons A two-dimensional array of buttons representing the player's grid.
     * @param shipLabels A list of labels that are updated to show the status of ships.
     */
    private void gameFuncFx(Button button, Button[][] compButtons, Button[][] playerButtons, List<Label> shipLabels) {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Button compButton = compButtons[row][col];
                compButton.setMouseTransparent(false);
                compButton.setOnAction(e -> handleComputerCellClick(compButton, compButtons, playerButtons, shipLabels));
            }
        }
        whatToDoLabel.setText("Choose enemy's cell to shoot");
        whatToDoLabel.setOpacity(1.0);
    }

    /**
     * Handles clicking on a cell in the computer's grid while playing a game.
     * This method is called when a player selects a cell on the computer field to attack.
     * Checks to see if the player hit or missed the ship and makes the turn transition to the computer,
     * if the player's attack did not result in a second move. Also checks the victory conditions for both players after each turn.
     *
     * @param button A button representing the target cell on the computer field.
     * @param compButtons  A two-dimensional array of buttons representing the computer grid.
     * @param playerButtons A two-dimensional array of buttons representing the player's grid.
     * @param shipLabels A list of labels that are updated to show the status of ships.
     */
    private void handleComputerCellClick(Button button, Button[][] compButtons, Button[][] playerButtons, List<Label> shipLabels) {

        boolean flag;
        flag = playerTakeTurnFx(button, compButtons, shipLabels);
        if (computerBoard.isWinCondition()) {
            DialogManager.showAlert(Alert.AlertType.INFORMATION, "Game Over", "Player wins!", playerButtons, compButtons, whatToDoLabel);
            return;
        }

        if (!flag) computerTakeTurnFx(playerButtons, shipLabels);
        if (playerBoard.isWinCondition()) {
            DialogManager.showAlert(Alert.AlertType.INFORMATION, "Game Over", "Computer Win!", playerButtons, compButtons, whatToDoLabel);
            return;
        }
    }

    /**
     * Manages the turn taken by the player, determining hit or miss and handling game status updates.
     *
     * @param button The button representing the cell targeted by the player.
     * @param compButtons Array of buttons representing computer's grid.
     * @param shipLabels List of labels for ship status updates.
     * @return true if a ship is hit, otherwise false.
     */
    private boolean playerTakeTurnFx(Button button, Button[][] compButtons, List<Label> shipLabels) {
        Object userData = button.getUserData();
        if (!(userData instanceof Point2D)) {
            return false;
        }
        Point2D point = (Point2D) userData;
        int row = (int) point.getX();
        int col = (int) point.getY();

        if (!computerBoard.getCell(row, col).isShot()) {
            boolean shotResult = Player.shootAtFx(row, col, button);
            button.setDisable(true);
            if (shotResult) {
                compButtons[row][col].setText("X");
                compButtons[row][col].setOpacity(1.0);

                whatToDoLabel.setText("You hit a ship! Take another shot.");
                whatToDoLabel.setOpacity(1.0);
                playerTakeTurnFx(button, compButtons, shipLabels);
                return true;
            } else {
                compButtons[row][col].setText("O");
                compButtons[row][col].setOpacity(1.0);
                whatToDoLabel.setText("Missed. Now computer's turn.");
                whatToDoLabel.setOpacity(1.0);
                return false;
            }
        } else {
            whatToDoLabel.setText("You hit a ship! Take another shot.");
            if (computerBoard.getCell(row, col).getShip().isSunk()) {
                whatToDoLabel.setText("Ship has been destroyed! Take another shot");
                shipLabels.get(computerBoard.getShips().size() - 1).setOpacity(0.0);
                shipLabels.remove(shipLabels.size() - 1);
                computerBoard.getShips().remove(computerBoard.getCell(row, col).getShip());
            }
            whatToDoLabel.setOpacity(1.0);
            return false;
        }
    }


    /**
     * Manages the computer's turn, determining strategy and executing moves.
     *
     * @param playerButtons Array of buttons representing player's grid.
     * @param shipLabels List of labels for ship status updates.
     */
    private void computerTakeTurnFx(Button[][] playerButtons, List<Label> shipLabels) {
        Random random = new Random();
        boolean shotResult;
        do {
            int votesForRandom = 0;
            int votesForPattern = 0;
            int index = 0;
            for (Ship ship : playerBoard.getShips()) {
                if (!ship.isSunk()) {
                    int vote = random.nextInt(2);
                    String choice;
                    if (vote == 0) {
                        votesForRandom++;
                        choice = "random";
                    } else {
                        votesForPattern++;
                        choice = "pattern";
                    }
                    if (index < shipLabels.size()) {
                        shipLabels.get(index).setText((index + 1) + "-st ship voted for " + choice);
                        shipLabels.get(index).setOpacity(1.0);
                    }
                    index++;
                }
            }

            Strategy strategy;
            if (votesForPattern >= votesForRandom) {
                strategy = new Pattern_Strategy();
            } else {
                strategy = new Random_Strategy();
            }

            // Use RTTI to set the text in the label
            if (strategy instanceof Random_Strategy) {
                tactic.setText("Computer chose Random Strategy");
            } else if (strategy instanceof Pattern_Strategy) {
                tactic.setText("Computer chose Pattern Strategy");
            }
            tactic.setOpacity(1.0);

            Cell targetCell = strategy.chooseCell();
            while (targetCell.isShot()) {
                targetCell = strategy.chooseCell();
            }
            shotResult = Computer.shootAtFx(targetCell, playerButtons);
            if (!shotResult) break;

        } while (true);
    }

}
