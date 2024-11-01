package nextstep.session.domain;

public interface SessionUserRepository {
    int save(final SessionUser sessionUser);

    SessionUser findById(final Long sessionId, final Long userId);
}
