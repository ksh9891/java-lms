package nextstep.session.infrastructure;

import nextstep.session.domain.SessionUser;
import nextstep.session.domain.SessionUserRepository;
import nextstep.session.domain.SessionUsers;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository("sessionUserRepository")
public class JdbcSessionUserRepository implements SessionUserRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionUserRepository(final JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(final SessionUser sessionUser) {
        final String sql = "insert into session_users(session_id, ns_user_id, created_at) values(?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            sessionUser.getSessionId(),
            sessionUser.getUserId(),
            sessionUser.getCreatedAt()
        );
    }

    @Override
    public SessionUsers findById(final Long sessionId) {
        String sql = "select session_id, ns_user_id, created_at, updated_at from session_users where session_id = ?";
        RowMapper<SessionUser> rowMapper = (rs, rowNum) -> new SessionUser(
            rs.getLong(1),
            rs.getLong(2),
            toLocalDateTime(rs.getTimestamp(3)),
            toLocalDateTime(rs.getTimestamp(4))
        );
        final List<SessionUser> sessionUser = jdbcTemplate.query(sql, rowMapper, sessionId);
        return new SessionUsers(sessionUser);
    }

    @Override
    public SessionUser findByIdAndUserId(final Long sessionId, final Long userId) {
        String sql = "select session_id, ns_user_id, created_at, updated_at from session_users where session_id = ? and ns_user_id = ?";
        RowMapper<SessionUser> rowMapper = (rs, rowNum) -> new SessionUser(
            rs.getLong(1),
            rs.getLong(2),
            toLocalDateTime(rs.getTimestamp(3)),
            toLocalDateTime(rs.getTimestamp(4))
        );
        return jdbcTemplate.queryForObject(sql, rowMapper, sessionId, userId);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
