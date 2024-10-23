package nextstep.courses;

import nextstep.courses.domain.CourseV2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class CourseTest {
    @Test
    @DisplayName("Session 이 없다면 Course 생성 시 예외가 발생한다.")
    void shouldThrowExceptionWhenSessionDoesNotExist() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new CourseV2("TDD, 클린 코드 with Java", 40, Collections.emptyList()));
    }
}
