package functions;

import functions.trig.*;
import functions.log.*;
import functions.system.SystemFunction;
import functions.util.CsvExporter;

public class Main {

    public static void main(String[] args) {

        double eps = 1e-6;

        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Csc csc = new Csc(sin);

        Ln ln = new Ln();
        Log2 log2 = new Log2(ln);
        Log3 log3 = new Log3(ln);
        Log5 log5 = new Log5(ln);

        SystemFunction system = new SystemFunction(
                sin, cos, csc,
                ln, log2, log3, log5
        );

        double[] testValues = {-2, -1, -0.5, 0.5, 2, 3};

        for (double x : testValues) {
            try {
                double result = system.calculate(x, eps);
                System.out.println("x = " + x + " → " + result);
            } catch (Exception e) {
                System.out.println("x = " + x + " → undefined");
            }
        }

        try {
            CsvExporter.export(system,
                    -3,
                    3,
                    0.1,
                    eps,
                    "output.csv");

            System.out.println("CSV файл создан.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}