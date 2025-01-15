import java.util.Optional;
import java.util.function.Function;

class State {
    private final Shop shop;
    private final PQ<Event> pq;
    private final String log;
    private final double totalWaitTime;
    private final int numServed;
    private final int numLeft;
    private final int numDone;

    State(PQ<Event> pq, Shop shop) {
        this.pq = pq;
        this.shop = shop;
        this.log = "";
        this.totalWaitTime = 0;
        this.numServed = 0;
        this.numLeft = 0;
        this.numDone = 0;
    }

    private State(PQ<Event> pq, Shop shop, String log, double totalWaitTime,
            int numServed, int numLeft, int numDone) {
        this.pq = pq;
        this.shop = shop;
        this.log = log;
        this.totalWaitTime = totalWaitTime;
        this.numServed = numServed;
        this.numLeft = numLeft;
        this.numDone = numDone;
    }

    public boolean isEmpty() {
        return pq.isEmpty();
    }

    public State next() {
        Optional<Event> currEvent = pq.poll().t(); // current event is taken out
        PQ<Event> nextPQ = pq.poll().u();

        PQ<Event> newPQ = currEvent
                .map(curr -> {
                    // if currEvent is Optional<Event>, process
                    // process currEvent

                    Optional<Event> nextEvent = curr.process(shop); // next event
                    return nextEvent.map(e -> nextPQ.add(e)).orElse(nextPQ);

                })
                // if currEvent is Optional<Empty>, return nextPQ
                .orElse(nextPQ);

        double newTotalWaitTime = currEvent.map(e -> totalWaitTime + e.getWaitTime())
                .orElse(totalWaitTime);
        int newNumServed = currEvent.map(e -> numServed + e.addServed()).orElse(numServed);
        int newNumLeft = currEvent.map(e -> numLeft + e.addLeft()).orElse(numLeft);
        int newNumDone = currEvent.map(e -> numDone + e.addDone()).orElse(numDone);
        String newLog = currEvent
                .map(event -> log + event.toString() + "\n")
                .orElse(log);

        return new State(newPQ, shop, newLog, newTotalWaitTime, newNumServed,
                newNumLeft, newNumDone);
    }

    public String toString() {
        return log.trim();
    }

    public double getTotalWaitTime() {
        return totalWaitTime;
    }

    public int getNumDone() {
        return numDone;
    }

    public int getNumServed() {
        return numServed;
    }

    public int getNumLeft() {
        return numLeft;
    }
}
