package nextstep.session.domain;

import java.util.Arrays;

public enum SessionRegistrationStatus {
    승인대기, 승인, 미승인;

    public static SessionRegistrationStatus fromName(final String name) {
        return Arrays.stream(values())
            .filter(value -> value.name().equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("상태를 찾을 수 없습니다."));
    }
}
