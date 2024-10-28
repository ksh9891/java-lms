package nextstep.session.domain;

import java.time.LocalDate;

public class DateRange {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public DateRange(final LocalDate startDate, final LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("시작일, 종료일은 필수입니다.");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("종료일은 시작일보다 이후여야 합니다.");
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isBeforeStartDate(final LocalDate date) {
        return date.isBefore(startDate);
    }

    public boolean isBetween(final LocalDate inputDate) {
        if (inputDate == null) {
            throw new IllegalArgumentException("비교 날짜가 존재하지 않습니다.");
        }

        return startDate.isEqual(inputDate) ||
            endDate.isEqual(inputDate) ||
            (startDate.isBefore(inputDate) && endDate.isAfter(inputDate));
    }
}
