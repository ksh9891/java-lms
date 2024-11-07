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
        final String sql = "insert into session(course_id, session_status, recruit_status, fee, start_date, end_date, capacity, cover_image_name, cover_image_extension, cover_image_width, cover_image_height, cover_image_size, created_at) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            session.getCourseId(),
            session.getSessionStatus(),
            session.getSessionRecruiting(),
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
        String sql = "select id, course_id, cover_image_name, cover_image_extension, cover_image_width, cover_image_height, cover_image_size, start_date, end_date, session_status, recruit_status, fee, capacity, created_at, updated_at from session where id = ?";
        RowMapper<Session> rowMapper = (rs, rowNum) -> new Session(
            rs.getLong("id"),
            rs.getLong("course_id"),
            new SessionCoverImage(
                rs.getString("cover_image_name"),
                ImageExtension.supports(rs.getString("cover_image_extension")),
                new ImageDimensions(rs.getInt("cover_image_width"), rs.getInt("cover_image_height")),
                new ImageSize(rs.getLong("cover_image_size"))
            ),
            new DateRange(toLocalDate(rs.getTimestamp("start_date")), toLocalDate(rs.getTimestamp("end_date"))),
            SessionStatus.fromName(rs.getString("session_status")),
            SessionRecruiting.fromName(rs.getString("recruit_status")),
            Money.of(rs.getBigDecimal("fee").toBigInteger()),
            Capacity.of(rs.getInt("capacity")),
            toLocalDateTime(rs.getTimestamp("created_at")),
            toLocalDateTime(rs.getTimestamp("updated_at"))
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
