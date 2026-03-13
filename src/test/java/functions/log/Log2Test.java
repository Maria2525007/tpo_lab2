package functions.log;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

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
            assertEquals(Math.log(x) / Math.log(2),
                    log2.calculate(x, eps), 1e-3);
        }
    }

    @Test
    void testWithLnStub() {
        Ln lnStub = mock(Ln.class);
        when(lnStub.calculate(anyDouble(), anyDouble())).thenAnswer(inv -> {
            double arg = inv.getArgument(0);
            double[][] table = {
                    {2.0, 0.6931471805599453},
                    {8.0, 2.0794415416798357},
                    {0.5, -0.6931471805599453},
                    {4.0, 1.3862943611198906},
                    {1.0, 0.0}
            };
            for (double[] entry : table) {
                if (Math.abs(arg - entry[0]) < 1e-9) return entry[1];
            }
            throw new IllegalArgumentException("No stub value for ln(" + arg + ")");
        });

        Log2 log2WithStub = new Log2(lnStub);

        assertEquals(3.0, log2WithStub.calculate(8, eps), 1e-6);
        assertEquals(-1.0, log2WithStub.calculate(0.5, eps), 1e-6);
        assertEquals(2.0, log2WithStub.calculate(4, eps), 1e-6);
        assertEquals(0.0, log2WithStub.calculate(1, eps), 1e-6);
    }
}
