package nextstep.session.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SessionTest {
    private static final DateRange SESSION_DATE_RANGE = new DateRange(LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 10));

    private static final Money PAID_FEE = Money.of(BigInteger.valueOf(1000));
    private static final Payment PAYMENT = new Payment("test001", 1L, 1L, 1000L);
    private static final Payment UNDER_PAYMENT = new Payment("test001", 1L, 1L, 500L);

    @Test
    @DisplayName("Session 기간은 필수이다.")
    void shouldThrowExceptionWhenSessionDateRangeIsMissing() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Session.freeSession(null, SessionStatus.모집중));
    }

    @Test
    @DisplayName("최대 수강 인원 제한이 없다.")
    void shouldAllowUnlimitedApply() {
        final Session session = Session.freeSession(SESSION_DATE_RANGE, SessionStatus.모집중);

        assertThat(session.hasLimit()).isFalse();
    }

    @Test
    @DisplayName("최대 수강 인원 제한이 있다.")
    void shouldAllowLimitedApply() {
        final Session session = Session.paidSession(SESSION_DATE_RANGE, SessionStatus.모집중, PAID_FEE, Capacity.of(10));

        assertThat(session.hasLimit()).isTrue();
    }

    @Test
    @DisplayName("자리가 있다면 신청이 가능하다.")
    void shouldAllowApplyWhenSlotsAreAvailable() {
        final Session session = Session.paidSession(SESSION_DATE_RANGE, SessionStatus.모집중, PAID_FEE, Capacity.of(2));
        final SessionUser user1 = new SessionUser(new NsUser(), session, PAYMENT);
        final SessionUser user2 = new SessionUser(new NsUser(), session, PAYMENT);

        session.apply(user1);
        session.apply(user2);

        assertAll(
            () -> assertThat(session.hasApplied(user1)).isTrue(),
            () -> assertThat(session.hasApplied(user2)).isTrue()
        );
    }

    @Test
    @DisplayName("자리가 없다면 예외가 발생한다.")
    void shouldThrowExceptionWhenNoSlotsAvailable() {
        final Session session = Session.paidSession(SESSION_DATE_RANGE, SessionStatus.모집중, PAID_FEE, Capacity.of(2));

        assertThrows(IllegalStateException.class, () -> {
            session.apply(new SessionUser(new NsUser(), session, PAYMENT));
            session.apply(new SessionUser(new NsUser(), session, PAYMENT));
            session.apply(new SessionUser(new NsUser(), session, PAYMENT));
        });
    }


    @Test
    @DisplayName("Session 이 '모집중' 상태가 아니면 신청 시 예외가 발생한다.")
    void shouldThrowExceptionWhenSessionIsNotInRecruitingStatus() {
        final Session session = Session.freeSession(SESSION_DATE_RANGE, SessionStatus.준비중);

        assertThatIllegalStateException()
            .isThrownBy(() -> session.apply(new SessionUser(new NsUser(), session, PAYMENT)));
    }

    @Test
    @DisplayName("수강생이 결제한 금액과 수강료가 일치하지 않으면 예외가 발생한다.")
    void shouldApplyWhenPaymentAmountMatchesTuitionFee() {
        final Session session = Session.paidSession(SESSION_DATE_RANGE, SessionStatus.모집중, PAID_FEE, Capacity.of(2));

        assertThatIllegalArgumentException()
            .isThrownBy(() -> session.apply(new SessionUser(new NsUser(), session, UNDER_PAYMENT)));
    }
}
