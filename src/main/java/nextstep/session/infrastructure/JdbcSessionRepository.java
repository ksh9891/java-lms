package nextstep.session.infrastructure;

import nextstep.session.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(final JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(final Session session) {
        final String sql = "insert into session(course_id, status, fee, start_date, end_date, capacity, cover_image_name, cover_image_extension, cover_image_width, cover_image_height, cover_image_size, created_at) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            session.getCourseId(),
            session.getStatus(),
            session.getFee().toLongValue(),
            session.getSessionDateRange().getStartDate(),
            session.getSessionDateRange().getEndDate(),
            session.getCapacity().toIntValue(),
            session.getSessionCoverImage().getName(),
            session.getSessionCoverImage().getExtension().name(),
            session.getSessionCoverImage().getDimensions().getWidth(),
            session.getSessionCoverImage().getDimensions().getHeight(),
            session.getSessionCoverImage().getSize().toLongValue(),
            session.getCreatedAt()
        );
    }

    @Override
    public Session findById(final Long id) {
        String sql = "select id, course_id, cover_image_name, cover_image_extension, cover_image_width, cover_image_height, cover_image_size, start_date, end_date, status, fee, capacity, created_at, updated_at from session where id = ?";
        RowMapper<Session> rowMapper = (rs, rowNum) -> new Session(
            rs.getLong(1),
            rs.getLong(2),
            new SessionCoverImage(
                rs.getString(3),
                ImageExtension.supports(rs.getString(4)),
                new ImageDimensions(rs.getInt(5), rs.getInt(6)),
                new ImageSize(rs.getLong(7))
            ),
            new DateRange(toLocalDate(rs.getTimestamp(8)), toLocalDate(rs.getTimestamp(9))),
            SessionStatus.fromName(rs.getString(10)),
            Money.of(rs.getBigDecimal(11).toBigInteger()),
            Capacity.of(rs.getInt(12)),
            toLocalDateTime(rs.getTimestamp(13)),
            toLocalDateTime(rs.getTimestamp(14))
        );
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }

    private LocalDate toLocalDate(final Timestamp timestamp) {
        return toLocalDateTime(timestamp).toLocalDate();
    }
}
