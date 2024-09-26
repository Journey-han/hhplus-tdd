package io.hhplus.tdd.point.util;

import org.springframework.stereotype.Component;

@Component
public class PointValidator {

    private static final long MAX_BALANCE = 100000L;  // 최대 잔고
    private static final long MIN_CHARGE_AMOUNT = 100L;  // 최소 충전 금액

    // 포인트 충전 검증
    public void validateCharge(long currentAmount, long amount) {
        if (amount < MIN_CHARGE_AMOUNT) {
            throw new IllegalArgumentException("최소 충전 금액은 " + MIN_CHARGE_AMOUNT + " 이상이어야 합니다.");
        }

        if (currentAmount + amount > MAX_BALANCE) {
            throw new IllegalArgumentException("최대 잔고를 초과했습니다.");
        }
    }

    // 포인트 사용 검증
    public void validateUse(long currentAmount, long amount) {
        if (currentAmount < amount) {
            throw new IllegalArgumentException("잔고가 부족합니다.");
        }
    }

}
