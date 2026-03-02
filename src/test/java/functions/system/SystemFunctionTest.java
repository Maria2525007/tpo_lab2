package functions.system;

import functions.trig.*;
import functions.log.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SystemFunctionTest {

    double eps = 1e-6;

    Sin sin = new Sin();
    Cos cos = new Cos(sin);
    Csc csc = new Csc(sin);

    Ln ln = new Ln();
    Log2 log2 = new Log2(ln);
    Log3 log3 = new Log3(ln);
    Log5 log5 = new Log5(ln);

    SystemFunction system =
            new SystemFunction(sin, cos, csc, ln, log2, log3, log5);

    @Test
    void testPositiveValue() {
        assertDoesNotThrow(() -> system.calculate(2, eps));
    }

    @Test
    void testNegativeValue() {
        assertDoesNotThrow(() -> system.calculate(-2, eps));
    }

    @Test
    void testZero() {
        assertThrows(Exception.class,
                () -> system.calculate(0, eps));
    }
}