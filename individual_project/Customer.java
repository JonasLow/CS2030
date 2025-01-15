import java.util.function.Supplier;

public class Customer implements Comparable<Customer> {
    private final int ident;
    private final double arrTime;
    private final Supplier<Double> serviceTimeSupplier;
    private static final Supplier<Double> DEFAULT_SERVICE_TIME = () -> 1.0;

    Customer(int ident, double arrTime) {
        this(ident, arrTime, DEFAULT_SERVICE_TIME);
    }

    Customer(int ident, double arrTime, Supplier<Double> serviceTimeSupplier) {
        this.ident = ident;
        this.arrTime = arrTime;
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    public int getId() {
        return this.ident;
    }

    public double getServiceTime() {
        return this.serviceTimeSupplier.get();
    }

    @Override
    public int compareTo(Customer other) {
        return Double.compare(this.arrTime, other.arrTime);
    }

    @Override
    public String toString() {
        return "customer " + this.ident;
    }
}
