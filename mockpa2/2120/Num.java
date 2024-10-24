import java.util.Optional;

class Num extends AbstractNum<Integer> {
    private Num(Optional<Integer> opt) {
        super(opt);
    }

    private Num(AbstractNum<Integer> num) {
        super(num.opt);
    }

    static Num of(int i) {
        return new Num(valid.test(i) ? Optional.of(i) : Optional.empty());
    }

    Num succ() {
        return new Num(
            this.opt.flatMap(x -> Optional.of(s.apply(x))).filter(valid));
    }

    static Num zero() {
        return new Num(AbstractNum.zero());
    }

    static Num one() {
        return zero().succ();
    }

    public Num add(Num num) {
        return num.opt
            .map(x -> {
                Num temp = this;
                for (int i = 0; i < x; i++) {
                    temp = temp.succ();
                }
                return temp;
            })
            .orElse(new Num(Optional.empty()));
    }

    private Num pred() {
        if (!this.isValid()) {
            return new Num(Optional.empty());
        }
        if (this.equals(Num.zero())) {
            return new Num(Optional.empty());
        }
        Num candidate = Num.zero();
        while (true) {
            Num candidateSucc = candidate.succ();
            if (candidateSucc.equals(this)) {
                return candidate;
            }
            candidate = candidate.succ();
            if (!candidate.isValid()) {
                return new Num(Optional.empty());
            }
        }
    }

    public Num sub(Num num) {
        if (!num.isValid() || !this.isValid()) {
            return new Num(Optional.empty());
        }
        if (num.equals(Num.zero())) {
            return this;
        }
        if (this.equals(num)) {
            return Num.zero();
        }

        Num result = this;
        Num counter = num;
        while (true) {
            if (counter.equals(Num.zero())) {
                return result;
            }
            result = result.pred();
            if (!result.isValid()) {
                return new Num(Optional.empty());
            }
            counter = counter.pred();
            if (!counter.isValid()) {
                return new Num(Optional.empty());
            }
        }
    }

    public Num mul(Num num) {
        if (!num.isValid() || !this.isValid()) {
            return new Num(Optional.empty());
        }
        return num.opt
            .map(x -> {
                if (x == 0) {
                    return Num.zero();
                }
                Num temp = this;
                for (int i = 1; i < x; i++) {
                    temp = this.add(temp);
                }
                return temp;
            })
            .orElse(new Num(Optional.empty()));
    }

    public Num div(Num num) {
        if (!num.isValid() || !num.equals(Num.zero())) {
            return new Num(Optional.empty());
        }
        Num count = Num.zero();
        Num temp = this;
        while (temp.sub(num).isValid()) {
            temp = temp.sub(num);
            count = count.succ();
        }
        return count;
    }

    static Num mod(Num a, Num b) {
        if (!b.isValid() || b.equals(Num.zero())) {
            return new Num(Optional.empty());
        }
        Num remainder = a;
        while (remainder.sub(b).isValid()) {
            remainder = remainder.sub(b);
        }
        return remainder;
    }

    public Num gcd(Num num) {
        Num tempA = this;
        Num tempB = num;
        while (tempB.isValid() && !tempB.equals(Num.zero())) {
            Num remainder = mod(tempA, tempB);
            tempA = tempB;
            tempB = remainder;
        }
        return tempA;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
