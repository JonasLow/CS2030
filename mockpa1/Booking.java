import java.util.stream.Stream;

public class Booking implements Comparable<Booking> {
    private final Driver driver;
    private final Request request;
    private final Service service;

    Booking(Driver driver, Request request) {
        this.driver = driver;
        this.request = request;
        this.service =
            driver.selectServices(request)
                .stream()
                .reduce((s1, s2)
                            -> request.computeFare(s1) < request.computeFare(s2)
                                   ? s1
                                   : s2)
                .orElseThrow(
                    () -> new IllegalStateException("No services found"));
    }

    Booking(Driver driver, Request request, Service service) {
        this.driver = driver;
        this.request = request;
        this.service = service;
    }

    public int compareTo(Booking other) {
        int costComparison = Double.compare(this.cost(), other.cost());
        if (costComparison != 0) {
            return costComparison;
        }
        return this.driver.compareTo(other.driver);
    }

    public double cost() {
        return this.request.computeFare(this.service) / 100.0;
    }

    @Override
    public String toString() {
        return String.format("$%.2f", this.cost()) + " using " +
            this.driver.toString() + " (" + this.service.toString() + ")";
    }
}
