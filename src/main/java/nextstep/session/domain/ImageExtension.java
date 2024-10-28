package nextstep.session.domain;

import java.util.Arrays;

public enum ImageExtension {
    GIF, JPG, JPEG, PNG, SVG;

    public static ImageExtension supports(final String extension) {
        return Arrays.stream(values())
            .filter(value -> value.name().equalsIgnoreCase(extension))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 확장자입니다."));
    }
}
