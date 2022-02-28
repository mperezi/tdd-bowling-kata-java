package com.example.kata;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by colm on 3/21/17.
 */
public class Game {

    private static final Frame INVALID_FRAME = new InvalidFrame();

    static final int TOTAL_FRAMES = 10;

    static final int TOTAL_PINS = 10;

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
                    prevFrame(currentFrameIndex - 1).value += pinsDown;
                }
            }
            if (currentFrame().tries < 2) {
                prevFrame().value += pinsDown;
            }
        }
        if (lastFrameWasSpare() && currentFrame().isFirstTry()) {
            prevFrame().value += pinsDown;
        }

        currentFrame().roll(pinsDown);

        advanceFrame();
    }

    private Frame currentFrame() {
        return frames[currentFrameIndex];
    }

    private boolean lastFrameWasSpare() {
        return prevFrame().isSpare();
    }

    private boolean lastFrameWasStrike() {
        return prevFrame().isStrike();
    }

    private boolean lastFrame2WasStrikeToo() {
        return prevFrame(currentFrameIndex - 1).isStrike();
    }

    private void advanceFrame() {
        if (currentFrame().isFinished()) {
            currentFrameIndex++;
        }
    }

    private Frame prevFrame() {
        return prevFrame(currentFrameIndex);
    }

    private Frame prevFrame(int frameNumber) {
        return frameNumber > 0
                ? frames[frameNumber - 1]
                : INVALID_FRAME;
    }

    public int score() {
        return Arrays.stream(frames).mapToInt(Frame::getValue).sum();
    }

}
