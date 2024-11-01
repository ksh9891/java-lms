package nextstep.session.domain;

public interface SessionRepository {
    int save(final Session session);

    Session findById(final Long id);
}
