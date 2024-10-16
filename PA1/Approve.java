public class Approve extends Transaction {
    private final int billing;
    private final Pair<Integer, Integer> booked;

    Approve(Transaction prev, Seating plan, int billing, Pair<Integer, Integer> booked) {
        super("APPROVED:", plan, String.format("%s\nbilled %d; booked %d--%d", 
        prev.getLog(), billing, booked.t(), booked.t() + booked.u() - 1));
        this.billing = billing;
        this.booked = booked;
    }
}