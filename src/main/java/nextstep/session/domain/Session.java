package nextstep.session.domain;

import java.math.BigInteger;

public class Session {
    private final SessionCoverImage sessionCoverImage;
    private final DateRange sessionDateRange;
    private final SessionStatus sessionStatus;
    private final Money fee;
    private final Capacity capacity;
    private final SessionUsers sessionUsers;

    private Session(final DateRange sessionDateRange, final SessionStatus sessionStatus, final Money fee) {
        this(sessionDateRange, sessionStatus, fee, Capacity.noLimit());
    }

    private Session(final DateRange sessionDateRange, final SessionStatus sessionStatus, final Money fee, final Capacity capacity) {
        this(null, sessionDateRange, sessionStatus, fee, capacity, new SessionUsers());
    }

    private Session(final SessionCoverImage sessionCoverImage, final DateRange sessionDateRange, final SessionStatus sessionStatus, final Money fee, final Capacity capacity, final SessionUsers sessionUsers) {
        validationSession(sessionDateRange, fee);

        this.sessionCoverImage = sessionCoverImage;
        this.sessionDateRange = sessionDateRange;
        this.sessionStatus = sessionStatus;
        this.fee = fee;
        this.capacity = capacity;
        this.sessionUsers = sessionUsers;
    }

    private static void validationSession(final DateRange sessionDateRange, final Money fee) {
        if (sessionDateRange == null) {
            throw new IllegalArgumentException("세션 기간은 필수압니다.");
        }

        if (fee == null) {
            throw new IllegalArgumentException("세션 비용은 필수입니다.");
        }
    }

    public static Session freeSession(final DateRange sessionDateRange, final SessionStatus sessionStatus) {
        return new Session(sessionDateRange, sessionStatus, Money.of(BigInteger.ZERO));
    }

    public static Session paidSession(final DateRange sessionDateRange, final SessionStatus sessionStatus, final Money fee, final Capacity capacity) {
        return new Session(sessionDateRange, sessionStatus, fee, capacity);
    }

    public void apply(final SessionUser sessionUser) {
        if (!isRecruiting()) {
            throw new IllegalStateException("모집 기간이 아닙니다.");
        }

        if (!isFree()) {
            validationPaidSession(sessionUser);
        }

        sessionUsers.add(sessionUser);
    }

    private void validationPaidSession(final SessionUser sessionUser) {
        if (!sessionUser.hasPaidFee(fee)) {
            throw new IllegalArgumentException("수강료를 지불하지 않았습니다.");
        }

        if (capacity.isFull(sessionUsers.size())) {
            throw new IllegalStateException("수강생이 가득찼습니다.");
        }
    }

    public boolean hasApplied(final SessionUser sessionUser) {
        return sessionUsers.contains(sessionUser);
    }

    public boolean hasLimit() {
        return capacity.hasLimit();
    }

    private boolean isRecruiting() {
        return sessionStatus.isRecruit();
    }

    private boolean isFree() {
        return fee.isEqualTo(Money.ZERO);
    }
}
