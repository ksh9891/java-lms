package nextstep.courses.domain;

import nextstep.courses.exception.SessionNotRecruitingException;

import java.time.LocalDate;

public class Session {
    private final DateRange sessionDateRange;
    private final DateRange sessionRecruitmentDateRange;

    public Session(final DateRange sessionDateRange, final DateRange sessionRecruitmentDateRange) {
        if (sessionDateRange == null) {
            throw new IllegalArgumentException("세션 기간은 필수압니다.");
        }

        if (sessionRecruitmentDateRange == null) {
            throw new IllegalArgumentException("세션 모집기간은 필수입니다.");
        }

        this.sessionDateRange = sessionDateRange;
        this.sessionRecruitmentDateRange = sessionRecruitmentDateRange;
    }

    public void apply(final LocalDate applyDate) {
        if (!isRecruiting(applyDate)) {
            throw new SessionNotRecruitingException("모집 기간이 아닙니다.");
        }
    }

    private boolean isRecruiting(final LocalDate applyDate) {
        return sessionRecruitmentDateRange.isBetween(applyDate);
    }
}
