package functions.system;

import functions.log.*;
import functions.trig.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class SystemFunctionTest {

    private final double eps = 1e-6;

    @Nested
    class AllStubsTest {

        @Test
        void trigBranch() {
            Sin sinStub = mock(Sin.class);
            Cos cosStub = mock(Cos.class);
            Csc cscStub = mock(Csc.class);
            Ln lnStub = mock(Ln.class);
            Log2 log2Stub = mock(Log2.class);
            Log3 log3Stub = mock(Log3.class);
            Log5 log5Stub = mock(Log5.class);

            when(cosStub.calculate(eq(-1.0), anyDouble())).thenReturn(0.5403023058681398);
            when(cscStub.calculate(eq(-1.0), anyDouble())).thenReturn(-1.1883951057781212);
            when(cosStub.calculate(eq(-2.0), anyDouble())).thenReturn(-0.4161468365471424);
            when(cscStub.calculate(eq(-2.0), anyDouble())).thenReturn(-1.0997501702946164);
            when(cosStub.calculate(eq(-0.5), anyDouble())).thenReturn(0.8775825618903728);
            when(cscStub.calculate(eq(-0.5), anyDouble())).thenReturn(-2.0858296429334882);

            SystemFunction sys = new SystemFunction(sinStub, cosStub, cscStub,
                    lnStub, log2Stub, log3Stub, log5Stub);

            double cosVal = 0.5403023058681398;
            double cscVal = -1.1883951057781212;
            double expected = Math.pow(cscVal * cosVal - cosVal, 2) / cosVal / (cscVal * cscVal);
            assertEquals(expected, sys.calculate(-1, eps), 1e-6);

            assertFalse(Double.isNaN(sys.calculate(-2, eps)));
            assertFalse(Double.isNaN(sys.calculate(-0.5, eps)));
        }

        @Test
        void logBranch() {
            Sin sinStub = mock(Sin.class);
            Cos cosStub = mock(Cos.class);
            Csc cscStub = mock(Csc.class);
            Ln lnStub = mock(Ln.class);
            Log2 log2Stub = mock(Log2.class);
            Log3 log3Stub = mock(Log3.class);
            Log5 log5Stub = mock(Log5.class);

            when(log2Stub.calculate(eq(2.0), anyDouble())).thenReturn(1.0);
            when(log3Stub.calculate(eq(2.0), anyDouble())).thenReturn(0.6309297535714573);
            when(log5Stub.calculate(eq(2.0), anyDouble())).thenReturn(0.43067655807339306);
            when(lnStub.calculate(eq(2.0), anyDouble())).thenReturn(0.6931471805599453);

            when(log2Stub.calculate(eq(3.0), anyDouble())).thenReturn(1.5849625007211562);
            when(log3Stub.calculate(eq(3.0), anyDouble())).thenReturn(1.0);
            when(log5Stub.calculate(eq(3.0), anyDouble())).thenReturn(0.6826061944859854);
            when(lnStub.calculate(eq(3.0), anyDouble())).thenReturn(1.0986122886681098);

            SystemFunction sys = new SystemFunction(sinStub, cosStub, cscStub,
                    lnStub, log2Stub, log3Stub, log5Stub);

            double result2 = sys.calculate(2, eps);
            assertFalse(Double.isNaN(result2));
            assertTrue(Double.isFinite(result2));

            double result3 = sys.calculate(3, eps);
            assertFalse(Double.isNaN(result3));
            assertTrue(Double.isFinite(result3));
        }

        @Test
        void trigBranchNaNWhenCosZero() {
            Sin sinStub = mock(Sin.class);
            Cos cosStub = mock(Cos.class);
            Csc cscStub = mock(Csc.class);
            Ln lnStub = mock(Ln.class);
            Log2 log2Stub = mock(Log2.class);
            Log3 log3Stub = mock(Log3.class);
            Log5 log5Stub = mock(Log5.class);

            when(cosStub.calculate(eq(-Math.PI / 2), anyDouble())).thenReturn(0.0);
            when(cscStub.calculate(eq(-Math.PI / 2), anyDouble())).thenReturn(-1.0);

            SystemFunction sys = new SystemFunction(sinStub, cosStub, cscStub,
                    lnStub, log2Stub, log3Stub, log5Stub);
            assertTrue(Double.isNaN(sys.calculate(-Math.PI / 2, eps)));
        }
    }

    @Nested
    class RealTrigStubLogTest {

        private final Sin sin = new Sin();
        private final Cos cos = new Cos(sin);
        private final Csc csc = new Csc(sin);

        @Test
        void trigBranchWithRealImplementations() {
            Ln lnStub = mock(Ln.class);
            Log2 log2Stub = mock(Log2.class);
            Log3 log3Stub = mock(Log3.class);
            Log5 log5Stub = mock(Log5.class);

            SystemFunction sys = new SystemFunction(sin, cos, csc,
                    lnStub, log2Stub, log3Stub, log5Stub);

            double result = sys.calculate(-1, eps);
            double cosVal = Math.cos(-1);
            double cscVal = 1.0 / Math.sin(-1);
            double expected = Math.pow(cscVal * cosVal - cosVal, 2) / cosVal / (cscVal * cscVal);
            assertEquals(expected, result, 1e-4);

            assertFalse(Double.isNaN(sys.calculate(-2, eps)));
            assertFalse(Double.isNaN(sys.calculate(-0.5, eps)));
            assertTrue(Double.isNaN(sys.calculate(-Math.PI / 2, eps)));
        }

        @Test
        void trigBranchNegativeRange() {
            Ln lnStub = mock(Ln.class);
            Log2 log2Stub = mock(Log2.class);
            Log3 log3Stub = mock(Log3.class);
            Log5 log5Stub = mock(Log5.class);

            SystemFunction sys = new SystemFunction(sin, cos, csc,
                    lnStub, log2Stub, log3Stub, log5Stub);

            for (double x = -10; x < 0; x += 0.5) {
                double result = sys.calculate(x, eps);
                assertFalse(Double.isNaN(result), "NaN for x=" + x);
            }
        }
    }

    @Nested
    class StubTrigRealLogTest {

        private final Ln ln = new Ln();
        private final Log2 log2 = new Log2(ln);
        private final Log3 log3 = new Log3(ln);
        private final Log5 log5 = new Log5(ln);

        @Test
        void logBranchWithRealImplementations() {
            Sin sinStub = mock(Sin.class);
            Cos cosStub = mock(Cos.class);
            Csc cscStub = mock(Csc.class);

            SystemFunction sys = new SystemFunction(sinStub, cosStub, cscStub,
                    ln, log2, log3, log5);

            double result = sys.calculate(2, eps);
            assertFalse(Double.isNaN(result));
            assertTrue(Double.isFinite(result));

            assertFalse(Double.isNaN(sys.calculate(3, eps)));
            assertFalse(Double.isNaN(sys.calculate(0.5, eps)));
        }

        @Test
        void logBranchPositiveRange() {
            Sin sinStub = mock(Sin.class);
            Cos cosStub = mock(Cos.class);
            Csc cscStub = mock(Csc.class);

            SystemFunction sys = new SystemFunction(sinStub, cosStub, cscStub,
                    ln, log2, log3, log5);

            for (double x = 0.5; x <= 10; x += 0.5) {
                double result = sys.calculate(x, eps);
                assertFalse(Double.isNaN(result), "NaN for x=" + x);
            }
        }

        @Test
        void checkBranch() {
            Cos cosStub = mock(Cos.class);
            Csc cscStub = mock(Csc.class);
            Ln lnStub = mock(Ln.class);
            Log2 log2Stub = mock(Log2.class);
            Log3 log3Stub = mock(Log3.class);
            Log5 log5Stub = mock(Log5.class);

            when(cosStub.calculate(anyDouble(), anyDouble())).thenReturn(0.5);
            when(log3Stub.calculate(anyDouble(), anyDouble())).thenReturn(1.0);

            SystemFunction sys = new SystemFunction(
                    mock(Sin.class), cosStub, cscStub, lnStub, log2Stub, log3Stub, log5Stub);

            sys.calculate(-1, eps);
            verify(cosStub).calculate(eq(-1.0), anyDouble());
            verify(log3Stub, never()).calculate(anyDouble(), anyDouble());

            Mockito.clearInvocations(cosStub, log3Stub);

            sys.calculate(2, eps);
            verify(log3Stub).calculate(eq(2.0), anyDouble());
            verify(cosStub, never()).calculate(anyDouble(), anyDouble());
        }
    }



    @Nested
    class FullIntegrationTest {

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
        void testBoundaryZeroThrows() {
            assertThrows(ArithmeticException.class,
                    () -> system.calculate(0, eps));
        }

        @Test
        void testBoundaryValuePositive() {
            double result = system.calculate(0.5, eps);
            assertFalse(Double.isNaN(result));
        }

        @Test
        void testEquivalenceClassNegative() {
            double result = system.calculate(-1, eps);
            assertFalse(Double.isNaN(result));
            assertTrue(Double.isFinite(result));
        }

        @Test
        void testEquivalenceClassPositive() {
            double result = system.calculate(2, eps);
            assertFalse(Double.isNaN(result));
            assertTrue(Double.isFinite(result));
        }

        @Test
        void testTrigPartReturnsNaNWhenCosZero() {
            double result = system.calculate(-Math.PI / 2, eps);
            assertTrue(Double.isNaN(result));
        }
    }
}
