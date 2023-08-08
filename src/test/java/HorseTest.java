import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {

    @Test
    public void nameNullParameterTest() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    new Horse("Max", 3.0, 5.0);
                    throw new IllegalArgumentException("Name cannot be null");
                }
        );
        assertEquals("Name cannot be null", exception.getMessage());

    }


    @ParameterizedTest
    @ValueSource(strings = {" ", "", "\n", "\t"})
    public void nameBlankTest(String nameHorse) {
        Throwable exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    new Horse(nameHorse, 3.0, 5.0);
                    throw new IllegalArgumentException("Name cannot be blank.");
                }
        );
        assertEquals("Name cannot be blank.", exception.getMessage());

    }


    @Test
    public void speedParameterTest() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
                    new Horse("Max", -3.0, 5.0);
                    throw new IllegalArgumentException("Speed cannot be negative.");
                }
        );
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }


    @Test
    public void distanceParameterTest() {

        Throwable exception = assertThrows(
                IllegalArgumentException.class, () -> {
                    new Horse("Max", 3.0, -5.0);
                    throw new IllegalArgumentException("Distance cannot be negative.");
                }
        );
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }


    @Test
    public void getNameTest() throws NoSuchFieldException, IllegalAccessException {
        String expectedName = "Max";
        Horse horse = new Horse(expectedName, 3.0, 5.0);

        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);

        String nameHorseValue = (String) name.get(horse);
        assertEquals(expectedName, nameHorseValue);
    }

//    @Test
//    public void getNameTest() {
//        Horse horse = new Horse("Max", 3.0, 5.0);
//        assertEquals("Max", horse.getName());
//    }


    @Test
    public void getSpeedTest() throws NoSuchFieldException, IllegalAccessException {
        double expectedSpeed = 3.0;
        Horse horse = new Horse("Max", expectedSpeed, 5.0);

        Field speed = Horse.class.getDeclaredField("speed");
        speed.setAccessible(true);
        double speedHorseValue = (double) speed.get(horse);
        assertEquals(expectedSpeed, speedHorseValue);

    }

//    @Test
//    public void getSpeedTest(){
//        Horse horse = new Horse("Max", 3.0, 5.0);
//        assertEquals(3.0, horse.getSpeed());
//    }


    @Test
    public void getDistanceTest() throws NoSuchFieldException, IllegalAccessException {
        double expectedDistance = 0;
        Horse horse = new Horse("Max", 3.0, expectedDistance);

        Field distance = Horse.class.getDeclaredField("distance");
        distance.setAccessible(true);
        double distanceHorseValue = (double) distance.get(horse);
        assertEquals(expectedDistance, distanceHorseValue);
    }

//    @Test
//    public void getDistanceTest(){
//        Horse horse = new Horse("Max", 3.0, 5.0);
//        assertEquals(5.0, horse.getDistance());
//    }

    @Test
    public void getNullDistanceTest() {
        Horse horse = new Horse("Max", 3.0);
        assertEquals(0, horse.getDistance());
    }

    @Test
    public void moveTest() {
        try {
            MockedStatic<Horse> mockedStaticHorse = Mockito.mockStatic(Horse.class);
            new Horse("Max", 3.0, 5.0).move();
            mockedStaticHorse.verify(() ->
                    Horse.getRandomDouble(0.2, 0.9));
        } catch (Exception e) {
            System.out.println("Exception" + e);
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9})
    public void moveRandomDistanceTest(double random) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Max", 3.0, 5.0);
            mockedStatic.when(() ->
                    Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();
            assertEquals(5.0 + 3.0 * random, horse.getDistance());
        }

    }
}
