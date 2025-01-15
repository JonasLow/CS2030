import java.util.function.Supplier;

class DefaultServiceTime implements Supplier<Double> {
    public Double get() {
        System.out.println("generating service time..");
        return generateServiceTime();
    }

    Double generateServiceTime() {
        return 1.0;
    }
}