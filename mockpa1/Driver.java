import java.util.List;

public abstract class Driver {
    private final String carplate;
    private final int eta;

    Driver(String carplate, int eta) {
        this.carplate = carplate;
        this.eta = eta;
    }

    public abstract List<Service> selectServices(Request request);

    public int compareTo(Driver other) {
        return Integer.compare(this.eta, other.eta);
    }

    public String toString() {
        return this.carplate + " (" + this.eta + " mins away)";
    }
}
