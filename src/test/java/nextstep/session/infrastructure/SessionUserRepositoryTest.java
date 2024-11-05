package nextstep.session.infrastructure;

import nextstep.session.domain.Session;
import nextstep.session.domain.SessionUser;
import nextstep.session.domain.SessionUserRepository;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionUserRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionUserRepository sessionUserRepository;

    @BeforeEach
    void setUp() {
        sessionUserRepository = new JdbcSessionUserRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        final SessionUser sessionUser = new SessionUser(
            new NsUser(1L),
            new Session(1L)
        );
        int count = sessionUserRepository.save(sessionUser);
        assertThat(count).isEqualTo(1);
        final SessionUser savedSessionUser = sessionUserRepository.findById(1L, 1L);
        assertThat(sessionUser.getUser().getId()).isEqualTo(savedSessionUser.getUser().getId());
    }
}
