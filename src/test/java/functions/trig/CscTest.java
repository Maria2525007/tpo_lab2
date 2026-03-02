package functions.trig;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CscTest {

    private final Sin sin = new Sin();
    private final Csc csc = new Csc(sin);
    private final double eps = 1e-6;

    @Test
    void testPiHalf() {
        assertEquals(1, csc.calculate(Math.PI / 2, eps), 1e-4);
    }

    @Test
    void testNegativePiHalf() {
        assertEquals(-1, csc.calculate(-Math.PI / 2, eps), 1e-4);
    }

    @Test
    void testSmallValue() {
        double x = 0.5;
        assertEquals(1 / Math.sin(x), csc.calculate(x, eps), 1e-4);
    }

    @Test
    void testNegativeValue() {
        double x = -2;
        assertEquals(1 / Math.sin(x), csc.calculate(x, eps), 1e-4);
    }

    @Test
    void testLargeValue() {
        double x = 30;
        assertEquals(1 / Math.sin(x), csc.calculate(x, eps), 1e-4);
    }

    @Test
    void testZeroThrows() {
        assertThrows(ArithmeticException.class,
                () -> csc.calculate(0, eps));
    }
}