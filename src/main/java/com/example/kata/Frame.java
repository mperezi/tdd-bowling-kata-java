package com.example.kata;

import java.util.ArrayList;
import java.util.List;

class Frame {

    final int id;

    List<Integer> pinsDown = new ArrayList<>();

    int bonus;

    public Frame(int id) {
        this.id = id;
    }

    void roll(int pinsDown) {
        validateRoll(pinsDown);
        this.pinsDown.add(pinsDown);
    }

    protected void validateRoll(int pinsDown) {
        int p = totalPinsDown() + pinsDown;
        if (p > Game.TOTAL_PINS) {
            throw new InvalidScoreException("Total number of pins down " + p + " not allowed for frame " + id);
        }
    }

    boolean isFirstTry() {
        return pinsDown.isEmpty();
    }

    boolean isSpare() {
        return totalPinsDown() == Game.TOTAL_PINS && tries() == 2;
    }

    boolean isStrike() {
        return totalPinsDown() == Game.TOTAL_PINS && tries() == 1;
    }

    boolean isFinished() {
        return isStrike() || tries() == 2;
    }

    public int getValue() {
        return totalPinsDown() + bonus;
    }

    public int tries() {
        return pinsDown.size();
    }

    int totalPinsDown() {
        return pinsDown.stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    public String toString() {
        String score = scoreString();
        if (!score.equals("X")) {
            score += "-".repeat(2 - score.length());  // pad missing rolls if any
        }
        return String.format("%d(%s)", id, score);
    }

    String scoreString() {
        var result = new StringBuilder();
        int prev = 0;
        for (int p : pinsDown) {
            if (p == Game.TOTAL_PINS) {
                result.append("X");
            } else if (prev + p == Game.TOTAL_PINS) {
                result.append("/");
            } else {
                result.append(p);
                prev = p;
            }
        }
        return result.toString();
    }
}

class LastFrame extends Frame {

    public LastFrame() {
        super(Game.TOTAL_FRAMES);
    }

    @Override
    boolean isFinished() {
        return tries() == 2 && totalPinsDown() < Game.TOTAL_PINS || tries() == 3;
    }

    protected void validateRoll(int pinsDown) {
        if (pinsDown > Game.TOTAL_PINS) {
            throw new InvalidScoreException("Pins down " + pinsDown + " not allowed for frame " + id);
        }
        if (totalPinsDown() % Game.TOTAL_PINS != 0) {  // not X or XX
            int p = totalPinsDown() + pinsDown;
            if (totalPinsDown() > Game.TOTAL_PINS) {   // strike in first roll
                p -= Game.TOTAL_PINS;
            }
            if (p > Game.TOTAL_PINS) {
                throw new InvalidScoreException("Total pins down " + p + " not allowed for frame " + id);
            }
        }
    }

    @Override
    public String toString() {
        String score = scoreString();
        if (!score.matches("\\d{2}")) {
            score += "-".repeat(3 - score.length());  // pad missing rolls if any
        }
        return String.format("%d(%s)", id, score);
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