package nextstep.session.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class SessionTest {
    private static final DateRange SESSION_DATE_RANGE = new DateRange(LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 10));

    private static final Money PAID_FEE = Money.of(BigInteger.valueOf(1000));
    private static final Payment PAYMENT = new Payment("test001", 1L, 1L, 1000L);
    private static final Payment UNDER_PAYMENT = new Payment("test001", 1L, 1L, 500L);

    @Test
    @DisplayName("Session 기간은 필수이다.")
    void shouldThrowExceptionWhenSessionDateRangeIsMissing() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Session.freeSession(1L, 1L, null, SessionStatus.준비중, SessionRecruiting.모집중));
    }

    @Test
    @DisplayName("최대 수강 인원 제한이 없다.")
    void shouldAllowUnlimitedApply() {
        final Session session = Session.freeSession(1L, 1L, SESSION_DATE_RANGE, SessionStatus.준비중, SessionRecruiting.모집중);

        assertThat(session.hasLimit()).isFalse();
    }

    @Test
    @DisplayName("최대 수강 인원 제한이 있다.")
    void shouldAllowLimitedApply() {
        final Session session = Session.paidSession(1L, 1L, SESSION_DATE_RANGE, SessionStatus.준비중, SessionRecruiting.모집중, PAID_FEE, Capacity.of(10));

        assertThat(session.hasLimit()).isTrue();
    }

    @Test
    @DisplayName("동일한 회원이 같은 Session 에 수강신청을 하면 예외가 발생한다.")
    void shouldThrowExceptionWhenSameUserRegistersForSameSessionTwice() {
        final Session session = Session.paidSession(1L, 1L, SESSION_DATE_RANGE, SessionStatus.준비중, SessionRecruiting.모집중, PAID_FEE, Capacity.of(2));
        final NsUser user1 = new NsUser(1L, "test001");

        assertThatIllegalStateException()
            .isThrownBy(() -> {
                session.apply(user1, PAYMENT);
                session.apply(user1, PAYMENT);
            });
    }

    @Test
    @DisplayName("자리가 없다면 예외가 발생한다.")
    void shouldThrowExceptionWhenNoSlotsAvailable() {
        final Session session = Session.paidSession(1L, 1L, SESSION_DATE_RANGE, SessionStatus.준비중, SessionRecruiting.모집중, PAID_FEE, Capacity.of(2));

        assertThatThrownBy(() -> {
            session.apply(new NsUser(1L, "test001"), PAYMENT);
            session.apply(new NsUser(2L, "test002"), PAYMENT);
            session.apply(new NsUser(3L, "test003"), PAYMENT);
        }).isInstanceOf(IllegalStateException.class);
    }


    @Test
    @DisplayName("Session 이 '모집중' 상태가 아니면 신청 시 예외가 발생한다.")
    void shouldThrowExceptionWhenSessionIsNotInRecruitingStatus() {
        final Session session = Session.freeSession(1L, 1L, SESSION_DATE_RANGE, SessionStatus.진행중, SessionRecruiting.비모집중);

        assertThatIllegalStateException()
            .isThrownBy(() -> session.apply(new NsUser(1L, "test001")));
    }

    @Test
    @DisplayName("수강생이 결제한 금액과 수강료가 일치하지 않으면 예외가 발생한다.")
    void shouldApplyWhenPaymentAmountMatchesTuitionFee() {
        final Session session = Session.paidSession(1L, 1L, SESSION_DATE_RANGE, SessionStatus.준비중, SessionRecruiting.모집중, PAID_FEE, Capacity.of(2));

        assertThatIllegalArgumentException()
            .isThrownBy(() -> session.apply(new NsUser(1L, "test001"), UNDER_PAYMENT));
    }
}
