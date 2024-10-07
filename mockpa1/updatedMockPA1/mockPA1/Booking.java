import java.util.List;
import java.util.stream.Stream;

class Booking implements Comparable<Booking> {
    private final Driver driver;
    private final Request request;
    private final Service service;

    Booking(Driver driver, Request request) {
        this.driver = driver;
        this.request = request;
        this.service = this.driver.chooseService(this.request);
    }

    Booking(Driver driver, Request request, Service service) {
        this.driver = driver;
        this.request = request;
        this.service = service;
    }

    @Override
    public int compareTo(Booking other) {
        int c1 = this.request.computeFare(this.service);
        int c2 = other.request.computeFare(other.service);

        if (c1 != c2) {
            return Integer.compare(c1, c2);
        }
        return this.driver.compare(other.driver);
    }

    @Override
    public String toString() {
        return String.format("$%.2f using %s (%s)",
                             this.request.computeFare(this.service) / 100.0,
                             this.driver.toString(), this.service.toString());
    }
}
