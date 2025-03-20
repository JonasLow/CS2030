import java.util.List;

public class NormalCab extends Driver {
    NormalCab(String carplate, int timeAway) {
        super(carplate, timeAway);
    }

    public List<Service> selectService() {
        return List.of(new JustRide(), new TakeACab());
    }

    public Service chooseService(Request request) {
        if (request.computeFare(new JustRide()) <
            request.computeFare(new TakeACab())) {
            return new JustRide();
        } else {
            return new TakeACab();
        }
    }

    @Override
    public String toString() {
        return super.toString() + "NormalCab";
    }
}
