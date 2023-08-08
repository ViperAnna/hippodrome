import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {
    @Test
    public void hippodromeTest() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    new Hippodrome(new ArrayList<>());
                    throw new IllegalArgumentException("Horses cannot be null.");
                }
        );
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void getHorsesTest(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 30 ; i++) {
            horses.add(new Horse(""+i, i, i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    public void moveTest(){
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 50 ; i++) {
            horses.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        for(Horse horse: horses){
            verify(horse).move();
        }
    }

    @Test
    public void getWinnerTest(){
        Horse horse1 = new Horse("Max", 3.0, 5.0);
        Horse horse2 = new Horse("Wax", 3.0, 5.2);
        Horse horse3 = new Horse("Tax", 3.0, 5.3);
        Horse horse4 = new Horse("Lax", 3.0, 5.1);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3,horse4));

        assertSame(horse3, hippodrome.getWinner());
    }
}
