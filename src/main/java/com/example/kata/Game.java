package com.example.kata;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Created by colm on 3/21/17.
 */
public class Game {

    Frame[] frames = new Frame[10];

    int currentFrame;

    int score = 0;

    int currentTry = 1;

    int lastFrame2PinsDown;

    int lastFrame2Tries;

    int lastFramePinsDown;

    int lastFrameTries;

    int currentFramePinsDown;

    public Game() {
        IntStream.range(0, 10).forEach(i -> frames[i] = new Frame());
    }

    public void roll(int pinsDown) {
        if (lastFrameWasStrike()) {
            if (lastFrame2WasStrikeToo()) {
                if (currentTry == 1) {
                    score += pinsDown;
                }
            }
            score += pinsDown;
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

    private Optional<Frame> prevFrame(int frameNumber) {
        return frameNumber > 0
                ? Optional.of(frames[frameNumber - 1])
                : Optional.empty();
    }

    public int score() {
        return score;
    }

    private static class Frame {

        int pinsDown;

        int tries = 1;

        void roll(int pinsDown) {
            this.pinsDown += pinsDown;
            if (tries < 2 && pinsDown < 10) {
                tries++;
            }
        }

        boolean isSpare() {
            return pinsDown == 10 && tries == 2;
        }

        boolean isStrike() {
            return pinsDown == 10 && tries == 1;
        }
    }

}
