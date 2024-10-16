import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Seating {
    private final int capacity;
    private final List<? extends Seat> seats;

    Seating(int capacity) {
        this.capacity = capacity;
        this.seats = IntStream.rangeClosed(1, capacity).mapToObj(x -> new Available()).toList();
    }

    Seating(int capacity, List<? extends Seat> seats, int start, int end) {
        this.capacity = capacity;
        this.seats = IntStream.range(0, capacity)
                    .mapToObj(x -> {
                        if (start <= x && x < end) {
                            return new Booked();
                        } else {
                            return seats.get(x);
                        }
                    })
                    .toList();
    }

    public boolean isAvailable(Pair<Integer,Integer> rowOfSeats) {
        int start = rowOfSeats.t();
        int consecutiveSeats = rowOfSeats.u();
        int end = start + consecutiveSeats;
        if (start < 0 || end > this.capacity || start >= this.capacity || consecutiveSeats <= 0) {
            return false; 
        }
        for (int i = start; i < end; i++) {
            if (this.seats.get(i).isBooked()) {
                return false;
            }
        }
        return true;
    }

    public Seating book(Pair<Integer,Integer> rowOfSeats) {
        if (isAvailable(rowOfSeats)){
            int start = rowOfSeats.t();
            int consecutiveSeats = rowOfSeats.u();
            int end = start + consecutiveSeats;
            return new Seating(this.capacity, this.seats, start, end);
        }
        return this;
    }

    @Override
    public String toString() {
        String output = this.seats.stream().map(x -> x.toString()).reduce("", (x, y) -> x + y);
        return String.format("%s", output);
    }
}