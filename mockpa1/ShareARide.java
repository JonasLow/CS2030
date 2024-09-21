public class ShareARide extends Service {
    private static final int DISTANCE = 50;
    private static final int BOOKING_FEE = 0;
    private static final int SURCHARGE = 500;

    ShareARide() {
        super(DISTANCE, BOOKING_FEE, SURCHARGE);
    }

    @Override
    public int computeFare(int distance, int pax, int time) {
        int total = super.computeFare(distance, pax, time);
        return total / pax;
    }

    @Override
    public String toString() {
        return "ShareARide";
    }
}
