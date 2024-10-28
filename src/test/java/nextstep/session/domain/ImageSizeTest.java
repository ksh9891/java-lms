package nextstep.session.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ImageSizeTest {
    @Test
    @DisplayName("1MB 를 초과하는 크키가 전달된 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenSizeExceeds1MB() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new ImageSize(1025));
    }

    @Test
    @DisplayName("1MB 이하의 크기가 전달된 경우 정상적으로 생성된다.")
    void shouldCreateWhenFileSizeIsLessThanOrEqualTo1MB() {
        assertThat(new ImageSize(1024)).isEqualTo(new ImageSize(1024));
    }
}