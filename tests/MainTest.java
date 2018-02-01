import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void convertKilometersToMiles() {
        assertEquals(1, Math.round(Main.convertKilometersToMiles(1.60934)*100)/100);
        assertEquals(10, Math.round(Main.convertKilometersToMiles(1.60934*10)*100)/100);
        assertEquals(100, Math.round(Main.convertKilometersToMiles(1.60934*100)*100)/100);
        assertEquals(4999, Math.round(Main.convertKilometersToMiles(1.60934*5000)*100)/100);
        assertEquals(14999, Math.round(Main.convertKilometersToMiles(1.60934*15000)*100)/100);
    }

    @Test
    void convertMilesToKilometers() {
        assertEquals(1, Math.round(Main.convertMilesToKilometers(0.621371)*100)/100);
        assertEquals(10, Math.round(Main.convertMilesToKilometers(0.621371*10)*100)/100);
        assertEquals(100, Math.round(Main.convertMilesToKilometers(0.621371*100)*100)/100);
        assertEquals(5000, Math.round(Main.convertMilesToKilometers(0.621371*5000)*100)/100);
        assertEquals(15000, Math.round(Main.convertMilesToKilometers(0.621371*15000)*100)/100);
    }

    @Test
    void calculateDistance() {
        assertEquals(0, Math.round(Main.calculateDistance(0, 0, 0, 0)));
        assertEquals(111, Math.round(Main.calculateDistance(1, 0, 0, 0)));
        assertEquals(111, Math.round(Main.calculateDistance(0, 1, 0, 0)));
        assertEquals(111, Math.round(Main.calculateDistance(0, 0, 1, 0)));
        assertEquals(111, Math.round(Main.calculateDistance(0, 0, 0, 1)));
        assertEquals(4605, Math.round(Main.calculateDistance(30, 0, 30, 0)));
        assertEquals(8398, Math.round(Main.calculateDistance(0, 60, 0, 60)));
    }
}