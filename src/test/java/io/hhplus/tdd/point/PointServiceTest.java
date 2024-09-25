package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.service.PointService;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PointServiceTest {

    @Mock
    private UserPointTable userPointTable;      // userPointTable mock 객체

    @Mock
    private PointHistoryTable pointHistoryTable;       // pointHistoryTable mock 객체

    @InjectMocks
    private PointService pointService;      // 실제 테스트할 대상 주입

    @BeforeEach
    void setUp() {
        // Mockito 초기화
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("포인트 조회")
    void testGetUserPoint() {
        // Given - 테스트 데이터 설정
        long userId = 1L;
        UserPoint mockUserPoint = new UserPoint(userId, 100L, System.currentTimeMillis());

        // When - 호출, thenReturn - 반환
        when(userPointTable.selectById(userId)).thenReturn(mockUserPoint);

        UserPoint resultUserPoint = pointService.getUserPoint(userId);

        // Then - 결과 검증.
        assertEquals(mockUserPoint, resultUserPoint);
        verify(userPointTable, times(1)).selectById(userId);    // 한 번 호출됐는지 검증
    }

    @Test
    @DisplayName("포인트 충전/사용 내역 조회")
    void testGetUserPointHistories() {
        // Given - 테스트 데이터 설정
        long userId = 1L;
        List<PointHistory> mockUserHistories = List.of(
                new PointHistory(1L, userId, 1000L, TransactionType.CHARGE, System.currentTimeMillis()),
                new PointHistory(1L, userId, 1000L, TransactionType.USE, System.currentTimeMillis()));

        // When
        when(pointHistoryTable.selectAllByUserId(userId)).thenReturn(mockUserHistories);

        List<PointHistory> resultList = pointService.getUserPointHistories(userId);

        // Then - 결과 검증
        assertEquals(mockUserHistories, resultList);
        verify(pointHistoryTable, times(1)).selectAllByUserId(userId);

        // CHARGE, USE 타입이 모두 있는지 타입 확인
        assertEquals(TransactionType.CHARGE, resultList.get(0).type());
        assertEquals(TransactionType.USE, resultList.get(1).type());

    }

    @Test
    @DisplayName("포인트 충전")
    void testChargeUserPoint() {
        // Given
        long userId = 1L;
        long amount = 1000L;
        UserPoint mockUserPoint = new UserPoint(userId, 2000L, System.currentTimeMillis());

        // When
        when(userPointTable.insertOrUpdate(userId, amount)).thenReturn(mockUserPoint);

        UserPoint resultUserPoint = pointService.chareUserPoint(userId, amount);

        // Then
        assertEquals(mockUserPoint, resultUserPoint);
        verify(userPointTable, times(1)).insertOrUpdate(userId, amount);
    }

    @Test
    @DisplayName("포인트 사용")
    void testUseUserPoint() {
        // Given
        long userId = 1L;
        long amount = 1000L;
        UserPoint mockUserPoint = new UserPoint(userId, 2000L, System.currentTimeMillis());

        // When
        when(userPointTable.insertOrUpdate(userId, amount)).thenReturn(mockUserPoint);

        UserPoint resultUserPoint = pointService.useUserPoint(userId, amount);

        // Then
        assertEquals(mockUserPoint, resultUserPoint);
        verify(userPointTable, times(1)).insertOrUpdate(userId, amount);

    }
}
