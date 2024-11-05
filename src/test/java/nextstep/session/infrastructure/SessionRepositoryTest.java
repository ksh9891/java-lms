package nextstep.session.infrastructure;

import nextstep.session.domain.*;
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

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        final Session session = new Session(
            0L,
            1L,
            new SessionCoverImage(
                "커버이미지",
                ImageExtension.GIF,
                new ImageDimensions(300, 200),
                new ImageSize(512L)
            ),
            new DateRange(
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 30)
            ),
            SessionStatus.모집중,
            Money.of(BigInteger.valueOf(100_000L)),
            Capacity.of(100),
            LocalDateTime.now(),
            null
        );
        int count = sessionRepository.save(session);
        assertThat(count).isEqualTo(1);
        final Session savedSession = sessionRepository.findById(1L);
        assertThat(session.getStatus()).isEqualTo(savedSession.getStatus());
    }
}
