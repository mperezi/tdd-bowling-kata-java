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
        if (lastFrameWasSpare() && currentTry == 1) {
            score += pinsDown;
        }

        score += pinsDown;
        currentFramePinsDown += pinsDown;

        advanceFrame(pinsDown);
    }

    private boolean lastFrameWasSpare() {
        return lastFramePinsDown == 10 && lastFrameTries == 2;
    }

    private void advanceFrame(int pinsDown) {
        if (currentTry == 1) {
            if (pinsDown < 10) {
                currentTry = 2;
            } else {  // strike
                resetFrame(1);
            }
        } else {
            resetFrame(2);
        }
    }

    private void resetFrame(int lastFrameTries) {
        currentTry = 1;
        lastFramePinsDown = currentFramePinsDown;
        currentFramePinsDown = 0;
        this.lastFrameTries = lastFrameTries;
    }

    public int score() {
        return score;
    }
}
