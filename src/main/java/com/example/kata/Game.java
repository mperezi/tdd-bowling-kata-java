package com.example.kata;

/**
 * Created by colm on 3/21/17.
 */
public class Game {

    int score = 0;

    int currentTry = 1;

    int lastFrame2PinsDown;

    int lastFrame2Tries;

    int lastFramePinsDown;

    int lastFrameTries;

    int currentFramePinsDown;

    public void roll(int pinsDown) {
        if (lastFrameWasStrike()) {
            if (lastFrame2WasStrikeToo()) {
                if (currentTry == 1) {
                    score += pinsDown;
                }
            } else {
                score += pinsDown;
            }
        }
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

    private boolean lastFrameWasStrike() {
        return lastFramePinsDown == 10 && lastFrameTries == 1;
    }

    private boolean lastFrame2WasStrikeToo() {
        return lastFrame2PinsDown == 10 && lastFrameTries == 1;
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

    private void resetFrame(int tries) {
        currentTry = 1;
        lastFrame2PinsDown = lastFramePinsDown;
        lastFramePinsDown = currentFramePinsDown;
        lastFrame2Tries = lastFrameTries;
        lastFrameTries = tries;
        currentFramePinsDown = 0;
    }

    public int score() {
        return score;
    }
}
