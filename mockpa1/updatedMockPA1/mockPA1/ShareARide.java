public class ShareARide implements Service {
    private static final int FARE = 50;
    private static final int SURCHARGE = 500;
    private static final int START_TIME = 600;
    private static final int END_TIME = 900;

    ShareARide() {
        
    }

    @Override
    public int computeFare(int distance, int pax, int time) {
        int fare = FARE * distance;
        if (START_TIME <= time && time <= END_TIME) {
            fare += SURCHARGE;
        }
        return fare / pax;
    }

    @Override
    public String toString() {
        return "ShareARide";
    }
}
