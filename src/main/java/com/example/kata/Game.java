package com.example.kata;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Created by colm on 3/21/17.
 */
public class Game {

    private static final int TOTAL_FRAMES = 10;

    private static final int TOTAL_PINS = 10;

    Frame[] frames = new Frame[TOTAL_FRAMES];

    int currentFrame;

    int score = 0;

    int currentTry = 1;

    int lastFrame2PinsDown;

    int lastFrame2Tries;

    int lastFramePinsDown;

    int lastFrameTries;

    int currentFramePinsDown;

    public Game() {
        IntStream.range(0, TOTAL_FRAMES).forEach(i -> frames[i] = new Frame());
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
        return lastFramePinsDown == TOTAL_PINS && lastFrameTries == 2;
    }

    private boolean lastFrameWasStrike() {
        return lastFramePinsDown == TOTAL_PINS && lastFrameTries == 1;
    }

    private boolean lastFrame2WasStrikeToo() {
        return lastFrame2PinsDown == TOTAL_PINS && lastFrameTries == 1;
    }

    private void advanceFrame(int pinsDown) {
        if (currentTry == 1) {
            if (pinsDown < TOTAL_PINS) {
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
            if (tries < 2 && pinsDown < TOTAL_PINS) {
                tries++;
            }
        }

        boolean isSpare() {
            return pinsDown == TOTAL_PINS && tries == 2;
        }

        boolean isStrike() {
            return pinsDown == TOTAL_PINS && tries == 1;
        }
    }

}
