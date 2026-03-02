package functions.log;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Log2Test {

    private final Ln ln = new Ln();
    private final Log2 log2 = new Log2(ln);
    private final double eps = 1e-6;

    @Test
    void testPowerOfTwo() {
        assertEquals(3, log2.calculate(8, eps), 1e-4);
    }

    @Test
    void testFraction() {
        assertEquals(-1, log2.calculate(0.5, eps), 1e-4);
    }

    @Test
    void testRange() {
        for (double x = 0.5; x <= 16; x += 0.5) {
            assertEquals(Math.log(x)/Math.log(2),
                    log2.calculate(x, eps), 1e-3);
        }
    }
}