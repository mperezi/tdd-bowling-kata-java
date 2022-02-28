package com.example.kata;

import java.util.Arrays;

class Frame {

    final int id;

    int pinsDown;

    int value;

    int tries;

    int[] rolls = new int[2];

    public Frame(int id) {
        this.id = id;
    }

    void roll(int pinsDown) {
        validateRoll(pinsDown);

        this.pinsDown += pinsDown;
        value += pinsDown;
        rolls[tries] = pinsDown;
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
        String s;
        if (isStrike()) {
            s = "X";
        } else if (isSpare()) {
            s = rolls[0] + "/";
        } else {
            s = "" + rolls[0] + rolls[1];
        }
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
        if (pinsDown > Game.TOTAL_PINS) {
            throw new InvalidScoreException("Pins down " + pinsDown + " not allowed for frame " + id);
        }
        if (this.pinsDown % Game.TOTAL_PINS != 0) {  // not X or XX
            int p = this.pinsDown + pinsDown;
            if (this.pinsDown > Game.TOTAL_PINS) {   // strike in first roll
                p -= Game.TOTAL_PINS;
            }
            if (p > Game.TOTAL_PINS) {
                throw new InvalidScoreException("Total pins down " + p + " not allowed for frame " + id);
            }
        }
    }

    @Override
    public String toString() {
        String s;
        if (Arrays.stream(rolls).allMatch(r -> r == Game.TOTAL_PINS)) {
            s = "XXX";
        } else if (rolls[0] + rolls[1] == 2 * Game.TOTAL_PINS) {
            s = "XX" + rolls[2];
        } else if (rolls[0] == Game.TOTAL_PINS) {
            if (rolls[1] + rolls[2] == Game.TOTAL_PINS) {
                s = "X" + rolls[1] + "/";
            } else {
                s = "X" + rolls[1] + rolls[2];
            }
        } else if (rolls[0] + rolls[1] == Game.TOTAL_PINS) {
            if (rolls[2] == Game.TOTAL_PINS) {
                s = rolls[0] + "/X";
            } else {
                s = rolls[0] + "/" + rolls[2];
            }
        } else {
            s =  "" + rolls[0] + rolls[1];
        }
        return String.format("%d(%s)", id, s);
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