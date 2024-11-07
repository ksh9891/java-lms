package nextstep.session.domain;

public interface SessionUserRepository {
    int save(final SessionUser sessionUser);

    SessionUsers findById(final Long sessionId);

    SessionUser findByIdAndUserId(final Long sessionId, final Long userId);

    int updateStatus(final SessionUser sessionUser);
}
