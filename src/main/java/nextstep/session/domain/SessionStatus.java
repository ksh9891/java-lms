package nextstep.session.domain;

import java.util.Arrays;

public enum SessionStatus {
    준비중, 진행중, 종료;

    public static SessionStatus fromName(final String name) {
        return Arrays.stream(values())
            .filter(value -> value.name().equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("상태를 찾을 수 없습니다."));
    }
}
