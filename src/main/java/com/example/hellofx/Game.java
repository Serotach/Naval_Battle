package com.example.hellofx;

import java.util.Scanner;

public class Game {
    static String playerName = "Player";
    static Scanner scanner = new Scanner(System.in);
    static Board playerBoard = new Board(10);
    static Board computerBoard = new Board(10);
    static Computer computer = new Computer();
    static Player player = new Player();

    public static void main(String[] args) {
        System.out.println("Please, enter your name:");
        playerName = scanner.nextLine();
        player.placeShips(playerName, playerBoard.getCells());
        computer.placeShips(computerBoard.getCells());

        while (true) {
            player.takeTurn();
//            Player.showPlayerShips();
            if (computerBoard.isWinCondition()) {
                System.out.println("Player win!");
                break;
            }
            computer.takeTurn();
//            Computer.showComputerShips();
            if (playerBoard.isWinCondition()) {
                System.out.println("Computer win!");
                break;
            }
        }
    }
}

