package nextstep.session.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CapacityTest {
    @Test
    @DisplayName("인원 제한이 있다면 true 를 반환한다.")
    void shouldReturnTrueWhenCapacityHasLimit() {
        final Capacity capacity = Capacity.of(10);

        Assertions.assertThat(capacity.hasLimit()).isTrue();
    }

    @Test
    @DisplayName("인원 제한이 없다면 false 를 반환한다.")
    void shouldReturnFalseWhenCapacityHasNoLimit() {
        final Capacity capacity = Capacity.noLimit();

        Assertions.assertThat(capacity.hasLimit()).isFalse();
    }
}
