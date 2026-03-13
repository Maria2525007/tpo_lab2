package functions.log;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

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
            assertEquals(Math.log(x) / Math.log(3),
                    log3.calculate(x, eps), 1e-3);
        }
    }

    @Test
    void testWithLnStub() {
        Ln lnStub = mock(Ln.class);
        when(lnStub.calculate(anyDouble(), anyDouble())).thenAnswer(inv -> {
            double arg = inv.getArgument(0);
            double[][] table = {
                    {3.0, 1.0986122886681098},
                    {9.0, 2.1972245773362196},
                    {27.0, 3.295836866004330},
                    {1.0, 0.0}
            };
            for (double[] entry : table) {
                if (Math.abs(arg - entry[0]) < 1e-9) return entry[1];
            }
            throw new IllegalArgumentException("No stub value for ln(" + arg + ")");
        });

        Log3 log3WithStub = new Log3(lnStub);

        assertEquals(2.0, log3WithStub.calculate(9, eps), 1e-6);
        assertEquals(3.0, log3WithStub.calculate(27, eps), 1e-6);
        assertEquals(0.0, log3WithStub.calculate(1, eps), 1e-6);
    }
}
