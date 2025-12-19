package com.stock.txpractice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CoreEventListener {

    private final LogRepository logRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCoreSaveEvent(CoreEntity coreEntity) {
        System.out.println("coreEntity.getId() = " + coreEntity.getId());
        System.out.println("CoreEntity 저장 성공");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCoreSaveEvent(PureEvent pureEvent) {
        System.out.println("pureEvent.localDateTime() = " + pureEvent.localDateTime());
        System.out.println("PureEvnet가 발생한 트랜잭션이 종료되고 Listen까지 끝");
    }

    @Transactional
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCoreSaveEventAndSaveLogEventFailed(CoreEntity coreEntity) {
        System.out.println("coreEntity.getId() = " + coreEntity.getId());
        System.out.println("CoreEntity 저장 성공");
        System.out.println("LogEvent 저장 시도");
        logRepository.save(new LogEntity(coreEntity.getId() + "로 인해 만들어진 LogEntity"));
        System.out.println("무조건 저장 실패");
    }

    @Transactional
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCoreSaveEventAndSaveLogEventSuccess1(CoreEntity coreEntity) {
        System.out.println("coreEntity.getId() = " + coreEntity.getId());
        System.out.println("CoreEntity 저장 성공");
        System.out.println("LogEvent 저장 시도");
        logRepository.save(new LogEntity(coreEntity.getId() + "로 인해 만들어진 LogEntity"));
        System.out.println("별도의 스레드에서 데이터커넥션을 휙득하여 LogEntity 저장 성공");
    }
}
