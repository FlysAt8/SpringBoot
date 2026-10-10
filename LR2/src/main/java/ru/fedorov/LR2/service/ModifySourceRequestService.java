package ru.fedorov.LR2.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.fedorov.LR2.model.Request;

@Slf4j
@Service
@Qualifier("ModifySourceRequestService")
public class ModifySourceRequestService implements ModifyRequestService {

    @Override
    public void modify(Request request) {
        log.info("Modifying source, old value: {}", request.getSource());
        request.setSource("Source Service 1");
        log.info("Modified source, new value: {}", request.getSource());
    }

}
