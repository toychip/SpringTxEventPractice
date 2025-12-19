package com.stock.txpractice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TxService {

    private final CoreRepository coreRepository;

    @Transactional
    public void saveCore(CoreEntity coreEntity) {
        coreRepository.save(coreEntity);
        Events.raise(coreEntity);
    }
}
