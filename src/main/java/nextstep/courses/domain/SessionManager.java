package nextstep.courses.domain;

import nextstep.courses.exception.SessionNotRecruitingException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SessionManager {
    private final Session session;
    private final Capacity capacity;
    private final List<SessionUser> sessionUserList;

    private SessionManager(final Session session, final Capacity capacity) {
        this.session = session;
        this.capacity = capacity;
        this.sessionUserList = new ArrayList<>();
    }

    public static SessionManager withNoLimit(final Session session) {
        return new SessionManager(session, Capacity.noLimit());
    }

    public static SessionManager withCapacityLimit(final Session session, final Capacity capacity) {
        return new SessionManager(session, capacity);
    }

    public boolean hasLimit() {
        return capacity.hasLimit();
    }

    public void apply(final LocalDate applyDate, final SessionUser sessionUser) {
        apply(applyDate, sessionUser, Money.ZERO);
    }

    public void apply(final LocalDate applyDate, final SessionUser sessionUser, final Money fee) {
        if (!session.isRecruiting(applyDate)) {
            throw new SessionNotRecruitingException("모집 기간이 아닙니다.");
        }

        if (!session.isEqualsFee(fee)) {
            throw new SessionNotRecruitingException("수강료가 지불한 금액과 일치하지 않습니다.");
        }

        if (capacity.isFull(sessionUserList.size())) {
            throw new SessionNotRecruitingException("수강생이 가득찼습니다.");
        }

        sessionUserList.add(sessionUser);
    }

    public boolean hasApplied(final SessionUser sessionUser) {
        return sessionUserList.contains(sessionUser);
    }
}
