import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

class Num extends AbstractNum<Integer> {
    private Num(Integer val) {
        super(val);
    }

    public Num(Optional<Integer> opt) {
        super(opt);
    }

    private Num(AbstractNum<Integer> num) {
        super(num.opt);
    }

    static Num zero() {
        return new Num(AbstractNum.zero());
    }

    static Num one() {
        return zero().succ();
    }

    static Num of(int i) {
        if (AbstractNum.valid.test(i)) {
            return new Num(i);
        }
        return new Num(Optional.empty());
    }

    public Num succ() {
        if (this.isValid()) {
            return new Num(this.map(AbstractNum.s));
        } else {
            return this;
        }
    }

    Num add(Num n) {
        if (n.isValid() && this.isValid()) {
            Num count = zero();
            Num sum = this;
            while (!count.equals(n)) {
                sum = sum.succ();
                count = count.succ();
            }
            return sum;
        } else {
            return new Num(Optional.empty());
        }
    }

    Num sub(Num n) {
        if (n.isValid() && this.isValid()) {
            Num negatedN = new Num(n.opt.map(AbstractNum.n));
            Num result = negatedN.add(this);
            return new Num(result.filter(AbstractNum.valid));
        }
        return new Num(Optional.empty());
    }

    public Num mul(Num num) {
        if (this.isValid() && num.isValid()) {
            Num target = Num.one();
            Num result = this;
            if (this.equals(Num.zero()) || num.equals(Num.zero())) {
                return Num.zero();
            }
            while (! target.equals(num)) {
                result = result.add(this);
                target = target.succ();
            }
            return result;
        }
        return new Num(Optional.empty());
    }

    Num map(Function<Integer, Integer> mapper) {
        return new Num(this.opt.map(x -> mapper.apply(x)));
    }

    Num filter(Predicate<Integer> pred) {
        return new Num(this.opt.filter(x -> pred.test(x)));
    }
}
