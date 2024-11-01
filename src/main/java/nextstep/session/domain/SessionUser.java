package nextstep.session.domain;

import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class SessionUser {
    private final NsUser user;
    private final Session session;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public SessionUser(final NsUser user, final Session session) {
        this.user = user;
        this.session = session;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public boolean matchSessionUser(final SessionUser sessionUser) {
        return matchUser(sessionUser.user) && matchSession(sessionUser.session);
    }

    private boolean matchUser(final NsUser target) {
        return user.matchUser(target);
    }

    private boolean matchSession(final Session target) {
        return session.matchSession(target);
    }

    public NsUser getUser() {
        return user;
    }

    public Session getSession() {
        return session;
    }
}
