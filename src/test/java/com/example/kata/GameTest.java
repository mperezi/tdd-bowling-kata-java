package com.example.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

public class GameTest {

    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    public void testWorstPlayer() {
        IntStream.rangeClosed(1, 20). forEach(v -> game.roll(0));

        assertThat(game.score()).isZero();
    }

    @Test
    public void testMostConsistentPlayer() {
        IntStream.rangeClosed(1, 20). forEach(v -> game.roll(1));

        assertThat(game.score()).isEqualTo(20);
    }

    @Test
    public void testSpare() {
        game.roll(7); game.roll(3); // spare
        game.roll(3);

        assertThat(game.score()).isEqualTo(16);
    }

    @Test
    public void testStrike() {
        game.roll(10); // strike
        game.roll(3);
        game.roll(3);

        assertThat(game.score()).isEqualTo(22);
    }

    @Test
    public void testDoubleStrike() {
        game.roll(10); // strike
        game.roll(10); // double strike
        game.roll(3);
        game.roll(3);

        assertThat(game.score()).isEqualTo(45);
    }

    @Test
    public void testSpareAndThenStrike() {
        game.roll(7); game.roll(3); // spare
        game.roll(10); // strike
        game.roll(3);
        game.roll(3);

        assertThat(game.score()).isEqualTo(42);
    }

    @Test
    public void testPerfectGame() {
        IntStream.rangeClosed(1, 12). forEach(v -> game.roll(10));

        assertThat(game.score()).isEqualTo(300);
    }

    @Nested
    class LastFrameTest {

        @BeforeEach
        void setUp() {
            // advance game to the last frame
            IntStream.rangeClosed(1, 9).forEach(v -> game.roll(10));
        }

        @Test
        public void testSpareEarnsOnlyOneMoreRoll() {
            assertThatCode(() -> {
                game.roll(3);
                game.roll(7); // spare
                game.roll(5); // extra roll
            }).doesNotThrowAnyException();
            assertThatThrownBy(() -> {
                game.roll(1);
            }).isInstanceOf(GameOverException.class);
        }

        @Test
        public void testStrikeEarnsTwoMoreRolls() {
            assertThatCode(() -> {
                game.roll(10); // strike
                game.roll(7);
                game.roll(3);
            }).doesNotThrowAnyException();
            assertThatThrownBy(() -> {
                game.roll(1);
            }).isInstanceOf(GameOverException.class);
        }

        @Test
        public void testNormalRollEndsTheGame() {
            assertThatCode(() -> {
                game.roll(5);
                game.roll(4);
            }).doesNotThrowAnyException();
            assertThatThrownBy(() -> {
                game.roll(1);
            }).isInstanceOf(GameOverException.class);
        }
    }

}
