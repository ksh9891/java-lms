package nextstep.courses.domain;

import nextstep.session.domain.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private Long id;

    private String title;

    private int cohort;

    private Long creatorId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Session> sessionList;

    public Course() {
    }

    public Course(final String title, final Long creatorId) {
        this(0L, title, creatorId, LocalDateTime.now(), null);
    }

    public Course(final Long id,
                  final String title,
                  final Long creatorId,
                  final LocalDateTime createdAt,
                  final LocalDateTime updatedAt
    ) {
        this(id, title, 1, creatorId, createdAt, updatedAt, new ArrayList<>());
    }

    public Course(final Long id,
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

        if (cohort < 1) {
            throw new IllegalArgumentException("기수는 최소 1이상이어야 합니다.");
        }

        this.id = id;
        this.title = title;
        this.cohort = cohort;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.sessionList = sessionList;
    }

    public String getTitle() {
        return title;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creatorId=" + creatorId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
