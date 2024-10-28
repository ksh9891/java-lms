package nextstep.session.domain;

import java.util.Objects;

public class SessionUser {
    private final String userId;
    private final Money paidFee;

    public SessionUser(String userId, Money paidFee) {
        this.userId = userId;
        this.paidFee = paidFee;
    }

    public boolean isEqualsFee(final Money paidFee) {
        return this.paidFee.equals(paidFee);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SessionUser that = (SessionUser) o;
        return Objects.equals(userId, that.userId) && Objects.equals(paidFee, that.paidFee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, paidFee);
    }
}
