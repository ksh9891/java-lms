package nextstep.courses.domain;

import nextstep.session.domain.Session;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Course {
    private Long id;

    private String title;

    private int cohort;

    private Long creatorId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Session> sessionList;

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
                  final LocalDateTime updatedAt
    ) {
        this(id, title, cohort, creatorId, createdAt, updatedAt, new ArrayList<>());
    }

    public Course(final Long id,
                  final String title,
                  final int cohort,
                  final Long creatorId,
                  final LocalDateTime createdAt,
                  final LocalDateTime updatedAt,
                  final List<Session> sessionList
    ) {
        if (cohort < 1) {
            throw new IllegalArgumentException("기수는 최소 1이상이어야 합니다.");
        }

        this.id = id;
        this.title = title;
        this.cohort = cohort;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.sessionList = Optional.ofNullable(sessionList).orElseGet(ArrayList::new);
    }

    public String getTitle() {
        return title;
    }

    public int getCohort() {
        return cohort;
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
