package nextstep.session.infrastructure;

import nextstep.session.domain.Session;
import nextstep.session.domain.SessionUser;
import nextstep.session.domain.SessionUserRepository;
import nextstep.users.domain.NsUser;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
            sessionUser.getSession().getId(),
            sessionUser.getUser().getId(),
            sessionUser.getCreatedAt()
        );
    }

    @Override
    public SessionUser findById(final Long sessionId, final Long userId) {
        String sql = "select id, session_id, ns_user_id, created_at, updated_at from session_users where session_id = ? and ns_user_id = ?";
        RowMapper<SessionUser> rowMapper = (rs, rowNum) -> new SessionUser(
            rs.getLong(1),
            new NsUser(rs.getLong(2)),
            new Session(rs.getLong(3)),
            toLocalDateTime(rs.getTimestamp(4)),
            toLocalDateTime(rs.getTimestamp(5))
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
