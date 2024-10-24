package nextstep.courses.domain;

import java.time.LocalDate;

public class Session {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Session(final LocalDate startDate, final LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("시작일, 종료일은 필수입니다.");
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }
}
