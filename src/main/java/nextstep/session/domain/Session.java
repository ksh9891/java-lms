package nextstep.session.domain;

import java.math.BigInteger;
import java.time.LocalDate;

public class Session {
    private final DateRange sessionDateRange;
    private final Money fee;

    private Session(final DateRange sessionDateRange, final Money fee) {
        validationSession(sessionDateRange, fee);

        this.sessionDateRange = sessionDateRange;
        this.fee = fee;
    }

    private static void validationSession(final DateRange sessionDateRange, final Money fee) {
        if (sessionDateRange == null) {
            throw new IllegalArgumentException("세션 기간은 필수압니다.");
        }

        if (fee == null) {
            throw new IllegalArgumentException("세션 비용은 필수입니다.");
        }
    }

    public static Session freeSession(final DateRange sessionDateRange) {
        return new Session(sessionDateRange, Money.of(BigInteger.ZERO));
    }

    public static Session paidSession(final DateRange sessionDateRange, final Money fee) {
        return new Session(sessionDateRange, fee);
    }

    public boolean isEqualsFee(final Money fee) {
        return this.fee.isEqualTo(fee);
    }

    public boolean isRecruiting(final LocalDate applyDate) {
        return currentStatus(applyDate).isRecruit();
    }

    public SessionStatus currentStatus(final LocalDate localDate) {
        if (sessionDateRange.isBeforeStartDate(localDate)) {
            return SessionStatus.준비중;
        }

        if (sessionDateRange.isBetween(localDate)) {
            return SessionStatus.모집중;
        }

        return SessionStatus.종료;
    }
}
