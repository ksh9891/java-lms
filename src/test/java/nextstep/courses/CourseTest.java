package nextstep.courses;

import nextstep.courses.domain.CourseV2;
import nextstep.courses.domain.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class CourseTest {
    @Test
    @DisplayName("Session 이 없다면 Course 생성 시 예외가 발생한다.")
    void shouldThrowExceptionWhenSessionDoesNotExist() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new CourseV2("TDD, 클린 코드 with Java", 40, Collections.emptyList()));
    }

    @Test
    @DisplayName("기수가 1보다 작은 수가 지정 되었다면 예외가 발생한다.")
    void shouldThrowExceptionWhenCohortIsNotProvided() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new CourseV2("TDD, 클린 코드 with Java", 0, Collections.emptyList()));
    }

    @Test
    @DisplayName("기수와 Session 을 포함한다면 Course 는 생성된다.")
    void shouldCreateCourseWhenCohortAndSessionAreProvided() {
        final CourseV2 courseV2 = new CourseV2("TDD, 클린 코드 with Java", 40, List.of(
            new Session(),
            new Session(),
            new Session()
        ));

        assertThat(courseV2).isNotNull();
    }
}
