package nextstep.session.domain;

import nextstep.courses.exception.SessionNotRecruitingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SessionManagerTest {
    private static final DateRange SESSION_DATE_RANGE = new DateRange(LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 10));
    private static final DateRange RECRUITMENT_DATE_RANGE = new DateRange(LocalDate.of(2024, 9, 1), LocalDate.of(2024, 9, 20));

    private static final LocalDate BEFORE_RECRUITMENT_DATE = LocalDate.of(2024, 8, 30);
    private static final LocalDate INCLUDE_RECRUITMENT_DATE = LocalDate.of(2024, 9, 20);

    private static final Money PAID_FEE = Money.of(BigInteger.valueOf(1000));

    private static final Session FREE_SESSION = Session.freeSession(SESSION_DATE_RANGE, RECRUITMENT_DATE_RANGE);
    private static final Session PAID_SESSION = Session.paidSession(SESSION_DATE_RANGE, RECRUITMENT_DATE_RANGE, PAID_FEE);

    @Test
    @DisplayName("최대 수강 인원 제한이 없다.")
    void shouldAllowUnlimitedApply() {
        final SessionManager sessionManager = SessionManager.withNoLimit(FREE_SESSION);

        assertThat(sessionManager.hasLimit()).isFalse();
    }

    @Test
    @DisplayName("최대 수강 인원 제한이 있다.")
    void shouldAllowLimitedApply() {
        final SessionManager sessionManager = SessionManager.withCapacityLimit(PAID_SESSION, Capacity.of(10));

        assertThat(sessionManager.hasLimit()).isTrue();
    }

    @Test
    @DisplayName("자리가 있다면 신청이 가능하다.")
    void shouldAllowApplyWhenSlotsAreAvailable() {
        final SessionManager sessionManager = SessionManager.withCapacityLimit(PAID_SESSION, Capacity.of(2));
        final SessionUser user1 = new SessionUser("test1", PAID_FEE);
        final SessionUser user2 = new SessionUser("test2", PAID_FEE);

        sessionManager.apply(INCLUDE_RECRUITMENT_DATE, user1, PAID_FEE);
        sessionManager.apply(INCLUDE_RECRUITMENT_DATE, user2, PAID_FEE);

        assertAll(
            () -> assertThat(sessionManager.hasApplied(user1)).isTrue(),
            () -> assertThat(sessionManager.hasApplied(user2)).isTrue()
        );
    }

    @Test
    @DisplayName("자리가 없다면 예외가 발생한다.")
    void shouldThrowExceptionWhenNoSlotsAvailable() {
        final SessionManager sessionManager = SessionManager.withCapacityLimit(PAID_SESSION, Capacity.of(2));

        assertThrows(SessionNotRecruitingException.class, () -> {
            sessionManager.apply(INCLUDE_RECRUITMENT_DATE, new SessionUser("test1", PAID_FEE));
            sessionManager.apply(INCLUDE_RECRUITMENT_DATE, new SessionUser("test2", PAID_FEE));
            sessionManager.apply(INCLUDE_RECRUITMENT_DATE, new SessionUser("test3", PAID_FEE));
        });
    }


    @Test
    @DisplayName("Session 이 '모집중' 상태가 아니면 신청 시 예외가 발생한다.")
    void shouldThrowExceptionWhenSessionIsNotInRecruitingStatus() {
        final SessionManager sessionManager = SessionManager.withNoLimit(FREE_SESSION);

        assertThatThrownBy(() -> sessionManager.apply(BEFORE_RECRUITMENT_DATE, new SessionUser("test1", Money.ZERO)))
            .isExactlyInstanceOf(SessionNotRecruitingException.class);
    }

    @Test
    @DisplayName("수강생이 결제한 금액과 수강료가 일치하지 않으면 예외가 발생한다.")
    void shouldApplyWhenPaymentAmountMatchesTuitionFee() {
        final SessionManager sessionManager = SessionManager.withCapacityLimit(PAID_SESSION, Capacity.of(2));

        assertThatThrownBy(() -> sessionManager.apply(INCLUDE_RECRUITMENT_DATE, new SessionUser("test1", Money.of(BigInteger.valueOf(500))), Money.of(BigInteger.valueOf(500))))
            .isExactlyInstanceOf(SessionNotRecruitingException.class);
    }
}
