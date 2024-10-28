package nextstep.session.domain;

import nextstep.courses.exception.SessionNotRecruitingException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private final SessionCoverImage sessionCoverImage;
    private final DateRange sessionDateRange;
    private final SessionStatus sessionStatus;
    private final Money fee;
    private final Capacity capacity;
    private final List<NsUser> sessionUserList;

    private Session(final DateRange sessionDateRange, final SessionStatus sessionStatus, final Money fee) {
        this(sessionDateRange, sessionStatus, fee, Capacity.noLimit());
    }

    private Session(final DateRange sessionDateRange, final SessionStatus sessionStatus, final Money fee, final Capacity capacity) {
        this(null, sessionDateRange, sessionStatus, fee, capacity, new ArrayList<>());
    }

    private Session(final SessionCoverImage sessionCoverImage, final DateRange sessionDateRange, final SessionStatus sessionStatus, final Money fee, final Capacity capacity, final List<NsUser> sessionUserList) {
        this.sessionStatus = sessionStatus;
        validationSession(sessionDateRange, fee);

        this.sessionCoverImage = sessionCoverImage;
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

    public static Session freeSession(final DateRange sessionDateRange, final SessionStatus sessionStatus) {
        return new Session(sessionDateRange, sessionStatus, Money.of(BigInteger.ZERO));
    }

    public static Session paidSession(final DateRange sessionDateRange, final SessionStatus sessionStatus, final Money fee, final Capacity capacity) {
        return new Session(sessionDateRange, sessionStatus, fee, capacity);
    }

    public void apply(final NsUser sessionUser) {
        apply(sessionUser, new Payment());
    }

    public void apply(final NsUser sessionUser, final Payment payment) {
        if (!isRecruiting()) {
            throw new SessionNotRecruitingException("모집 기간이 아닙니다.");
        }

        if (!payment.isEqualsFee(fee)) {
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

    private boolean isRecruiting() {
        return sessionStatus.isRecruit();
    }
}
