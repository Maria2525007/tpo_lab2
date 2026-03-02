package functions.trig;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SinTest {

    private final Sin sin = new Sin();
    private final double eps = 1e-6;

    @Test
    void testZero() {
        assertEquals(0, sin.calculate(0, eps), eps);
    }

    @Test
    void testPiHalf() {
        assertEquals(1, sin.calculate(Math.PI / 2, eps), 1e-3);
    }
}