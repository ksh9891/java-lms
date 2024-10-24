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

    @Test
    @DisplayName("종료일은 시작일 이전으로 설정이 불가능하다.")
    void shouldNotAllowEndDateBeforeStartDate() {
        final LocalDate startDate = LocalDate.of(2024, 12, 31);
        final LocalDate endDate = LocalDate.of(2024, 1, 1);

        assertThatIllegalArgumentException()
            .isThrownBy(() -> new Session(startDate, endDate));
    }
}
