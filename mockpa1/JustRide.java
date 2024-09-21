public class JustRide extends Service {
    private static final int DISTANCE = 22;
    private static final int BOOKING_FEE = 0;
    private static final int SURCHARGE = 500;

    JustRide() {
        super(DISTANCE, BOOKING_FEE, SURCHARGE);
    }

    @Override
    public String toString() {
        return "JustRide";
    }
}
