package com.example.mvvm.model;

import static com.example.mvvm.utils.StringUtility.isNullOrEmpty;

public class Cell {
    public Player player;

    public Cell(Player player) {
        this.player = player;
    }

    public boolean isEmpty() {
        return player == null || isNullOrEmpty(player.value);
    }
}
