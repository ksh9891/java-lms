package nextstep.session.infrastructure;

import nextstep.session.domain.SessionCoverImage;
import nextstep.session.domain.SessionCoverImageRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository("sessionCoverImageRepository")
public class JdbcSessionCoverImageRepository implements SessionCoverImageRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionCoverImageRepository(final JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(final Long sessionId, final SessionCoverImage sessionCoverImage) {
        final String sql = "insert into session_cover_image(session_id, cover_image_name, cover_image_extension, cover_image_width, cover_image_height, cover_image_size, created_at) values(?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            sessionId,
            sessionCoverImage.getName(),
            sessionCoverImage.getExtension().name(),
            sessionCoverImage.getDimensions().getWidth(),
            sessionCoverImage.getDimensions().getHeight(),
            sessionCoverImage.getSize().toLongValue(),
            sessionCoverImage.getCreatedAt()
        );
    }
}
