package com.example.kata;

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
}

class LastFrame extends Frame {

    public LastFrame() {
        super(Game.TOTAL_FRAMES);
    }

    @Override
    void roll(int pinsDown) {
        validateRoll(pinsDown);

        this.pinsDown += pinsDown;
        value += pinsDown;
        tries++;
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