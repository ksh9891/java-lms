package nextstep.courses.domain;

import nextstep.courses.exception.SessionNotRecruitingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class SessionTest {
    private static DateRange SESSION_DATE_RANGE = new DateRange(LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 10));
    private static DateRange RECURITMENT_DATE_RANGE = new DateRange(LocalDate.of(2024, 9, 1), LocalDate.of(2024, 9, 20));
    private static LocalDate BEFORE_RECURITMENT_DATE = LocalDate.of(2024, 8, 30);
    private static LocalDate INCLUDE_RECURITMENT_DATE = LocalDate.of(2024, 9, 20);

    @Test
    @DisplayName("Session 기간은 필수이다.")
    void shouldThrowExceptionWhenSessionDateRangeIsMissing() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Session(null, RECURITMENT_DATE_RANGE));
    }

    @Test
    @DisplayName("Session 모집기간은 필수이다.")
    void shouldThrowExceptionWhenSessionRecruitmentDateRangeIsMissing() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Session(SESSION_DATE_RANGE, null));
    }

    @Test
    @DisplayName("Session 이 '모집중' 상태가 아니면 신청 시 예외가 발생한다.")
    void shouldThrowExceptionWhenSessionIsNotInRecruitingStatus() {
        final Session session = new Session(SESSION_DATE_RANGE, RECURITMENT_DATE_RANGE);

        assertThatThrownBy(() -> session.apply(BEFORE_RECURITMENT_DATE))
            .isExactlyInstanceOf(SessionNotRecruitingException.class);
    }
}
