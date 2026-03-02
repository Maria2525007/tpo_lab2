package functions.util;

import functions.MathFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CsvExporterTest {

    private final double eps = 1e-6;

    @Test
    void exportWritesHeaderAndData(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("out.csv");
        MathFunction identity = (x, e) -> x;

        CsvExporter.export(identity, 0, 2, 1, eps, file.toString());

        String content = Files.readString(file);
        assertTrue(content.startsWith("x,result\n"));
        assertTrue(content.contains("0.0,0.0"));
        assertTrue(content.contains("1.0,1.0"));
        assertTrue(content.contains("2.0,2.0"));
    }

    @Test
    void exportWritesUndefinedWhenFunctionThrows(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("out.csv");
        MathFunction throwing = (x, e) -> {
            throw new IllegalArgumentException();
        };

        CsvExporter.export(throwing, 1, 3, 1, eps, file.toString());

        String content = Files.readString(file);
        assertTrue(content.contains("1.0,undefined"));
        assertTrue(content.contains("2.0,undefined"));
        assertTrue(content.contains("3.0,undefined"));
    }

    @Test
    void exportRespectsStartEndStep(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("out.csv");
        MathFunction identity = (x, e) -> x;

        CsvExporter.export(identity, -1, 1, 0.5, eps, file.toString());

        String content = Files.readString(file);
        String[] lines = content.trim().split("\n");
        assertEquals(6, lines.length);
        assertEquals("x,result", lines[0]);
    }
}
