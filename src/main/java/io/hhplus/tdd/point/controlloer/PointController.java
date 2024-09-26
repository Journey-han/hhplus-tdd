package io.hhplus.tdd.point.controlloer;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.service.PointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    PointService pointService;

    private static final Logger log = LoggerFactory.getLogger(PointController.class);

    /**
     * TODO - 특정 유저의 포인트를 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}")
    public UserPoint point(@PathVariable long id) {
        UserPoint point = pointService.getUserPoint(id);
        log.debug("point => {}", point);
        System.out.println(point);
        return point;
    }

    /**
     * TODO - 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}/histories")
    public List<PointHistory> history(@PathVariable long id) {
        List<PointHistory> historyList = pointService.getUserPointHistories(id);
        log.debug("histories => {}", historyList);
        System.out.println(historyList);
        return historyList;
    }

    /**
     * TODO - 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/charge")
    public UserPoint charge(@PathVariable long id, @RequestBody long amount) {
        UserPoint chareUserPoint = pointService.chareUserPoint(id, amount);
        log.debug("chargeUserPoint => {}", chareUserPoint);
        System.out.println(chareUserPoint);
        return chareUserPoint;
    }

    /**
     * TODO - 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/use")
    public UserPoint use(@PathVariable long id, @RequestBody long amount) {
        UserPoint useUserPoint = pointService.useUserPoint(id, amount);
        log.debug("useUserPoint => {}", useUserPoint);
        System.out.println(useUserPoint);
        return useUserPoint;
    }
}
