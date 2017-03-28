import com.example.kata.Game;
import org.junit.Assert;
import org.junit.Test;

public class GameTest {

    @Test
    public void testWorstPlayer(){

        Game game = new Game();
        for(int i=0; i<20; i++)
            game.roll(0);

        Assert.assertEquals(game.score(), 0);
    }

}
