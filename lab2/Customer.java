public class Customer {
    private final int ident;
    private final double arrTime;

    Customer(int ident, double arrTime) {
        this.ident = ident;
        this.arrTime = arrTime;
    }

    public boolean canBeServed(double time) {
        return (this.arrTime >= time);
    }

    public double serveTill(double time) {
        return this.arrTime + time;
    }

    public String toString() {
        return "customer " + this.ident;
    }
}
