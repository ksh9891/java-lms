package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class SessionTest {
    @Test
    @DisplayName("Session 기간은 필수이다.")
    void shouldThrowExceptionWhenSessionDateRangeIsMissing() {
        final DateRange recruitmentDateRange = new DateRange(LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 10));

        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Session(null, recruitmentDateRange));
    }

    @Test
    @DisplayName("Session 모집기간은 필수이다.")
    void shouldThrowExceptionWhenSessionRecruitmentDateRangeIsMissing() {
        final DateRange sessionDateRange = new DateRange(LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 10));
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Session(sessionDateRange, null));
    }
}
