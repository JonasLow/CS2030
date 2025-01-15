import java.util.function.Supplier;

class Customer {
    private final int identifier;
    private final double arrivalTime;
    private final Supplier<Double> serviceTimeSupplier;

    Customer(int identifier, double arrivalTime, Supplier<Double> serviceTimeSupplier) {
        this.identifier = identifier;
        this.arrivalTime = arrivalTime;
        this.serviceTimeSupplier = serviceTimeSupplier;
    }

    Customer(int identifier, double arrivalTime) {
        this.identifier = identifier;
        this.arrivalTime = arrivalTime;
        this.serviceTimeSupplier = () -> 1.0;
    }

    Supplier<Double> getServiceTime() {
        return this.serviceTimeSupplier;
    }

    public String toString() {
        return "customer " + this.identifier;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public int compare(Customer other) {
        return Integer.compare(this.identifier, other.identifier);
    }
}
