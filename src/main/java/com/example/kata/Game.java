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

    int currentFrameIndex;

    int score = 0;

    public Game() {
        IntStream.range(0, TOTAL_FRAMES).forEach(i -> frames[i] = new Frame());
    }

    public void roll(int pinsDown) {
        if (lastFrameWasStrike()) {
            if (lastFrame2WasStrikeToo()) {
                if (currentFrame().isFirstTry()) {
                    score += pinsDown;
                }
            }
            score += pinsDown;
        }
        if (lastFrameWasSpare() && currentFrame().isFirstTry()) {
            score += pinsDown;
        }

        currentFrame().roll(pinsDown);
        score += pinsDown;

        advanceFrame();
    }

    private Frame currentFrame() {
        return frames[currentFrameIndex];
    }

    private boolean lastFrameWasSpare() {
        return prevFrame().map(Frame::isSpare).orElse(false);
    }

    private boolean lastFrameWasStrike() {
        return prevFrame().map(Frame::isStrike).orElse(false);
    }

    private boolean lastFrame2WasStrikeToo() {
        return prevFrame(currentFrameIndex - 1).map(Frame::isStrike).orElse(false);
    }

    private void advanceFrame() {
        if (currentFrame().isStrike() || currentFrame().tries == 2) {
            currentFrameIndex++;
        }
    }

    private Optional<Frame> prevFrame() {
        return prevFrame(currentFrameIndex);
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

        int tries;

        void roll(int pinsDown) {
            this.pinsDown += pinsDown;
            if (tries == 0 || pinsDown < TOTAL_PINS) {
                tries++;
            }
        }

        boolean isFirstTry() {
            return tries == 0;
        }

        boolean isSpare() {
            return pinsDown == TOTAL_PINS && tries == 2;
        }

        boolean isStrike() {
            return pinsDown == TOTAL_PINS && tries == 1;
        }
    }

}
