import java.util.stream.Stream;

void main() {}

public Transaction process(Stream<Transaction> transactions, Init init) {
    return transactions.reduce(init, (current, request) -> request.transact(current));
}
