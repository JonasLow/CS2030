import java.util.function.Supplier;

public class DefaultServiceTime implements Supplier<Double> {
    public Double get() {
        return 1.0;
    }
}
