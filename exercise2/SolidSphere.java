public class SolidSphere extends Sphere implements Solid {
    private final SolidImpl solidImpl;

    SolidSphere(double radius, double density) {
        super(radius);
        this.solidImpl = new SolidImpl(this, density);
    }

    public double mass() {
        return this.solidImpl.mass();
    }

    @Override
    public String toString() {
        return "solid-" + super.toString() +
            String.format(" with a mass of %.2f", this.mass());
    }
}
