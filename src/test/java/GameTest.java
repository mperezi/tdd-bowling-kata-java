import com.example.kata.Game;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    @Test
    public void testWorstPlayer() {
        var game = new Game();

        IntStream.rangeClosed(1, 20). forEach(v -> game.roll(0));

        assertThat(game.score()).isZero();
    }

    @Test
    public void testMostConsistentPlayer() {
        var game = new Game();

        IntStream.rangeClosed(1, 20). forEach(v -> game.roll(1));

        assertThat(game.score()).isEqualTo(20);
    }

    @Test
    public void testSpare() {
        var game = new Game();

        game.roll(7); game.roll(3); // spare
        game.roll(3);

        assertThat(game.score()).isEqualTo(16);
    }

}
