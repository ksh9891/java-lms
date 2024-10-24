package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

public class DateRangeTest {
    @Test
    @DisplayName("시작일과 종료일 중 어느하나라도 설정되지 않은 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenStartDateOrEndDateIsMissing() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new DateRange(LocalDate.now(), null));
    }

    @Test
    @DisplayName("종료일은 시작일 이전으로 설정이 불가능하다.")
    void shouldNotAllowEndDateBeforeStartDate() {
        final LocalDate startDate = LocalDate.of(2024, 12, 31);
        final LocalDate endDate = LocalDate.of(2024, 1, 1);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> new DateRange(startDate, endDate));
    }

    @Test
    @DisplayName("입력된 날짜가 null 이면 예외를 반환한다.")
    void shouldThrowExceptionWhenDateIsNull() {
        final LocalDate startDate = LocalDate.of(2024, 10, 1);
        final LocalDate endDate = LocalDate.of(2024, 10, 10);
        final DateRange dateRange = new DateRange(startDate, endDate);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> dateRange.isBetween(null));
    }

    @Test
    @DisplayName("기간 내의 날짜가 입력되면 true 를 반환한다.")
    void shouldReturnTrueWhenDateIsWithinRange() {
        final LocalDate startDate = LocalDate.of(2024, 10, 1);
        final LocalDate endDate = LocalDate.of(2024, 10, 10);
        final DateRange dateRange = new DateRange(startDate, endDate);
        final LocalDate inputDate = LocalDate.of(2024, 10, 3);

        assertThat(dateRange.isBetween(inputDate)).isTrue();
    }

    @Test
    @DisplayName("기간 내의 날짜가 입력되지 않으면 false 를 반환한다.")
    void shouldReturnFalseWhenDateIsNotWithinRange() {
        final LocalDate startDate = LocalDate.of(2024, 10, 1);
        final LocalDate endDate = LocalDate.of(2024, 10, 10);
        final DateRange dateRange = new DateRange(startDate, endDate);
        final LocalDate afterEndDate = LocalDate.of(2024, 10, 11);
        final LocalDate beforeStartDate = LocalDate.of(2024, 9, 30);

        assertAll(
            () -> assertThat(dateRange.isBetween(afterEndDate)).isFalse(),
            () -> assertThat(dateRange.isBetween(beforeStartDate)).isFalse()
        );
    }

    @Test
    @DisplayName("시작일과 동일한 날짜가 입력되면 true 를 반환한다.")
    void shouldReturnTrueWhenDateIsStartDate() {
        final LocalDate startDate = LocalDate.of(2024, 10, 1);
        final LocalDate endDate = LocalDate.of(2024, 10, 10);
        final DateRange dateRange = new DateRange(startDate, endDate);
        final LocalDate inputDate = LocalDate.of(2024, 10, 1);

        assertThat(dateRange.isBetween(inputDate)).isTrue();
    }

    @Test
    @DisplayName("종료일과 동일한 날짜가 입력되면 true 를 반환한다.")
    void shouldReturnTrueWhenDateIsEndDate() {
        final LocalDate startDate = LocalDate.of(2024, 10, 1);
        final LocalDate endDate = LocalDate.of(2024, 10, 10);
        final DateRange dateRange = new DateRange(startDate, endDate);
        final LocalDate inputDate = LocalDate.of(2024, 10, 10);

        assertThat(dateRange.isBetween(inputDate)).isTrue();
    }
}
