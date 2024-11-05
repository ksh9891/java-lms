package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class CourseTest {
    @Test
    @DisplayName("기수가 1보다 작은 수가 지정 되었다면 예외가 발생한다.")
    void shouldThrowExceptionWhenCohortIsNotProvided() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Course(1L, "TDD, 클린 코드 with Java", 0, 1L, LocalDateTime.now(), null, Collections.emptyList()));
    }

    @Test
    @DisplayName("기수와 Session 을 포함한다면 Course 는 생성된다.")
    void shouldCreateCourseWhenCohortAndSessionAreProvided() {
        final Course course = new Course(
            1L,
            "TDD, 클린 코드 with Java",
            40,
            1L,
            LocalDateTime.now(),
            null
        );

        assertThat(course).isNotNull();
    }
}
