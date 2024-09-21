import java.util.List;

public class NormalCab extends Driver {
    NormalCab(String carplate, int eta) {
        super(carplate, eta);
    }

    @Override
    public List<Service> selectServices(Request request) {
        return List.of(new JustRide(), new TakeACab());
    }

    @Override
    public String toString() {
        return super.toString() + " NormalCab";
    }
}
