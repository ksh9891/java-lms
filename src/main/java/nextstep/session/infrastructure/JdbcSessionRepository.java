package nextstep.session.infrastructure;

import nextstep.session.domain.*;
import nextstep.users.domain.NsUser;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "select s.id, s.course_id, s.cover_image_name, s.cover_image_extension, s.cover_image_width, s.cover_image_height, s.cover_image_size, " +
            "s.start_date, s.end_date, s.session_status, s.recruit_status, s.fee, s.capacity, s.created_at, s.updated_at, " +
            "su.session_id, su.ns_user_id, nu.user_id, su.created_at as session_users_created_at, su.updated_at as session_users_updated_at " +
            "from session s left join session_users su on s.id = su.session_id left join ns_user nu on su.ns_user_id = nu.id " +
            "where s.id = ?";

        return jdbcTemplate.query(sql, rs -> {
            Session session = null;
            int row = 0;
            final SessionUsers sessionUsers = new SessionUsers();
            while (rs.next()) {
                if (session == null) {
                    session = sessionRowMapper().mapRow(rs, row);
                }

                if (session != null) {
                    final SessionUser sessionUser = SessionUserRowMapper().mapRow(rs, row);
                    sessionUsers.add(sessionUser);
                    row++;
                }
            }

            if (session != null) {
                session.addSessionUsers(sessionUsers);
            }

            return session;
        }, id);
    }

    private RowMapper<SessionUser> SessionUserRowMapper() {
        return (rs, rowNum) -> new SessionUser(
            rs.getLong("session_id"),
            new NsUser(rs.getLong("ns_user_id"), rs.getString("user_id")),
            toLocalDateTime(rs.getTimestamp("session_users_created_at")),
            toLocalDateTime(rs.getTimestamp("session_users_updated_at"))
        );
    }

    private RowMapper<Session> sessionRowMapper() {
        return (rs, rowNum) -> new Session(
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
