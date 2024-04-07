package com.example.hellofx;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private Board playerBoard = Game.playerBoard;
    private Board enemyboard = Game.computerBoard;
    private String name;

    public void takeTurn() {
        Scanner scanner = new Scanner(System.in);
        int row, col;
        boolean shotResult;

        do {
            System.out.println("Input the coordinates of your shot (row and column):");
            row = scanner.nextInt();
            col = scanner.nextInt();

            if (row >= 0 && row <= 9 && col >= 0 && col <= 9) {
                if (!enemyboard.getCell(row, col).isShot()) {
                    shotResult = enemyboard.shootAt(row, col);
                    if (!shotResult) {
                        System.out.println("You missed. Now the computers turn");
                        System.out.println("Enemy board:");
                        Board.drawField(enemyboard.getCells());
                        break;
                    }
                } else {
                    System.out.println("This cell was already shot. Try another one");
                }
            } else {
                System.out.println("Incorrect coordinates have been entered. Try again");
            }
        } while (true);
    }

    public void placeShips(String playerName, List<List<Cell>> cells) {
        int deck = 4;
        int count = 0;
        Scanner scanner = new Scanner(System.in);
        while (deck >= 1) {
            System.out.println();
            System.out.println(playerName + ", please place your " + deck + "-deck ship on the battlefield:");
            System.out.println();

            Board.drawField(cells);

            System.out.println("Please enter OX coordinate:");
            int x = scanner.nextInt();
            System.out.println("Please enter OY coordinate:");
            int y = scanner.nextInt();
            System.out.println("Choose direction:");
            System.out.println("1. Vertical.");
            System.out.println("2. Horizontal.");
            int direction = scanner.nextInt();
            if (!isAvailable(x, y, deck, direction, cells)){
                System.out.println("Wrong coordinates!");
                continue;
            }
            Ship ship = new Ship(deck, playerBoard);
            for (int i = 0; i < deck; i++) {
                if (direction == 1) {
                    Cell targetCell = cells.get(x).get(y + i);
                    targetCell.placeShip(ship);
                    playerBoard.addShip(ship);
                    ship.placeShip(targetCell);

                } else {
                    Cell targetCell = cells.get(x + i).get(y);
                    targetCell.placeShip(ship);
                    playerBoard.addShip(ship);
                    ship.placeShip(targetCell);
                }
            }
            count++;
            if (count == 1) deck--;
            if (count == 3) deck--;
            if (count == 6) deck--;
            if (count == 10) break;
        }
        Board.drawField(cells);
    }

    public static boolean isAvailable(int x, int y, int deck, int rotation, List<List<Cell>> cells) {
        // out of bound check
        if (rotation == 1) {
            if (y + deck > cells.size()) {
                return false;
            }
        }
        if (rotation == 2){
            if (x + deck > cells.get(0).size()){
                return false;
            }
        }

        //neighbours check
        while (deck!=0){
            for (int i = 0; i < deck; i++) {
                int xi = 0;
                int yi = 0;
                if (rotation == 1){
                    yi = i;
                } else{
                    xi = i;
                }
                // check horizontals and verticals
                if (x + 1 + xi < cells.size() && x + 1 + xi >= 0){
                    if (cells.get(x + 1 + xi).get(y + yi).isShipPlaced()){
                        return false;
                    }
                }
                if (x - 1 + xi < cells.size() && x - 1 + xi >= 0){
                    if (cells.get(x - 1 + xi).get(y + yi).isShipPlaced()){
                        return false;
                    }
                }
                if (y + 1 + yi < cells.size() && y + 1 + yi >= 0){
                    if (cells.get(x + xi).get(y + 1 + yi).isShipPlaced()){
                        return false;
                    }
                }
                if (y - 1 + yi < cells.size() && y - 1 + yi >= 0){
                    if (cells.get(x + xi).get(y - 1 + yi).isShipPlaced()){
                        return false;
                    }
                }
                // Checking diagonals
                if (x + 1 + xi < cells.size() && y + 1 + yi < cells.get(0).size() && cells.get(x + 1 + xi).get(y + 1 + yi).isShipPlaced()){
                    return false;
                }
                if (x + 1 + xi < cells.size() && y - 1 + yi >= 0 && cells.get(x + 1 + xi).get(y - 1 + yi).isShipPlaced()){
                    return false;
                }
                if (x - 1 + xi >= 0 && y + 1 + yi < cells.get(0).size() && cells.get(x - 1 + xi).get(y + 1 + yi).isShipPlaced()){
                    return false;
                }
                if (x - 1 + xi >= 0 && y - 1 + yi >= 0 && cells.get(x - 1 + xi).get(y - 1 + yi).isShipPlaced()){
                    return false;
                }
            }
            deck--;
        }
        return true;
    }
    public String getName() {
        return name;
    }

    public static void showPlayerShips() {
        List<Ship> ships = Game.playerBoard.getShips();
        for (Ship ship : ships) {
            System.out.println("Size:" + ship.getSize() + "\nHp:" + ship.getHitPoints() + "\nis Sunk:" + ship.isSunk() + "\n");
        }
    }

}
