package nextstep.courses;

import nextstep.courses.domain.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class SessionTest {
    @Test
    @DisplayName("시작일과 종료일 중 어느하나라도 설정되지 않은 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenStartDateOrEndDateIsMissing() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Session(LocalDate.now(), null));
    }
}
