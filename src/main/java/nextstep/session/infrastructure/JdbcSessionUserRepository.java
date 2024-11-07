package nextstep.session.infrastructure;

import nextstep.session.domain.SessionRegistrationStatus;
import nextstep.session.domain.SessionUser;
import nextstep.session.domain.SessionUserRepository;
import nextstep.session.domain.SessionUsers;
import nextstep.users.domain.NsUser;
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
        final String sql = "insert into session_users(session_id, ns_user_id, status, created_at) values(?, ?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            sessionUser.getSessionId(),
            sessionUser.getUserId(),
            sessionUser.getStatus().name(),
            sessionUser.getCreatedAt()
        );
    }

    @Override
    public SessionUsers findById(final Long sessionId) {
        String sql = "select session_id, ns_user_id, status, created_at, updated_at from session_users where session_id = ?";
        RowMapper<SessionUser> rowMapper = (rs, rowNum) -> new SessionUser(
            rs.getLong("session_id"),
            new NsUser(rs.getLong("ns_user_id")),
            SessionRegistrationStatus.fromName(rs.getString("status")),
            toLocalDateTime(rs.getTimestamp("created_at")),
            toLocalDateTime(rs.getTimestamp("updated_at"))
        );
        final List<SessionUser> sessionUser = jdbcTemplate.query(sql, rowMapper, sessionId);
        return new SessionUsers(sessionUser);
    }

    @Override
    public SessionUser findByIdAndUserId(final Long sessionId, final Long userId) {
        String sql = "select session_id, ns_user_id, status, created_at, updated_at from session_users where session_id = ? and ns_user_id = ?";
        RowMapper<SessionUser> rowMapper = (rs, rowNum) -> new SessionUser(
            rs.getLong("session_id"),
            new NsUser(rs.getLong("ns_user_id")),
            SessionRegistrationStatus.fromName(rs.getString("status")),
            toLocalDateTime(rs.getTimestamp("created_at")),
            toLocalDateTime(rs.getTimestamp("updated_at"))
        );
        return jdbcTemplate.queryForObject(sql, rowMapper, sessionId, userId);
    }

    @Override
    public int updateStatus(final SessionUser sessionUser) {
        String sql = "update session_users set status = ?, updated_at = ? where session_id = ? and ns_user_id = ?";
        return jdbcTemplate.update(sql, sessionUser.getStatus().name(), LocalDateTime.now(), sessionUser.getSessionId(), sessionUser.getUserId());
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
