public class TakeACab implements Service {
    private static final int FARE = 33;
    private static final int BOOKING_FEE = 200;

    TakeACab() {
        
    }

    @Override
    public int computeFare(int distance, int pax, int time) {
        return FARE * distance + BOOKING_FEE;
    }

    @Override
    public String toString() {
        return "TakeACab";
    }
}
