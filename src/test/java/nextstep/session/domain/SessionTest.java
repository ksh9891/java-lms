package nextstep.session.domain;

import nextstep.courses.exception.SessionNotRecruitingException;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SessionTest {
    private static final DateRange SESSION_DATE_RANGE = new DateRange(LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 10));
    private static final LocalDate BEFORE_RECRUITMENT_DATE = LocalDate.of(2024, 8, 30);
    private static final LocalDate INCLUDE_RECRUITMENT_DATE = LocalDate.of(2024, 10, 3);
    private static final LocalDate AFTER_RECRUITMENT_DATE = LocalDate.of(2024, 11, 3);

    private static final Money PAID_FEE = Money.of(BigInteger.valueOf(1000));

    @Test
    @DisplayName("Session 기간은 필수이다.")
    void shouldThrowExceptionWhenSessionDateRangeIsMissing() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Session.freeSession(null));
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

    @Test
    @DisplayName("최대 수강 인원 제한이 없다.")
    void shouldAllowUnlimitedApply() {
        final Session session = Session.freeSession(SESSION_DATE_RANGE);

        assertThat(session.hasLimit()).isFalse();
    }

    @Test
    @DisplayName("최대 수강 인원 제한이 있다.")
    void shouldAllowLimitedApply() {
        final Session session = Session.paidSession(SESSION_DATE_RANGE, PAID_FEE, Capacity.of(10));

        assertThat(session.hasLimit()).isTrue();
    }

    @Test
    @DisplayName("자리가 있다면 신청이 가능하다.")
    void shouldAllowApplyWhenSlotsAreAvailable() {
        final Session sessionManager = Session.paidSession(SESSION_DATE_RANGE, PAID_FEE, Capacity.of(2));
        final NsUser user1 = new NsUser();
        final NsUser user2 = new NsUser();

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
        final Session sessionManager = Session.paidSession(SESSION_DATE_RANGE, PAID_FEE, Capacity.of(2));

        assertThrows(SessionNotRecruitingException.class, () -> {
            sessionManager.apply(INCLUDE_RECRUITMENT_DATE, new NsUser());
            sessionManager.apply(INCLUDE_RECRUITMENT_DATE, new NsUser());
            sessionManager.apply(INCLUDE_RECRUITMENT_DATE, new NsUser());
        });
    }


    @Test
    @DisplayName("Session 이 '모집중' 상태가 아니면 신청 시 예외가 발생한다.")
    void shouldThrowExceptionWhenSessionIsNotInRecruitingStatus() {
        final Session sessionManager = Session.freeSession(SESSION_DATE_RANGE);

        assertThatThrownBy(() -> sessionManager.apply(BEFORE_RECRUITMENT_DATE, new NsUser()))
            .isExactlyInstanceOf(SessionNotRecruitingException.class);
    }

    @Test
    @DisplayName("수강생이 결제한 금액과 수강료가 일치하지 않으면 예외가 발생한다.")
    void shouldApplyWhenPaymentAmountMatchesTuitionFee() {
        final Session sessionManager = Session.paidSession(SESSION_DATE_RANGE, PAID_FEE, Capacity.of(2));

        assertThatThrownBy(() -> sessionManager.apply(INCLUDE_RECRUITMENT_DATE, new NsUser(), Money.of(BigInteger.valueOf(500))))
            .isExactlyInstanceOf(SessionNotRecruitingException.class);
    }
}
