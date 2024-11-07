package nextstep.session.domain;

import java.util.Arrays;

public enum SessionRecruiting {
    비모집중, 모집중;

    public boolean isRecruit() {
        return this == 모집중;
    }

    public static SessionRecruiting fromName(final String name) {
        return Arrays.stream(values())
            .filter(value -> value.name().equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("상태를 찾을 수 없습니다."));
    }
}
