package functions.system;

import functions.log.*;
import functions.trig.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SystemFunctionTest {

    private final double eps = 1e-6;

    private final Sin sin = new Sin();
    private final Cos cos = new Cos(sin);
    private final Csc csc = new Csc(sin);

    private final Ln ln = new Ln();
    private final Log2 log2 = new Log2(ln);
    private final Log3 log3 = new Log3(ln);
    private final Log5 log5 = new Log5(ln);

    private final SystemFunction system =
            new SystemFunction(sin, cos, csc, ln, log2, log3, log5);

    @Test
    void testNegativeRange() {
        for (double x = -10; x < 0; x += 0.5) {
            double result = system.calculate(x, eps);
            assertFalse(Double.isNaN(result), "NaN for x=" + x);
        }
    }

    @Test
    void testPositiveRange() {
        for (double x = 0.5; x <= 10; x += 0.5) {
            double result = system.calculate(x, eps);
            assertFalse(Double.isNaN(result), "NaN for x=" + x);
        }
    }

    @Test
    void testBoundaryValue() {
        double result = system.calculate(0.5, eps);
        assertFalse(Double.isNaN(result), "NaN for boundary x=0.5");
    }
}