package nextstep.courses.domain;

import java.math.BigInteger;
import java.time.LocalDate;

public class Session {
    private final DateRange sessionDateRange;
    private final DateRange sessionRecruitmentDateRange;
    private final Money fee;

    private Session(final DateRange sessionDateRange, final DateRange sessionRecruitmentDateRange, final Money fee) {
        validationSession(sessionDateRange, sessionRecruitmentDateRange, fee);

        this.sessionDateRange = sessionDateRange;
        this.sessionRecruitmentDateRange = sessionRecruitmentDateRange;
        this.fee = fee;
    }

    private static void validationSession(final DateRange sessionDateRange, final DateRange sessionRecruitmentDateRange, final Money fee) {
        if (sessionDateRange == null) {
            throw new IllegalArgumentException("세션 기간은 필수압니다.");
        }

        if (sessionRecruitmentDateRange == null) {
            throw new IllegalArgumentException("세션 모집기간은 필수입니다.");
        }

        if (fee == null) {
            throw new IllegalArgumentException("세션 비용은 필수입니다.");
        }
    }

    public static Session freeSession(final DateRange sessionDateRange, final DateRange sessionRecruitmentDateRange) {
        return new Session(sessionDateRange, sessionRecruitmentDateRange, Money.of(BigInteger.ZERO));
    }

    public static Session paidSession(final DateRange sessionDateRange, final DateRange sessionRecruitmentDateRange, final Money fee) {
        return new Session(sessionDateRange, sessionRecruitmentDateRange, fee);
    }

    public boolean isEqualsFee(final Money fee) {
        return this.fee.isEqualTo(fee);
    }

    public boolean isRecruiting(final LocalDate applyDate) {
        return sessionRecruitmentDateRange.isBetween(applyDate);
    }
}
