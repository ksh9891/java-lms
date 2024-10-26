package nextstep.courses.domain;

import nextstep.courses.exception.SessionNotRecruitingException;

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

    public void apply(final LocalDate applyDate, final Money money) {
        if (!isRecruiting(applyDate)) {
            throw new SessionNotRecruitingException("모집 기간이 아닙니다.");
        }

        if (!fee.isEqualTo(money)) {
            throw new SessionNotRecruitingException("수강료가 지불한 금액과 일치하지 않습니다.");
        }
    }

    private boolean isRecruiting(final LocalDate applyDate) {
        return sessionRecruitmentDateRange.isBetween(applyDate);
    }
}
