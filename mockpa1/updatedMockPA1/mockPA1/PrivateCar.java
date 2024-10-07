import java.util.List;

public class PrivateCar extends Driver {
    PrivateCar(String carplate, int timeAway) {
        super(carplate, timeAway);
    }

    public List<Service> selectService() {
        return List.of(new JustRide(), new ShareARide());
    }

    public Service chooseService(Request request) {
        if (request.computeFare(new JustRide()) <
            request.computeFare(new ShareARide())) {
            return new JustRide();
        } else {
            return new ShareARide();
        }
    }

    @Override
    public String toString() {
        return super.toString() + "PrivateCar";
    }
}
