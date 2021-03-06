package com.example.mvvm.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

public class Game {
    private static final String TAG = Game.class.getSimpleName();
    private static final int BOARD_SIZE = 3;

    public Player player1;
    public Player player2;

    public Player currentPlayer = player1;
    public Cell[][] cells;

    public MutableLiveData<Player> winner = new MutableLiveData<>();

    public Game(String player_one, String player_two) {
        cells = new Cell[BOARD_SIZE][BOARD_SIZE];
        player1 = new Player(player_one, "x");
        player2 = new Player(player_two, "o");
        currentPlayer = player1;
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }

    public boolean hasGameEnded() {
        if (hasThreeSameHorizontalCells()
                || hasThreeSameVerticalCells()
                || hasThreeSameDiagonalCells()) {
            winner.setValue(currentPlayer);
            return true;
        }
        if (boardIsFull()) {
            winner.setValue(null);
            return true;
        }
        return false;
    }

    public boolean hasThreeSameHorizontalCells() {
        try {
            for (int i = 0; i < BOARD_SIZE; i++)
                if (areEqual(cells[i][0], cells[i][1], cells[i][2]))
                    return true;
            return false;
        } catch (NullPointerException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return false;
        }
    }

    public boolean hasThreeSameVerticalCells() {
        try {
            for (int i = 0; i < BOARD_SIZE; i++)
                if (areEqual(cells[0][i], cells[1][i], cells[2][i]))
                    return true;
            return false;
        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public boolean hasThreeSameDiagonalCells() {
        try {
            return areEqual(cells[0][0], cells[1][1], cells[2][2]) ||
                    areEqual(cells[0][2], cells[1][1], cells[2][0]);
        } catch (NullPointerException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public boolean boardIsFull() {
        for (Cell[] row : cells)
            for (Cell cell : row)
                if (cell == null || cell.isEmpty())
                    return false;
        return true;
    }

    /**
     * 2 cells are equal if:
     * - Both are non null
     * - Both have non null values
     * - both have equal values
     *
     * @param cells: Cells to check if are equal
     * @return
     */
    public boolean areEqual(Cell... cells) {
        if (cells == null || cells.length == 0)
            return false;

        for (Cell cell : cells)
            if (cell == null || cell.player.value == null || cell.player.value.length() == 0)
                return false;

        Cell comparisonBase = cells[0];
        for (int i = 1; i < cells.length; i++)
            if (!comparisonBase.player.value.equals(cells[i].player.value))
                return false;
        return true;
    }

    public void reset() {
        player1 = null;
        player2 = null;
        currentPlayer = null;
        cells = null;
    }
}
