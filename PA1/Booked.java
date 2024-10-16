public class Booked implements Seat {
    public boolean isBooked() {
        return true;
    }

    @Override
    public String toString() {
        return "B";
    }
}