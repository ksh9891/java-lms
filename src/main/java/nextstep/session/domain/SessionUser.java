package nextstep.session.domain;

import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class SessionUser {
    private final Long id;
    private final NsUser user;
    private final Session session;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public SessionUser(final NsUser user, final Session session) {
        this(0L, user, session, LocalDateTime.now(), null);
    }

    public SessionUser(final Long id, final NsUser user, final Session session, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.session = session;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Long getId() {
        return id;
    }

    public NsUser getUser() {
        return user;
    }

    public Session getSession() {
        return session;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
