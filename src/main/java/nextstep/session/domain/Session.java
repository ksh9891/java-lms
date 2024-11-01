package nextstep.session.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class Session {
    private final Long id;
    private final Long courseId;
    private final SessionCoverImage sessionCoverImage;
    private final DateRange sessionDateRange;
    private final SessionStatus sessionStatus;
    private final Money fee;
    private final Capacity capacity;
    private final SessionUsers sessionUsers;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Session(final Long id,
                    final Long courseId,
                    final DateRange sessionDateRange,
                    final SessionStatus sessionStatus,
                    final Money fee,
                    final Capacity capacity
    ) {
        this(id, courseId, null, sessionDateRange, sessionStatus, fee, capacity, LocalDateTime.now(), null);
    }

    public Session(final Long id,
                   final Long courseId,
                   final SessionCoverImage sessionCoverImage,
                   final DateRange sessionDateRange,
                   final SessionStatus sessionStatus,
                   final Money fee,
                   final Capacity capacity,
                   final LocalDateTime createdAt,
                   final LocalDateTime updatedAt
    ) {
        this(id, courseId, sessionCoverImage, sessionDateRange, sessionStatus, fee, capacity, new SessionUsers(), createdAt, updatedAt);
    }

    public Session(final Long id,
                   final Long courseId,
                   final SessionCoverImage sessionCoverImage,
                   final DateRange sessionDateRange,
                   final SessionStatus sessionStatus,
                   final Money fee,
                   final Capacity capacity,
                   final SessionUsers sessionUsers,
                   final LocalDateTime createdAt,
                   final LocalDateTime updatedAt
    ) {
        validationSession(sessionDateRange, fee);

        this.id = id;
        this.courseId = courseId;
        this.sessionCoverImage = sessionCoverImage;
        this.sessionDateRange = sessionDateRange;
        this.sessionStatus = sessionStatus;
        this.fee = fee;
        this.capacity = capacity;
        this.sessionUsers = sessionUsers;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private static void validationSession(final DateRange sessionDateRange, final Money fee) {
        if (sessionDateRange == null) {
            throw new IllegalArgumentException("세션 기간은 필수압니다.");
        }

        if (fee == null) {
            throw new IllegalArgumentException("세션 비용은 필수입니다.");
        }
    }

    public static Session freeSession(final Long id,
                                      final Long courseId,
                                      final DateRange sessionDateRange,
                                      final SessionStatus sessionStatus
    ) {
        return new Session(id, courseId, sessionDateRange, sessionStatus, Money.of(BigInteger.ZERO), Capacity.noLimit());
    }

    public static Session paidSession(final Long id,
                                      final Long courseId,
                                      final DateRange sessionDateRange,
                                      final SessionStatus sessionStatus,
                                      final Money fee,
                                      final Capacity capacity
    ) {
        return new Session(id, courseId, sessionDateRange, sessionStatus, fee, capacity);
    }

    public void apply(final NsUser nsUser) {
        apply(nsUser, null);
    }

    public void apply(final NsUser nsUser, final Payment payment) {
        if (!isRecruiting()) {
            throw new IllegalStateException("모집 기간이 아닙니다.");
        }

        if (!isFree()) {
            validationPaidSession(payment);
        }

        sessionUsers.add(new SessionUser(nsUser, this));
    }

    private void validationPaidSession(final Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("결제정보가 존재하지 않습니다.");
        }

        if (!payment.isEqualsFee(fee)) {
            throw new IllegalArgumentException("수강료를 지불하지 않았습니다.");
        }

        if (capacity.isFull(sessionUsers.size())) {
            throw new IllegalStateException("수강생이 가득찼습니다.");
        }
    }

    public boolean hasApplied(final NsUser nsUser) {
        final SessionUser sessionUser = new SessionUser(nsUser, this);
        return sessionUsers.contains(sessionUser);
    }

    public boolean matchSession(final Session target) {
        return id.equals(target.id);
    }

    public boolean hasLimit() {
        return capacity.hasLimit();
    }

    private boolean isRecruiting() {
        return sessionStatus.isRecruit();
    }

    private boolean isFree() {
        return fee.isEqualTo(Money.ZERO);
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getStatus() {
        return sessionStatus.name();
    }

    public Money getFee() {
        return fee;
    }

    public DateRange getSessionDateRange() {
        return sessionDateRange;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public SessionCoverImage getSessionCoverImage() {
        return sessionCoverImage;
    }
}
