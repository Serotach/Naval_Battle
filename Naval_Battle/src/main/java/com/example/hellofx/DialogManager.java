package com.example.hellofx;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

/**
 * A utility class for displaying alert dialogs in the application.
 * It provides a centralized way to handle alerts and manage interface interactivity during alerts.
 * Also, it's an example of using generics in my own classes
 */
public class DialogManager {
    /**
     * Displays an alert dialog with the specified parameters and modifies the GUI elements' interactivity.
     * It sets the text of a label, if provided, and makes button grids mouse-transparent during the alert,
     * preventing user interaction.
     *
     * @param alertType The type of the alert (e.g., INFORMATION, ERROR).
     * @param title The title of the alert dialog.
     * @param message The message to be displayed in the alert and optionally in a label.
     * @param playerButtons A 2D array of player buttons that should be made non-interactive.
     * @param compButtons A 2D array of computer buttons that should be made non-interactive.
     * @param whatToDoLabel An optional label where the alert message can also be displayed.
     */
    public static void showAlert(Alert.AlertType alertType, String title, String message, Button[][] playerButtons, Button[][] compButtons, Label whatToDoLabel) {
        // Display the message in a provided label
        if (whatToDoLabel != null) {
            whatToDoLabel.setText(message);
        }

        // Make player buttons non-interactive if specified
        if (playerButtons != null) {
            setMouseTransparent(playerButtons, true);
        }

        // Make computer buttons non-interactive if specified
        if (compButtons != null) {
            setMouseTransparent(compButtons, true);
        }

        // Create and display the alert dialog
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Sets the mouse transparency of a 2D array of buttons. This method is used to disable
     * or enable user interactions with the button grid.
     *
     * @param buttons A 2D array of buttons whose interactivity is to be modified.
     * @param value If true, buttons will be non-interactive; if false, interactive.
     */
    private static void setMouseTransparent(Button[][] buttons, boolean value) {
        for (Button[] buttonRow : buttons) {
            for (Button b : buttonRow) {
                b.setMouseTransparent(value);
            }
        }
    }
}