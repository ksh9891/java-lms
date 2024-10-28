package nextstep.session.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class SessionTest {
    private static final DateRange SESSION_DATE_RANGE = new DateRange(LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 10));
    private static final LocalDate BEFORE_RECRUITMENT_DATE = LocalDate.of(2024, 8, 30);
    private static final LocalDate INCLUDE_RECRUITMENT_DATE = LocalDate.of(2024, 10, 3);
    private static final LocalDate AFTER_RECRUITMENT_DATE = LocalDate.of(2024, 11, 3);

    @Test
    @DisplayName("Session 기간은 필수이다.")
    void shouldThrowExceptionWhenSessionDateRangeIsMissing() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Session.freeSession(null));
    }

    @Test
    @DisplayName("입력된 날짜가 모집기간 내에 포함되면 true 를 반환한다.")
    void shouldReturnTrueWhenDateIsWithinRecruitmentPeriod() {
        final Session session = Session.freeSession(SESSION_DATE_RANGE);

        assertThat(session.isRecruiting(INCLUDE_RECRUITMENT_DATE)).isTrue();
    }

    @Test
    @DisplayName("입력된 날짜가 모집기간 내에 포함되지 않으면 false 를 반환한다.")
    void shouldReturnFalseWhenDateIsOutsideRecruitmentPeriod() {
        final Session session = Session.freeSession(SESSION_DATE_RANGE);

        assertThat(session.isRecruiting(BEFORE_RECRUITMENT_DATE)).isFalse();
    }

    @Test
    @DisplayName("입력된 날짜가 Session 시작일 이전이면 준비중 상태를 반환한다.")
    void shouldReturnPreparingStatusWhenDateIsBeforeSessionStartDate() {
        final Session session = Session.freeSession(SESSION_DATE_RANGE);

        assertThat(session.currentStatus(BEFORE_RECRUITMENT_DATE)).isEqualTo(SessionStatus.준비중);
    }

    @Test
    @DisplayName("입력된 날짜가 Session 시작일, 종료일 사이라면 모집중 상태를 반환한다.")
    void shouldReturnRecruitingStatusWhenDateIsBetweenSessionStartAndEnd() {
        final Session session = Session.freeSession(SESSION_DATE_RANGE);

        assertThat(session.currentStatus(INCLUDE_RECRUITMENT_DATE)).isEqualTo(SessionStatus.모집중);
    }

    @Test
    @DisplayName("입력된 날짜가 Session 종료일 이후이면 종료 상태를 반환한다.")
    void shouldReturnClosedStatusWhenDateIsAfterSessionEndDate() {
        final Session session = Session.freeSession(SESSION_DATE_RANGE);

        assertThat(session.currentStatus(AFTER_RECRUITMENT_DATE)).isEqualTo(SessionStatus.종료);
    }
}
