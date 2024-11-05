package nextstep.session.domain;

import java.time.LocalDateTime;

public class SessionUser {
    private final Long sessionId;
    private final Long userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public SessionUser(final Long sessionId, final Long userId) {
        this(sessionId, userId, LocalDateTime.now(), null);
    }

    public SessionUser(final Long sessionId, final Long userId, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public boolean matchSessionUser(final SessionUser sessionUser) {
        return matchUser(sessionUser.userId) && matchSession(sessionUser.sessionId);
    }

    private boolean matchUser(final Long targetId) {
        return userId.equals(targetId);
    }

    private boolean matchSession(final Long targetId) {
        return sessionId.equals(targetId);
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
