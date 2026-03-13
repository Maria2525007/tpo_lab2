package functions.trig;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class CosTest {

    private final Sin sin = new Sin();
    private final Cos cos = new Cos(sin);
    private final double eps = 1e-6;

    @Test
    void testZero() {
        assertEquals(1, cos.calculate(0, eps), eps);
    }

    @Test
    void testPi() {
        assertEquals(-1, cos.calculate(Math.PI, eps), 1e-4);
    }

    @Test
    void testPiHalf() {
        assertEquals(0, cos.calculate(Math.PI / 2, eps), 1e-4);
    }

    @Test
    void testTwoPi() {
        assertEquals(1, cos.calculate(2 * Math.PI, eps), 1e-4);
    }

    @Test
    void testNegativeValue() {
        double x = -3;
        assertEquals(Math.cos(x), cos.calculate(x, eps), 1e-4);
    }

    @Test
    void testLargeValue() {
        double x = 50;
        assertEquals(Math.cos(x), cos.calculate(x, eps), 1e-4);
    }

    @Test
    void testRange() {
        for (double x = -10; x <= 10; x += 0.5) {
            double value = cos.calculate(x, eps);
            assertTrue(value <= 1.0001 && value >= -1.0001);
        }
    }

    @Test
    void testWithSinStub() {
        Sin sinStub = mock(Sin.class);
        when(sinStub.calculate(anyDouble(), anyDouble())).thenAnswer(inv -> {
            double arg = inv.getArgument(0);
            double[][] table = {
                    {Math.PI / 2, 1.0},
                    {0.0, 0.0},
                    {-Math.PI / 2, -1.0},
                    {Math.PI / 2 + 1, 0.5403023058681398},
                    {Math.PI / 2 - 2, -0.4161468365471424}
            };
            for (double[] entry : table) {
                if (Math.abs(arg - entry[0]) < 1e-9) return entry[1];
            }
            throw new IllegalArgumentException("No stub value for sin(" + arg + ")");
        });

        Cos cosWithStub = new Cos(sinStub);

        assertEquals(1.0, cosWithStub.calculate(0, eps), 1e-6);
        assertEquals(0.0, cosWithStub.calculate(Math.PI / 2, eps), 1e-6);
        assertEquals(-1.0, cosWithStub.calculate(Math.PI, eps), 1e-6);
        assertEquals(0.5403023058681398, cosWithStub.calculate(-1, eps), 1e-6);
        assertEquals(-0.4161468365471424, cosWithStub.calculate(2, eps), 1e-6);
    }
}
