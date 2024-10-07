public class SolidCuboid extends Cuboid implements Solid {
    private final SolidImpl solidImpl;

    SolidCuboid(double height, double width, double length, double density) {
        super(height, width, length);
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
