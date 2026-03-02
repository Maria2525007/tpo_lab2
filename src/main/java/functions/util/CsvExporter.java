package functions.util;

import functions.MathFunction;
import java.io.FileWriter;
import java.io.IOException;

public class CsvExporter {

    public static void export(
            MathFunction function,
            double start,
            double end,
            double step,
            double eps,
            String fileName) throws IOException {

        FileWriter writer = new FileWriter(fileName);
        writer.write("x,result\n");

        for (double x = start; x <= end; x += step) {
            try {
                double value = function.calculate(x, eps);
                writer.write(x + "," + value + "\n");
            } catch (Exception e) {
                writer.write(x + ",undefined\n");
            }
        }

        writer.close();
    }
}