package nextstep.session.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class MoneyTest {
    @Test
    @DisplayName("입력금액이 null 이면 예외가 발생한다.")
    void shouldThrowExceptionWhenMoneyIsNull() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Money.of(null));
    }

    @Test
    @DisplayName("입력금액이 음수이면 예외가 발생한다.")
    void shouldThrowExceptionWhenMoneyIsNegative() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Money.of(BigInteger.valueOf(-1)));
    }

    @Test
    @DisplayName("입력금액과 금액이 같으면 true 를 반환한다.")
    void shouldReturnTrueWhenAmountsAreEqual() {
        final Money money = Money.of(BigInteger.valueOf(1000));
        final Money otherMoney = Money.of(BigInteger.valueOf(1000));

        assertThat(money.isEqualTo(otherMoney)).isTrue();
    }

    @Test
    @DisplayName("입력금액과 금액이 다르면 false 를 반환한다.")
    void shouldReturnFalseWhenAmountsAreNotEqual() {
        final Money money = Money.of(BigInteger.valueOf(1000));
        final Money otherMoney = Money.of(BigInteger.valueOf(5000));

        assertThat(money.isEqualTo(otherMoney)).isFalse();
    }
}