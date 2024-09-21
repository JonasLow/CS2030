public abstract class Service {
    private final int fare;
    private final int booking;
    private final int surcharge;

    private static final int START_TIME = 600;
    private static final int END_TIME = 900;

    Service(int fare, int booking, int surcharge) {
        this.fare = fare;
        this.booking = booking;
        this.surcharge = surcharge;
    }

    public int computeFare(int distance, int passenger, int time) {
        int total = distance * this.fare + this.booking;
        if (START_TIME <= time && time <= END_TIME) {
            total += this.surcharge;
        }
        return total;
    }
}
