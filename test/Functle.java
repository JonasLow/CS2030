import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

// https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html
// Movable<T> is a mutable object which doesn't return itself
// We do not need to keep track of Terrapin (or any Movable) *except for Lvl 5
// As a reminder, consumer accepts an argument and returns no result (i.e void return)

class Functle<T extends Movable<T>> {
    // <T extends Movable<T>> is necessary as Functle only deals with generic objects that are Movable
    // The reason this matters is that Functle's methods rely on Movable methods
    // I said to someone that it was implements but apparantly Java generics doesn't work that way - oops mb
    private final Consumer<T> commands;
    private final Consumer<T> reverseCommands;

    private Functle(Consumer<T> initialCommand,
    Consumer<T> reverseCommands) {
        this.commands = initialCommand;
        this.reverseCommands = reverseCommands;
    }

    public static <T extends Movable<T>> Functle<T> of() {
        return new Functle<>(movable -> {}, movable -> {}); // Blank Command
    }

    // Level 1
    // As mentioned, we make use of the methods explicitly defined in Movable interface (NOT TERRAPIN)
    // Terrapin should only be used as a reference checker against the test case
    // In fact, Terrapin should not be used ANYWHERE in this file
    public Functle<T> forward(int steps) {
        return new Functle<>(
            commands.andThen(movable -> movable.moveForward(steps)),
            movable -> { 
                movable.moveForward(steps * -1);
                reverseCommands.accept(movable);
            }
        );
    }

    public Functle<T> left(int theta) {
        return new Functle<>(
            commands.andThen(movable -> movable.turnLeft(theta)),
            movable -> {
                movable.turnLeft(-theta);
                reverseCommands.accept(movable); 
            }
        );
    }
        
    public void run(T movable) {
        commands.accept(movable);
    }

    // Level 2
    // Fortunately, forward(negative steps) and left(negative theta) is handled by the implementation of T object
    // You can cross-reference Terrapin.java - moveForward() uses addition and turnLeft() uses mod %
    public Functle<T> backward(int steps) {
        return this.forward(steps * -1);
    }

    public Functle<T> right(int theta) {
        return this.left(theta * -1);
    }

    // Level 3
    // I could not think of a better way to reverse the previous commands other than to keep track using
    // a seperate consumer. Luckily, it was mentioned that calling reverse() more than once does nothing,
    // hence, we can safely throw the reverse consumer afterwards
    public Functle<T> reverse() {
        return new Functle<>(
            commands.andThen(reverseCommands), 
            movable -> {}
        );
    }

    // Level 4
    // Consumer allows you chain multiple Consumers together
    // Take note of the order for reversed Consumers
    public Functle<T> andThen(Functle<T> nextFunctle) {
        return new Functle<>(
            commands.andThen(nextFunctle.commands),
            nextFunctle.reverseCommands.andThen(reverseCommands)
        );
    }
    
    // Looping is simply chaining multiple commands together
    public Functle<T> loop(int n) {
        return new Functle<>(
            Stream.iterate(commands, x -> x.andThen(this.commands))
                  .limit(n)
                  .reduce(movable -> {}, (x, y) -> y), 
            Stream.iterate(reverseCommands, x -> x.andThen(this.reverseCommands))
                  .limit(n)
                  .reduce(movable -> {}, (x, y) -> y)
        );
    }

    // Level 5
    // This will be a bit confusing so bear with me
    // This level is essentially asking you how many times the commands have to loop
    // until the T object can return to its starting position - if it does not reach
    // with just loop(), it reverses itself then adds another loop

    // Terrapin @ (0, 0) faces 0                     |     
    // Terrapin @ (100, 0) faces 0                   |   
    // Terrapin @ (100, 0) faces 3              <    |    loop(1) + reverse()
    // Terrapin @ (100, 0) faces 0                   | 
    // Terrapin @ (0, 0) faces 0                     | 

    // Terrapin @ (0, 0) faces 0                     |
    // Terrapin @ (100, 0) faces 0                   |
    // Terrapin @ (100, 0) faces 3                   |
    // Terrapin @ (100, 100) faces 3                 |
    // Terrapin @ (100, 100) faces 2            <    |    loop(2) + reverse()
    // Terrapin @ (100, 100) faces 3                 |
    // Terrapin @ (100, 0) faces 3                   |
    // Terrapin @ (100, 0) faces 0                   |
    // Terrapin @ (0, 0) faces 0                     |

    // Terrapin @ (0, 0) faces 0                     |
    // Terrapin @ (100, 0) faces 0                   |
    // Terrapin @ (100, 0) faces 3                   |
    // Terrapin @ (100, 100) faces 3                 |
    // Terrapin @ (100, 100) faces 2                 |
    // Terrapin @ (0, 100) faces 2                   |    
    // Terrapin @ (0, 100) faces 1              <    |    loop(3) + reverse()
    // Terrapin @ (0, 100) faces 2                   |
    // Terrapin @ (100, 100) faces 2                 |
    // Terrapin @ (100, 100) faces 3                 |
    // Terrapin @ (100, 0) faces 3                   |
    // Terrapin @ (100, 0) faces 0                   |
    // Terrapin @ (0, 0) faces 0                     |

    // Terrapin @ (0, 0) faces 0                     |
    // Terrapin @ (100, 0) faces 0                   |
    // Terrapin @ (100, 0) faces 3                   |
    // Terrapin @ (100, 100) faces 3                 |
    // Terrapin @ (100, 100) faces 2                 |
    // Terrapin @ (0, 100) faces 2                   |
    // Terrapin @ (0, 100) faces 1                   |
    // Terrapin @ (0, 0) faces 1                     |
    // Terrapin @ (0, 0) faces 0                <    |    loop(4) so we end here

    public Functle<T> comeHome() {
        return new Functle<>(
            movable -> {
                Stream.iterate(1,
                    x -> {
                        // We need to compare the state of the T object before and after we call loop()
                        // The way we do this is by looking at the Movable's equals() definition
                        // It takes a T supplier, not another T object
                        // We have to do this because we cannot create a new instance of T object manually
                        Functle<T> result  = this.loop(x);
                        Supplier<T> supp = () -> {
                            result.commands.accept(movable);
                            return movable;
                        };

                        // We stop iteration when the starting T is the same as the final T
                        // after calling loop() multiple times
                        if (movable.equals(supp)) {
                            return false;
                        }
                            
                        result.reverseCommands.accept(movable);
                        return true;
                    },
                    x -> x + 1)
                .map(x -> this.loop(x).commands)
                .reduce(this.commands, (x, y) -> y);
            },
            movable -> {}
        );
    }

    
    @Override
    public String toString() {
        return "Functle";
    }
}
