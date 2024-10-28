package nextstep.session.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class SessionTest {
    private static final DateRange SESSION_DATE_RANGE = new DateRange(LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 10));
    private static final DateRange RECRUITMENT_DATE_RANGE = new DateRange(LocalDate.of(2024, 9, 1), LocalDate.of(2024, 9, 20));
    private static final LocalDate BEFORE_RECRUITMENT_DATE = LocalDate.of(2024, 8, 30);
    private static final LocalDate INCLUDE_RECRUITMENT_DATE = LocalDate.of(2024, 9, 20);

    @Test
    @DisplayName("Session 기간은 필수이다.")
    void shouldThrowExceptionWhenSessionDateRangeIsMissing() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Session.freeSession(null, RECRUITMENT_DATE_RANGE));
    }

    @Test
    @DisplayName("Session 모집기간은 필수이다.")
    void shouldThrowExceptionWhenSessionRecruitmentDateRangeIsMissing() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Session.freeSession(SESSION_DATE_RANGE, null));
    }

    @Test
    @DisplayName("입력된 날짜가 모집기간 내에 포함되면 true 를 반환한다.")
    void shouldReturnTrueWhenDateIsWithinRecruitmentPeriod() {
        final Session session = Session.freeSession(SESSION_DATE_RANGE, RECRUITMENT_DATE_RANGE);

        assertThat(session.isRecruiting(INCLUDE_RECRUITMENT_DATE)).isTrue();
    }

    @Test
    @DisplayName("입력된 날짜가 모집기간 내에 포함되지 않으면 false 를 반환한다.")
    void shouldReturnFalseWhenDateIsOutsideRecruitmentPeriod() {
        final Session session = Session.freeSession(SESSION_DATE_RANGE, RECRUITMENT_DATE_RANGE);

        assertThat(session.isRecruiting(BEFORE_RECRUITMENT_DATE)).isFalse();
    }
}
