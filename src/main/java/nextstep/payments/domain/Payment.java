package nextstep.payments.domain;

import nextstep.session.domain.Money;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class Payment {
    private String id;

    // 결제한 강의 아이디
    private Long sessionId;

    // 결제한 사용자 아이디
    private Long nsUserId;

    // 결제 금액
    private Long amount;

    private LocalDateTime createdAt;

    public Payment() {
    }

    public Payment(String id, Long sessionId, Long nsUserId, Long amount) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public boolean isEqualsFee(final Money sessionFee) {
        if (amount == null) {
            throw new IllegalArgumentException("결제 금액이 존재하지 않습니다.");
        }

        final Money fee = Money.of(BigInteger.valueOf(amount));
        return fee.isEqualTo(sessionFee);
    }
}
