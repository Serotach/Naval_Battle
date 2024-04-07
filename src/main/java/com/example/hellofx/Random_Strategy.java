package com.example.hellofx;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Random_Strategy implements Strategy{
    private Board board = Game.playerBoard;

    public Cell chooseCell() {
        List<Cell> availableCells = new ArrayList<>();
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                Cell cell = board.getCell(i, j);
                if (!cell.isShot()) {
                    availableCells.add(cell);
                }
            }
        }
        if (availableCells.isEmpty()) {
            return null;
        }

        Random random = new Random();
        return availableCells.get(random.nextInt(availableCells.size()));
    }
}
