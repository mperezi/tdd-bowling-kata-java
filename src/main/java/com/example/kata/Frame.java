package com.example.kata;

import java.util.Arrays;

class Frame {

    final int id;

    int pinsDown;

    int value;

    int tries;

    public Frame(int id) {
        this.id = id;
    }

    void roll(int pinsDown) {
        validateRoll(pinsDown);

        this.pinsDown += pinsDown;
        value += pinsDown;
        if (tries == 0 || pinsDown < Game.TOTAL_PINS) {
            tries++;
        }
    }

    private void validateRoll(int pinsDown) {
        int p = this.pinsDown + pinsDown;
        if (p > Game.TOTAL_PINS) {
            throw new InvalidScoreException("Total number of pins down " + p + " not allowed for frame " + id);
        }
    }

    boolean isFirstTry() {
        return tries == 0;
    }

    boolean isSpare() {
        return pinsDown == Game.TOTAL_PINS && tries == 2;
    }

    boolean isStrike() {
        return pinsDown == Game.TOTAL_PINS && tries == 1;
    }

    boolean isFinished() {
        return isStrike() || tries == 2;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        String s = String.valueOf(pinsDown);
        if (isSpare())  s = "/";
        if (isStrike()) s = "X";
        return String.format("%d(%s)", id, s);
    }
}

class LastFrame extends Frame {

    int[] rolls = new int[3];

    public LastFrame() {
        super(Game.TOTAL_FRAMES);
    }

    @Override
    void roll(int pinsDown) {
        validateRoll(pinsDown);

        this.pinsDown += pinsDown;
        value += pinsDown;
        rolls[tries++] = pinsDown;
    }

    @Override
    boolean isFinished() {
        return tries == 2 && pinsDown < Game.TOTAL_PINS || tries == 3;
    }

    private void validateRoll(int pinsDown) {
        if (this.pinsDown == Game.TOTAL_PINS || this.pinsDown == 2 * Game.TOTAL_PINS) {
            if (pinsDown > Game.TOTAL_PINS) {
                throw new InvalidScoreException("Pins down " + pinsDown + " not allowed for frame " + id);
            }
        } else {
            int p = this.pinsDown + pinsDown;
            if (p > Game.TOTAL_PINS) {
                throw new InvalidScoreException("Total pins down " + p + " not allowed for frame " + id);
            }
        }
    }

    @Override
    public String toString() {
        if (Arrays.stream(rolls).allMatch(r -> r == Game.TOTAL_PINS)) {
            return "XXX";
        } else if (rolls[0] + rolls[1] == 2 * Game.TOTAL_PINS) {
            return "XX" + pinsDown;
        } else if (rolls[0] == Game.TOTAL_PINS) {
            if (rolls[1] + rolls[2] == Game.TOTAL_PINS) {
                return "X/";
            } else {
                return "X" + pinsDown;
            }
        } else if (rolls[0] + rolls[1] == Game.TOTAL_PINS) {
            return "/" + pinsDown;
        } else {
            return String.valueOf(pinsDown);
        }
    }
}

class InvalidFrame extends Frame {

    public InvalidFrame() {
        super(-1);
    }

    @Override
    boolean isStrike() {
        return false;
    }

    @Override
    boolean isSpare() {
        return false;
    }
}