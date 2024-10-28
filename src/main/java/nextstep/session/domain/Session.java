package nextstep.session.domain;

import nextstep.courses.exception.SessionNotRecruitingException;
import nextstep.users.domain.NsUser;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private final DateRange sessionDateRange;
    private final Money fee;
    private final Capacity capacity;
    private final List<NsUser> sessionUserList;

    private Session(final DateRange sessionDateRange, final Money fee) {
        this(sessionDateRange, fee, Capacity.noLimit());
    }

    private Session(final DateRange sessionDateRange, final Money fee, final Capacity capacity) {
        this(sessionDateRange, fee, capacity, new ArrayList<>());
    }

    private Session(final DateRange sessionDateRange, final Money fee, final Capacity capacity, final List<NsUser> sessionUserList) {
        validationSession(sessionDateRange, fee);

        this.sessionDateRange = sessionDateRange;
        this.fee = fee;
        this.capacity = capacity;
        this.sessionUserList = sessionUserList;
    }

    private static void validationSession(final DateRange sessionDateRange, final Money fee) {
        if (sessionDateRange == null) {
            throw new IllegalArgumentException("세션 기간은 필수압니다.");
        }

        if (fee == null) {
            throw new IllegalArgumentException("세션 비용은 필수입니다.");
        }
    }

    public static Session freeSession(final DateRange sessionDateRange) {
        return new Session(sessionDateRange, Money.of(BigInteger.ZERO));
    }

    public static Session paidSession(final DateRange sessionDateRange, final Money fee, final Capacity capacity) {
        return new Session(sessionDateRange, fee, capacity);
    }

    public SessionStatus currentStatus(final LocalDate localDate) {
        if (sessionDateRange.isBeforeStartDate(localDate)) {
            return SessionStatus.준비중;
        }

        if (sessionDateRange.isBetween(localDate)) {
            return SessionStatus.모집중;
        }

        return SessionStatus.종료;
    }

    public void apply(final LocalDate applyDate, final NsUser sessionUser) {
        apply(applyDate, sessionUser, Money.ZERO);
    }

    public void apply(final LocalDate applyDate, final NsUser sessionUser, final Money fee) {
        if (!isRecruiting(applyDate)) {
            throw new SessionNotRecruitingException("모집 기간이 아닙니다.");
        }

        if (!isEqualsFee(fee)) {
            throw new SessionNotRecruitingException("수강료가 지불한 금액과 일치하지 않습니다.");
        }

        if (capacity.isFull(sessionUserList.size())) {
            throw new SessionNotRecruitingException("수강생이 가득찼습니다.");
        }

        sessionUserList.add(sessionUser);
    }

    public boolean hasApplied(final NsUser sessionUser) {
        return sessionUserList.contains(sessionUser);
    }

    public boolean hasLimit() {
        return capacity.hasLimit();
    }

    private boolean isEqualsFee(final Money fee) {
        return this.fee.isEqualTo(fee);
    }

    private boolean isRecruiting(final LocalDate applyDate) {
        return currentStatus(applyDate).isRecruit();
    }
}
