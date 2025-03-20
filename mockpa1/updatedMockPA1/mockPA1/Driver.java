import java.util.List;

abstract class Driver {
    private final String carplate;
    private final int timeAway;

    Driver(String carplate, int timeAway) {
        this.carplate = carplate;
        this.timeAway = timeAway;
    }

    abstract List<Service> selectService();

    abstract Service chooseService(Request request);

    public int compare(Driver other) {
        return Integer.compare(this.timeAway, other.timeAway);
    }

    @Override
    public String toString() {
        return String.format("%s (%d mins away) ", this.carplate,
                             this.timeAway);
    }
}
