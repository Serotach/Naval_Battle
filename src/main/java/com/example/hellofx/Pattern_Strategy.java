package com.example.hellofx;
import java.util.List;
import java.util.Random;

public class Pattern_Strategy implements Strategy {
    private Board board = Game.playerBoard;
    static Random random = new Random();

    public Cell chooseCell() {
        List<Ship> ships = Game.playerBoard.getShips();
        // Узнаем размер самого большого корабля, который еще не затонул
        int maxSizeOfShip = 1;
        Ship longestShip = null;
        for (Ship ship : ships) {
            if (ship.getSize() > maxSizeOfShip && !ship.isSunk()) {
                maxSizeOfShip = ship.getSize();
                longestShip = ship;
            }
        }
//        System.out.println("longest ship hp:" + longestShip.getHitPoints() + "\nlongest ship size:" + longestShip.getSize());
        // Если остались только однопалубные корабли, то стреляем по стратегии Random_Strategy
        if (maxSizeOfShip == 1) {
            Strategy strategy = new Random_Strategy();
            return strategy.chooseCell();

        // Обрабатываем случаи, когда жив еще хоть какой-то корабль, помимо однопалубных

            //Здесь мы даем компьютеру чит, который в 100% случаев добивает твой раненый корабль
        } else if (longestShip.getHitPoints() != longestShip.getSize()) {
            List<Cell> availableForShoot = longestShip.getCoordinates();
            int choose = random.nextInt(0, availableForShoot.size());
            while (availableForShoot.get(choose).isShot()) {
                choose = random.nextInt(0, availableForShoot.size());
            }
            return availableForShoot.get(choose);
        } else {
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
        System.out.println("Тебя здесь быть не должно...");
        return new Random_Strategy().chooseCell();
    }
}
