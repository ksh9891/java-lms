package nextstep.session.infrastructure;

import nextstep.session.domain.*;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;
    private SessionUserRepository sessionUserRepository;
    private SessionCoverImageRepository sessionCoverImageRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
        sessionUserRepository = new JdbcSessionUserRepository(jdbcTemplate);
        sessionCoverImageRepository = new JdbcSessionCoverImageRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        final Session session = new Session(
            0L,
            1L,
            new DateRange(
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 30)
            ),
            SessionStatus.준비중,
            SessionRecruiting.모집중,
            Money.of(BigInteger.valueOf(100_000L)),
            Capacity.of(100),
            LocalDateTime.now(),
            null
        );
        int count = sessionRepository.save(session);

        final SessionUser sessionUser = new SessionUser(1L, new NsUser(1L), SessionRegistrationStatus.승인대기);
        sessionUserRepository.save(sessionUser);

        final SessionCoverImage sessionCoverImage = new SessionCoverImage(
            "커버이미지",
            ImageExtension.GIF,
            new ImageDimensions(300, 200),
            new ImageSize(512L)
        );
        sessionCoverImageRepository.save(1L, sessionCoverImage);

        assertThat(count).isEqualTo(1);
        final Session savedSession = sessionRepository.findById(1L);
        assertThat(session.getSessionStatus()).isEqualTo(savedSession.getSessionStatus());
    }
}
