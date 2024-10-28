package nextstep.payments.domain;

import nextstep.session.domain.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PaymentTest {
    @Test
    @DisplayName("결제금액이 없다면 예외가 발생한다.")
    void shouldThrowExceptionWhenPaymentAmountIsNull() {
        final Payment payment = new Payment();

        assertThatIllegalArgumentException()
            .isThrownBy(() -> payment.isEqualsFee(Money.of(BigInteger.valueOf(100))));
    }

    @Test
    @DisplayName("입력받은 금액과 일치하면 true 를 반환한다.")
    void shouldReturnTrueWhenPaymentAmountMatches() {
        final Payment payment = new Payment("test001", 1L, 1L, 1000L);

        assertThat(payment.isEqualsFee(Money.of(BigInteger.valueOf(1000)))).isTrue();
    }

    @Test
    @DisplayName("입력받은 금액과 일치하지 않으면 false 를 반환한다.")
    void shouldReturnFalseWhenPaymentAmountDoesNotMatch() {
        final Payment payment = new Payment("test001", 1L, 1L, 1000L);

        assertThat(payment.isEqualsFee(Money.of(BigInteger.valueOf(900)))).isFalse();
    }
}