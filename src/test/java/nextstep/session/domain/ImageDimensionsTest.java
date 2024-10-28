package nextstep.session.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ImageDimensionsTest {
    @Test
    @DisplayName("width 가 300px 미만이라면 예외가 발생한다.")
    void shouldThrowExceptionWhenWidthIsLessThan300() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new ImageDimensions(299, 300));
    }

    @Test
    @DisplayName("height 는 200px 미만이라면 예외가 발생한다.")
    void shouldThrowExceptionWhenHeightIsLessThan200() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new ImageDimensions(300, 199));
    }

    @Test
    @DisplayName("width 와 height 의 비율이 3:2 가 아니라면 예외가 발생한다.")
    void shouldThrowExceptionWhenAspectRatioIsNot3To2() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new ImageDimensions(300, 201));
    }

    @Test
    @DisplayName("width, height 가 유효하다면 객체가 생성된다.")
    void shouldCreateImagePropertiesWhenValidWidthAndHeight() {
        assertThat(new ImageDimensions(300, 200)).isNotNull();
    }
}