package functions.log;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Log3Test {

    private final Ln ln = new Ln();
    private final Log3 log3 = new Log3(ln);
    private final double eps = 1e-6;

    @Test
    void testPowerOfThree() {
        assertEquals(2, log3.calculate(9, eps), 1e-4);
    }

    @Test
    void testRange() {
        for (double x = 0.5; x <= 20; x += 0.5) {
            assertEquals(Math.log(x)/Math.log(3),
                    log3.calculate(x, eps), 1e-3);
        }
    }
}