package com.example.kata;

/**
 * Created by colm on 3/21/17.
 */
public class Game {

    int score = 0;

    public void roll(int pinsDown) {
        score += pinsDown;
    }

    public int score() {
        return score;
    }
}
