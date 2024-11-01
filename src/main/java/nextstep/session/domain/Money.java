package nextstep.session.domain;

import java.math.BigInteger;
import java.util.Objects;

public class Money {
    public static final Money ZERO = new Money(BigInteger.ZERO);

    private final BigInteger money;

    private Money(final BigInteger money) {
        this.money = money;
    }

    public static Money of(final BigInteger money) {
        if (money == null) {
            throw new IllegalArgumentException("금액은 null 일 수 없습니다.");
        }

        if (BigInteger.ZERO.compareTo(money) > 0) {
            throw new IllegalArgumentException("금액은 음수일 수 없습니다.");
        }

        if (BigInteger.ZERO.equals(money)) {
            return ZERO;
        }

        return new Money(money);
    }

    public boolean isEqualTo(final Money other) {
        return this.money.equals(other.money);
    }

    public long toLongValue() {
        return this.money.longValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Money money1 = (Money) o;
        return Objects.equals(money, money1.money);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(money);
    }
}
