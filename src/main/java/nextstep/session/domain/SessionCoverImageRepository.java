package nextstep.session.domain;

public interface SessionCoverImageRepository {
    int save(final Long sessionId, final SessionCoverImage sessionCoverImage);
}
