package nextstep.session.domain;

import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class SessionUser {
    private final Long sessionId;
    private final NsUser nsUser;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public SessionUser(final Long sessionId, final NsUser nsUser) {
        this(sessionId, nsUser, LocalDateTime.now(), null);
    }

    public SessionUser(final Long sessionId, final NsUser nsUser, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.sessionId = sessionId;
        this.nsUser = nsUser;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public boolean matchSessionUser(final SessionUser sessionUser) {
        return matchUser(sessionUser.nsUser) && matchSession(sessionUser.sessionId);
    }

    private boolean matchUser(final NsUser target) {
        return nsUser.equals(target);
    }

    private boolean matchSession(final Long targetId) {
        return sessionId.equals(targetId);
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return nsUser.getId();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
