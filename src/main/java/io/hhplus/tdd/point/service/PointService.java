package io.hhplus.tdd.point.service;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointService {

    @Autowired
    UserPointTable userPointTable;

    @Autowired
    PointHistoryTable pointHistoryTable;

    // 포인트 조회
    public UserPoint getUserPoint(long id) {
        return userPointTable.selectById(id);
    }

    // 포인트 충전/사용 내역 조회
    public List<PointHistory> getUserPointHistories(long id) {
        return pointHistoryTable.selectAllByUserId(id);
    }

    // 포인트 충전
    public UserPoint chareUserPoint(long id, long amount) {
        UserPoint userPoint = userPointTable.insertOrUpdate(id, amount);

        // 포인트 충전 내역 insert
        pointHistoryTable.insert(id, amount, TransactionType.CHARGE, System.currentTimeMillis());

        return userPoint;
    }

    // 포인트 사용
    public UserPoint useUserPoint(long id, long amount) {
        UserPoint userPoint = userPointTable.insertOrUpdate(id, amount);

        // 포인트 사용 내역 insert
        pointHistoryTable.insert(id, amount, TransactionType.USE, System.currentTimeMillis());

        return userPoint;
    }


}
