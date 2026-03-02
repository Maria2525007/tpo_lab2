package functions.log;

public class LogBaseImpl extends LogBase {

    public LogBaseImpl(double base, Ln ln) {
        super(ln, base);
        if (base <= 0 || base == 1) {
            throw new IllegalArgumentException("Base must be >0 and !=1");
        }
    }
}