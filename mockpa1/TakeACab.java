public class TakeACab extends Service {
    private static final int DISTANCE = 33;
    private static final int BOOKING_FEE = 200;
    private static final int SURCHARGE = 0;

    TakeACab() {
        super(DISTANCE, BOOKING_FEE, SURCHARGE);
    }

    @Override
    public String toString() {
        return "TakeACab";
    }
}
