package functions.trig;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CosTest {

    private final Sin sin = new Sin();
    private final Cos cos = new Cos(sin);
    private final double eps = 1e-6;

    @Test
    void testZero() {
        assertEquals(1, cos.calculate(0, eps), eps);
    }

    @Test
    void testPi() {
        assertEquals(-1, cos.calculate(Math.PI, eps), 1e-4);
    }

    @Test
    void testPiHalf() {
        assertEquals(0, cos.calculate(Math.PI / 2, eps), 1e-4);
    }

    @Test
    void testTwoPi() {
        assertEquals(1, cos.calculate(2 * Math.PI, eps), 1e-4);
    }

    @Test
    void testNegativeValue() {
        double x = -3;
        assertEquals(Math.cos(x), cos.calculate(x, eps), 1e-4);
    }

    @Test
    void testLargeValue() {
        double x = 50;
        assertEquals(Math.cos(x), cos.calculate(x, eps), 1e-4);
    }

    @Test
    void testRange() {
        for (double x = -10; x <= 10; x += 0.5) {
            double value = cos.calculate(x, eps);
            assertTrue(value <= 1.0001 && value >= -1.0001);
        }
    }
}