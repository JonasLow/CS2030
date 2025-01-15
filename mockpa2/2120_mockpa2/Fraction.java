import java.util.Optional;

class Fraction extends AbstractNum<Frac> {
    private Fraction(Optional<Frac> frac) {
        super(frac);
    }

    static Fraction of(Integer num, Integer denom) {
        if (valid.test(num) && valid.test(denom) &&
            !Num.of(denom).equals(Num.zero())) {
            return new Fraction(Optional.<Frac>of(Frac.of(Num.of(num), Num.of(denom))));
        }
        return new Fraction(Optional.empty());
    }

    Fraction add(Fraction frac) {
        if (this.isValid() && frac.isValid()) {
            Optional<Num> a = this.opt.map(x -> x.t());
            Optional<Num> b = this.opt.map(x -> x.u());
            Optional<Num> c = frac.opt.map(x -> x.t());
            Optional<Num> d = frac.opt.map(x -> x.u());

            Optional<Num> ad = a.flatMap(x -> d.map(y -> y.mul(x)));
            Optional<Num> bc = b.flatMap(x -> c.map(y -> y.mul(x)));
            Optional<Num> bd = b.flatMap(x -> d.map(y -> y.mul(x)));
            Optional<Num> adbc = ad.flatMap(x -> bc.map(y -> y.add(x)));

            Optional<Frac> value =
                adbc.flatMap(x -> bd.map(y -> Frac.of(x, y)));
            return new Fraction(value);
        }
        return new Fraction(Optional.empty());
    }

    public Fraction sub(Fraction frac) {
        if (this.isValid() && frac.isValid()) {
            Optional<Num> numerator = this.opt.flatMap(
                x -> frac.opt.map(
                    y -> x.t().mul(y.u()).sub(x.u().mul(y.t()))
                )
            ).filter(x -> x.isValid());
            Optional<Num> denominator = this.opt.flatMap(
                x -> frac.opt.map(
                    y -> x.u().mul(y.u())
                )
            ).filter(x -> x.isValid());
            Optional<Frac> opt = numerator.flatMap(
                x -> denominator.map(
                    y -> Frac.of(x, y)
                )
            );
            return new Fraction(opt);
        }
        return new Fraction(Optional.empty());
    }

    Fraction mul(Fraction frac) {
        if (this.isValid() && frac.isValid()) {
            Optional<Num> a = this.opt.map(x -> x.t());
            Optional<Num> b = this.opt.map(x -> x.u());
            Optional<Num> c = frac.opt.map(x -> x.t());
            Optional<Num> d = frac.opt.map(x -> x.u());

            Optional<Num> ac = a.flatMap(x -> c.map(y -> y.mul(x)));
            Optional<Num> bd = b.flatMap(x -> d.map(y -> y.mul(x)));

            Optional<Frac> value = ac.flatMap(x -> bd.map(y -> Frac.of(x, y)));
            return new Fraction(value);
        }
        return new Fraction(Optional.empty());
    }
}
