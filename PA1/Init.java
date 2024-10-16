public class Init extends Transaction{
    Init(final Seating plan) {
        super("INIT:", plan, "Initializing");
    }
}