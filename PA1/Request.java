public class Request extends Transaction {
    private final Pair<Integer, Integer> rowOfSeats;
    private final int billing;
    private final Bank bank;
    
    Request(Seating plan, Pair<Integer,Integer> rowOfSeats, int billing, Bank bank){
        super("REQUEST:", plan, "Requesting");
        this.rowOfSeats = rowOfSeats;
        this.billing = billing;
        this.bank = bank;
    }

    public Transaction transact(Transaction t) {
        Seating currentSeating = t.getPlan();
        if (!currentSeating.isAvailable(this.rowOfSeats)) {
            return t;  
        }

        if (this.bank.canPurchase(this.billing)) {
            Seating updatedSeating = currentSeating.book(this.rowOfSeats);
            return new Approve(t, updatedSeating, billing, this.rowOfSeats);
        } else {
            return new Reject(t, currentSeating, billing);
        }
    }
}