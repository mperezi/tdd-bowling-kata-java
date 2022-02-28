package com.example.kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameApiTest {

    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void shouldNotAllowMoreThan10PinsDownInOneRoll() {
        assertThatThrownBy(() -> {
            game.roll(11);
        }).isNotNull();
    }

    @Test
    void shouldNotAllowMoreThan10PinsDownInTwoRolls() {
        assertThatThrownBy(() -> {
            game.roll(7);
            game.roll(4);
        }).isNotNull();
    }

    @Test
    void shouldNotAllowPlayingMoreThan10Frames() {
        assertThatThrownBy(() -> {
            IntStream.rangeClosed(1, 12).forEach(v -> game.roll(10));
            game.roll(1);
        }).isInstanceOf(GameOverException.class);
    }

    @Nested
    class GameEndTest {

        @BeforeEach
        void setUp() {
            // advance game to the last frame
            IntStream.rangeClosed(1, 9).forEach(v -> game.roll(10));
        }

        @Test
        void shouldNotAllowMoreThan10PinsDownInOneRoll() {
            assertThatThrownBy(() -> {
                game.roll(11);
            }).isNotNull();
        }

        @Test
        void shouldNotAllowMoreThan10PinsInTwoRolls() {
            assertThatThrownBy(() -> {
                game.roll(7);
                game.roll(4);
            }).isNotNull();
        }

        @Test
        void shouldNotAllowMoreThan10PinsDownAfterSpare() {
            assertThatThrownBy(() -> {
                game.roll(7);
                game.roll(3);
                game.roll(11);
            }).isNotNull();
        }

        @Test
        void shouldNotAllowMoreThan10PinsDownAfterOneStrike() {
            assertThatThrownBy(() -> {
                game.roll(10);
                game.roll(11);
            }).isNotNull();
        }

        @Test
        void shouldNotAllowMoreThan10PinsDownAfterTwoStrikes() {
            assertThatThrownBy(() -> {
                game.roll(10);
                game.roll(10);
                game.roll(11);
            }).isNotNull();
        }
    }

}