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

            double numerator =
                    Math.pow((csc.calculate(x, eps) * cos.calculate(x, eps)
                            - cos.calculate(x, eps)), 2)
                            / cos.calculate(x, eps);

            return numerator / Math.pow(csc.calculate(x, eps), 2);

        } else {

            double part =
                    ((log2.calculate(x, eps) / log2.calculate(x, eps))
                            + log3.calculate(x, eps))
                            *
                            (log3.calculate(x, eps)
                                    /
                                    (ln.calculate(x, eps)
                                            / log5.calculate(x, eps)));

            return Math.pow(part / log3.calculate(x, eps), 3);
        }
    }
}