public class Available implements Seat {
    public boolean isBooked() {
        return false;
    }

    @Override
    public String toString() {
        return ".";
    }
}