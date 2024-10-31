package nextstep.session.domain;

public enum SessionStatus {
    준비중, 모집중, 종료;

    public boolean isRecruit() {
        return this == 모집중;
    }
}
