package com.example.kata;

import java.util.Arrays;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

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
        IntStream.range(0, TOTAL_FRAMES - 1).forEach(i -> frames[i] = new Frame(i + 1));
        frames[TOTAL_FRAMES - 1] = new LastFrame();
    }

    public void roll(int pinsDown) {
        validateGameStatus();

        applyBonusStrike(pinsDown);
        applyBonusSpare(pinsDown);

        currentFrame().roll(pinsDown);

        advanceFrame();
    }

    public int score() {
        return Arrays.stream(frames).mapToInt(Frame::getValue).sum();
    }

    private void validateGameStatus() {
        if (currentFrameIndex >= TOTAL_FRAMES) {
            throw new GameOverException();
        }
    }

    private void applyBonusStrike(int pinsDown) {
        if (prevFrame().isStrike()) {
            // strike bonus only applies to next two rolls
            if (currentFrame().tries() < 2) {
                prevFrame().bonus += pinsDown;
            }
            if (prevFrame(2).isStrike() && currentFrame().isFirstTry()) {
                prevFrame(2).bonus += pinsDown;
            }
        }
    }

    private void applyBonusSpare(int pinsDown) {
        // spare bonus only applies to next roll
        if (prevFrame().isSpare() && currentFrame().isFirstTry()) {
            prevFrame().bonus += pinsDown;
        }
    }

    private Frame currentFrame() {
        return frames[currentFrameIndex];
    }

    private Frame prevFrame() {
        return prevFrame(1);
    }

    private Frame prevFrame(int framesBack) {
        int frameIndex = currentFrameIndex - framesBack;
        return frameIndex >= 0
                ? frames[frameIndex]
                : INVALID_FRAME;
    }

    private void advanceFrame() {
        if (currentFrame().isFinished()) {
            currentFrameIndex++;
        }
    }

    @Override
    public String toString() {
        return Arrays.stream(frames).map(Frame::toString).collect(joining(" "));
    }
}
