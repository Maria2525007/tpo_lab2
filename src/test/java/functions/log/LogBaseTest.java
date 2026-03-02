package functions.log;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogBaseTest {

    private final Ln ln = new Ln();
    private final double eps = 1e-6;

    @Test
    void testBase2() {
        LogBase log = new LogBaseImpl(2, ln);
        assertEquals(3, log.calculate(8, eps), 1e-4);
    }

    @Test
    void testBase10() {
        LogBase log = new LogBaseImpl(10, ln);
        assertEquals(2, log.calculate(100, eps), 1e-4);
    }

    @Test
    void testFraction() {
        LogBase log = new LogBaseImpl(2, ln);
        assertEquals(-1, log.calculate(0.5, eps), 1e-4);
    }

    @Test
    void testRange() {
        LogBase log = new LogBaseImpl(3, ln);
        for (double x = 0.5; x <= 20; x += 0.5) {
            assertEquals(Math.log(x)/Math.log(3),
                    log.calculate(x, eps),
                    1e-3);
        }
    }

    @Test
    void testInvalidArgument() {
        LogBase log = new LogBaseImpl(2, ln);
        assertThrows(IllegalArgumentException.class,
                () -> log.calculate(-5, eps));
    }

    @Test
    void testInvalidBase() {
        assertThrows(IllegalArgumentException.class,
                () -> new LogBaseImpl(1, ln));
    }
}