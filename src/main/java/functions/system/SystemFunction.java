package functions.system;

import functions.MathFunction;
import functions.trig.*;
import functions.log.*;

public class SystemFunction implements MathFunction {

    private final Sin sin;
    private final Cos cos;
    private final Csc csc;

    private final Ln ln;
    private final Log2 log2;
    private final Log3 log3;
    private final Log5 log5;

    public SystemFunction(
            Sin sin,
            Cos cos,
            Csc csc,
            Ln ln,
            Log2 log2,
            Log3 log3,
            Log5 log5) {

        this.sin = sin;
        this.cos = cos;
        this.csc = csc;
        this.ln = ln;
        this.log2 = log2;
        this.log3 = log3;
        this.log5 = log5;
    }

    @Override
    public double calculate(double x, double eps) {

        if (x <= 0) {
            double cosX = cos.calculate(x, eps);
            double cscX = csc.calculate(x, eps);

            if (cosX == 0 || cscX == 0) {
                return Double.NaN;
            }

            double numerator = Math.pow(cscX * cosX - cosX, 2) / cosX;
            return numerator / (cscX * cscX);

        } else {
            double log2x = log2.calculate(x, eps);
            double log3x = log3.calculate(x, eps);
            double log5x = log5.calculate(x, eps);
            double lnx = ln.calculate(x, eps);

            double safeLnx = lnx + eps;
            double safeLog3x = log3x + eps;

            double part = (1 + log3x) * (log3x / (safeLnx / log5x));

            return Math.pow(part / safeLog3x, 3);
        }
    }
}