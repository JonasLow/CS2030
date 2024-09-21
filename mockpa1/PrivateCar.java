import java.util.List;

public class PrivateCar extends Driver {
    PrivateCar(String carplate, int eta) {
        super(carplate, eta);
    }

    @Override
    public List<Service> selectServices(Request request) {
        return List.of(new JustRide(), new ShareARide());
    }

    @Override
    public String toString() {
        return super.toString() + " PrivateCar";
    }
}
