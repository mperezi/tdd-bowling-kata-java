package com.example.kata;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * Created by colm on 3/21/17.
 */
public class Game {

    private static final int TOTAL_FRAMES = 10;

    private static final int TOTAL_PINS = 10;

    Frame[] frames = new Frame[TOTAL_FRAMES];

    int currentFrameIndex;

    public Game() {
        IntStream.range(0, TOTAL_FRAMES - 1).forEach(i -> frames[i] = new Frame());
        frames[TOTAL_FRAMES - 1] = new LastFrame();
    }

    public void roll(int pinsDown) {
        if (lastFrameWasStrike()) {
            if (lastFrame2WasStrikeToo()) {
                if (currentFrame().isFirstTry()) {
                    prevFrame(currentFrameIndex - 1).ifPresent(applyBonus(pinsDown));
                }
            }
            if (currentFrame().tries < 2) {
                prevFrame().ifPresent(applyBonus(pinsDown));
            }
        }
        if (lastFrameWasSpare() && currentFrame().isFirstTry()) {
            prevFrame().ifPresent(applyBonus(pinsDown));
        }

        currentFrame().roll(pinsDown);

        advanceFrame();
    }

    private static Consumer<Frame> applyBonus(int bonus) {
        return f -> f.value += bonus;
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
        if (currentFrame().isFinished()) {
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
        return Arrays.stream(frames).mapToInt(Frame::getValue).sum();
    }

    private static class Frame {

        int pinsDown;

        int value;

        int tries;

        void roll(int pinsDown) {
            this.pinsDown += pinsDown;
            value += pinsDown;
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

        boolean isFinished() {
            return isStrike() || tries == 2;
        }

        public int getValue() {
            return value;
        }
    }

    private static class LastFrame extends Frame {
        @Override
        void roll(int pinsDown) {
            this.pinsDown += pinsDown;
            value += pinsDown;
            tries++;
        }

        @Override
        boolean isFinished() {
            return tries == 3;
        }
    }

}
