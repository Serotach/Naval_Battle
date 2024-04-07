package com.example.hellofx;

import java.util.List;
import java.util.Random;

public class Computer extends Player {

    private Board playerBoard = Game.computerBoard;
    private Board computerBoard = Game.playerBoard;
    private Random random = new Random();

    public void placeShips(List<List<Cell>> cells) {
        int deck = 4;
        int count = 0;
        while (deck >= 1) {
            int x = random.nextInt(0, 10);
            int y = random.nextInt(0, 10);
            int direction = random.nextInt(1, 3);
            if (!isAvailable(x, y, deck, direction, cells)){
                continue;
            }
            Ship ship = new Ship(deck, playerBoard);
            playerBoard.addShip(ship);
            for (int i = 0; i < deck; i++) {
                if (direction == 1) {
                    Cell targetCell = cells.get(x).get(y + i);
                    targetCell.placeShip(ship);

                } else {
                    Cell targetCell = cells.get(x + i).get(y);
                    targetCell.placeShip(ship);
                }
            }
            count++;
            if (count == 1) deck--;
            if (count == 3) deck--;
            if (count == 6) deck--;
            if (count == 10) break;
        }
        System.out.println("Computer board:");
        Board.drawField(cells);
    }

    public void takeTurn() {
        boolean shotResult;
        do {
            int votesForRandom = 0;
            int votesForPattern = 0;
            for (Ship ship : playerBoard.getShips()) {
                if (!ship.isSunk()) {
                    int vote = random.nextInt(3);
                    if (vote == 1) {
                        votesForRandom++;
                    } else if (vote == 2) {
                        votesForPattern++;
                    }
                }
            }
            if (votesForPattern >= votesForRandom) {
                Pattern_Strategy strategy = new Pattern_Strategy();
                shotResult = computerBoard.shootAt(strategy.chooseCell());
                System.out.println("Computer chose pattern attack option");
                if (!shotResult) break;
            } else {
                Random_Strategy strategy = new Random_Strategy();
                shotResult = computerBoard.shootAt(strategy.chooseCell());
                System.out.println("Computer chose random attack option");
                if (!shotResult) break;
            }
        } while (true);

        System.out.println("Computer has made his turn");
        System.out.println("This is your battlefield");
        Board.drawField(computerBoard.getCells());
    }

    public static void showComputerShips() {
        List<Ship> ships = Game.computerBoard.getShips();
        for (Ship ship : ships) {
            System.out.println("Size:" + ship.getSize() + "\nHp:" + ship.getHitPoints() + "\nis Sunk:" + ship.isSunk() + "\n");
        }
    }
}
