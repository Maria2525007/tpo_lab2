package functions.log;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Log5Test {

    private final Ln ln = new Ln();
    private final Log5 log5 = new Log5(ln);
    private final double eps = 1e-6;

    @Test
    void testPowerOfFive() {
        assertEquals(2, log5.calculate(25, eps), 1e-4);
    }

    @Test
    void testRange() {
        for (double x = 0.5; x <= 50; x += 0.5) {
            assertEquals(Math.log(x)/Math.log(5),
                    log5.calculate(x, eps), 1e-3);
        }
    }
}