package functions.trig;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinTest {

    private final Sin sin = new Sin();
    private final double eps = 1e-6;

    @Test
    void testZero() {
        assertEquals(0, sin.calculate(0, eps), eps);
    }

    @Test
    void testPiHalf() {
        assertEquals(1, sin.calculate(Math.PI / 2, eps), 1e-4);
    }

    @Test
    void testNegativePiHalf() {
        assertEquals(-1, sin.calculate(-Math.PI / 2, eps), 1e-4);
    }

    @Test
    void testPi() {
        assertEquals(0, sin.calculate(Math.PI, eps), 1e-4);
    }

    @Test
    void testSmallValue() {
        double x = 0.001;
        assertEquals(Math.sin(x), sin.calculate(x, eps), 1e-6);
    }

    @Test
    void testLargeValue() {
        double x = 100;
        assertEquals(Math.sin(x), sin.calculate(x, eps), 1e-4);
    }

    @Test
    void testNegativeValue() {
        double x = -2;
        assertEquals(Math.sin(x), sin.calculate(x, eps), 1e-4);
    }

    @Test
    void testTwoPi() {
        assertEquals(0, sin.calculate(2 * Math.PI, eps), 1e-4);
    }

    @Test
    void testRange() {
        for (double x = -10; x <= 10; x += 0.5) {
            double value = sin.calculate(x, eps);
            assertTrue(value <= 1.0001 && value >= -1.0001);
        }
    }
}