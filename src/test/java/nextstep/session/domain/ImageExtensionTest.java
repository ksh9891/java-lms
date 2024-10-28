package nextstep.session.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ImageExtensionTest {
    @Test
    @DisplayName("지정된 확장자 외의 확장자가 전달된 경우 예외가 발생한다.")
    void shouldThrowExceptionWhenUnsupportedExtensionProvided() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> ImageExtension.supports("html"));
    }

    @Test
    @DisplayName("지원하지 않는 확장자가 전달된 경우 해당 객체를 반환한다.")
    void shouldReturnObjectWhenUnsupportedExtensionProvided() {
        final ImageExtension imageExtension = ImageExtension.supports("png");

        assertThat(imageExtension).isEqualTo(ImageExtension.PNG);
    }
}