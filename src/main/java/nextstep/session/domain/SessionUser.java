package nextstep.session.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class SessionUser {
    private final NsUser user;
    private final Session session;
    private final Payment payment;

    public SessionUser(final NsUser user, final Session session) {
        this(user, session, null);
    }

    public SessionUser(final NsUser user, final Session session, final Payment payment) {
        this.user = user;
        this.session = session;
        this.payment = payment;
    }

    public boolean hasPaidFee(final Money fee) {
        if (payment == null) {
            return false;
        }

        return payment.isEqualsFee(fee);
    }
}
