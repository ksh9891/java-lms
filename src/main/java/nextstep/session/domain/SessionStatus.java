package nextstep.session.domain;

public enum SessionStatus {
    준비중(false), 모집중(true), 종료(false);

    private final boolean isRecruit;

    SessionStatus(boolean isRecruit) {
        this.isRecruit = isRecruit;
    }

    public boolean isRecruit() {
        return isRecruit;
    }
}
