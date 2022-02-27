package com.example.kata;

/**
 * Created by colm on 3/21/17.
 */
public class Game {

    int score = 0;

    int currentTry = 1;

    int lastFramePinsDown;

    int lastFrameTries;

    int currentFramePinsDown;

    public void roll(int pinsDown) {
        if (lastFramePinsDown == 10 &&  lastFrameTries == 2 && currentTry == 1) {
            score += pinsDown;
        }

        score += pinsDown;
        currentFramePinsDown += pinsDown;

        if (currentTry == 1) {
            if (pinsDown < 10) {
                currentTry = 2;
            } else {  // strike
                lastFramePinsDown = currentFramePinsDown;
                currentFramePinsDown = 0;
                lastFrameTries = 1;
            }
        } else {
            currentTry = 1;
            lastFramePinsDown = currentFramePinsDown;
            currentFramePinsDown = 0;
            lastFrameTries = 2;
        }
    }

    public int score() {
        return score;
    }
}
