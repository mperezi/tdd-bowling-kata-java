package com.example.kata;

class Frame {

    int pinsDown;

    int value;

    int tries;

    void roll(int pinsDown) {
        this.pinsDown += pinsDown;
        value += pinsDown;
        if (tries == 0 || pinsDown < Game.TOTAL_PINS) {
            tries++;
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

class InvalidFrame extends Frame {

    @Override
    boolean isStrike() {
        return false;
    }

    @Override
    boolean isSpare() {
        return false;
    }
}