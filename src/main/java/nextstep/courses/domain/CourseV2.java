package nextstep.courses.domain;

import java.time.LocalDateTime;
import java.util.List;

public class CourseV2 {
    private final Long id;

    private final String title;

    private final int cohort;

    private final Long creatorId;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    private final List<Session> sessionList;

    public CourseV2(final String title, final int cohort, final List<Session> sessionList) {
        this(0L, title, cohort, null, null, null, sessionList);
    }

    public CourseV2(
        final Long id,
        final String title,
        final int cohort,
        final Long creatorId,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt,
        final List<Session> sessionList
    ) {
        if (sessionList == null || sessionList.isEmpty()) {
            throw new IllegalArgumentException("코스에 강의 목록이 존재하지 않습니다.");
        }

        this.id = id;
        this.title = title;
        this.cohort = cohort;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.sessionList = sessionList;
    }
}
