package nextstep.courses.domain;

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
}
