package functions.log;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

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
            assertEquals(Math.log(x) / Math.log(5),
                    log5.calculate(x, eps), 1e-3);
        }
    }

    @Test
    void testWithLnStub() {
        Ln lnStub = mock(Ln.class);
        when(lnStub.calculate(anyDouble(), anyDouble())).thenAnswer(inv -> {
            double arg = inv.getArgument(0);
            double[][] table = {
                    {5.0, 1.6094379124341003},
                    {25.0, 3.2188758248682006},
                    {1.0, 0.0},
                    {0.2, -1.6094379124341003}
            };
            for (double[] entry : table) {
                if (Math.abs(arg - entry[0]) < 1e-9) return entry[1];
            }
            throw new IllegalArgumentException("No stub value for ln(" + arg + ")");
        });

        Log5 log5WithStub = new Log5(lnStub);

        assertEquals(2.0, log5WithStub.calculate(25, eps), 1e-6);
        assertEquals(0.0, log5WithStub.calculate(1, eps), 1e-6);
        assertEquals(-1.0, log5WithStub.calculate(0.2, eps), 1e-6);
    }
}
