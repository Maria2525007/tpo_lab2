package functions.trig;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

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

    @Test
    void testWithSinStub() {
        Sin sinStub = mock(Sin.class);
        when(sinStub.calculate(anyDouble(), anyDouble())).thenAnswer(inv -> {
            double arg = inv.getArgument(0);
            double[][] table = {
                    {Math.PI / 2, 1.0},
                    {-Math.PI / 2, -1.0},
                    {-1.0, -0.8414709848078965},
                    {-2.0, -0.9092974268256817},
                    {0.5, 0.4794255386042030}
            };
            for (double[] entry : table) {
                if (Math.abs(arg - entry[0]) < 1e-9) return entry[1];
            }
            throw new IllegalArgumentException("No stub value for sin(" + arg + ")");
        });

        Csc cscWithStub = new Csc(sinStub);

        assertEquals(1.0, cscWithStub.calculate(Math.PI / 2, eps), 1e-6);
        assertEquals(-1.0, cscWithStub.calculate(-Math.PI / 2, eps), 1e-6);
        assertEquals(1.0 / -0.8414709848078965, cscWithStub.calculate(-1, eps), 1e-6);
        assertEquals(1.0 / -0.9092974268256817, cscWithStub.calculate(-2, eps), 1e-6);
        assertEquals(1.0 / 0.4794255386042030, cscWithStub.calculate(0.5, eps), 1e-6);
    }
}
