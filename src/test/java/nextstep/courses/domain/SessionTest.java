package nextstep.courses.domain;

import nextstep.courses.exception.SessionNotRecruitingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

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
    @DisplayName("Session 이 '모집중' 상태가 아니면 신청 시 예외가 발생한다.")
    void shouldThrowExceptionWhenSessionIsNotInRecruitingStatus() {
        final Session session = Session.freeSession(SESSION_DATE_RANGE, RECRUITMENT_DATE_RANGE);

        assertThatThrownBy(() -> session.apply(BEFORE_RECRUITMENT_DATE, Money.of(BigInteger.valueOf(3))))
            .isExactlyInstanceOf(SessionNotRecruitingException.class);
    }

    @Test
    @DisplayName("수강생이 결제한 금액과 수강료가 일치하지 않으면 예외가 발생한다.")
    void shouldApplyWhenPaymentAmountMatchesTuitionFee() {
        final Session session = Session.paidSession(
            SESSION_DATE_RANGE,
            RECRUITMENT_DATE_RANGE,
            Money.of(BigInteger.valueOf(1000))
        );

        assertThatThrownBy(() -> session.apply(INCLUDE_RECRUITMENT_DATE, Money.of(BigInteger.valueOf(500))))
            .isExactlyInstanceOf(SessionNotRecruitingException.class);
    }
}
