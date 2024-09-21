import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

void main(){};

public List<Booking> findBestBooking(Request r, List<Driver> driverList) {
    return driverList.stream()
        .flatMap(
            d -> d.selectServices(r).stream().map(s -> new Booking(d, r, s)))
        .sorted()
        .toList();
}
