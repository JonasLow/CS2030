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
        return String.format("solid-%s with mass of %.2f", super.toString(),
                             this.mass());
    }
}
