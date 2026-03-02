package functions.log;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LnTest {

    private final Ln ln = new Ln();
    private final double eps = 1e-6;

    @Test
    void testOne() {
        assertEquals(0, ln.calculate(1, eps), eps);
    }

    @Test
    void testE() {
        assertEquals(1, ln.calculate(Math.E, eps), 1e-4);
    }

    @Test
    void testSmallValue() {
        double x = 0.5;
        assertEquals(Math.log(x), ln.calculate(x, eps), 1e-4);
    }

    @Test
    void testLargeValue() {
        double x = 10;
        assertEquals(Math.log(x), ln.calculate(x, eps), 1e-4);
    }

    @Test
    void testVerySmallPositive() {
        double x = 0.001;
        assertEquals(Math.log(x), ln.calculate(x, eps), 1e-3);
    }

    @Test
    void testRange() {
        for (double x = 0.1; x <= 10; x += 0.5) {
            assertEquals(Math.log(x), ln.calculate(x, eps), 1e-3);
        }
    }

    @Test
    void testNegativeThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> ln.calculate(-1, eps));
    }

    @Test
    void testZeroThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> ln.calculate(0, eps));
    }
}