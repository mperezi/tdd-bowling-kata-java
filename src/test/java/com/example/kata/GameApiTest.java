package com.example.kata;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameApiTest {

    @Test
    void shouldNotAllowMoreThan10PinsDownInOneRoll() {
        var game = new Game();

        assertThatThrownBy(() -> {
            game.roll(11);
        }).isNotNull();
    }

    @Test
    void shouldNotAllowMoreThan10PinsDownInTwoRolls() {
        var game = new Game();

        assertThatThrownBy(() -> {
            game.roll(7);
            game.roll(4);
        }).isNotNull();
    }

    @Test
    void shouldNotAllowPlayingMoreThan10Frames() {
        var game = new Game();

        assertThatThrownBy(() -> {
            IntStream.rangeClosed(1, 12).forEach(v -> game.roll(10));
            game.roll(1);
        }).isInstanceOf(GameOverException.class);
    }
}