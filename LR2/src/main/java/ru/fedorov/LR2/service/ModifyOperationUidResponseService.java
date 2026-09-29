package ru.fedorov.LR2.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.fedorov.LR2.model.Response;

@Slf4j
@Service
@Qualifier("ModifyOperationUidResponseService")
public class ModifyOperationUidResponseService implements ModifyResponseService {

    @Override
    public Response modify(Response response) {

        log.info("Modifying operationUid, old value: {}", response.getOperationUid());
        UUID uuid = UUID.randomUUID();
        response.setOperationUid(uuid.toString());
        log.info("Modified operationUid, new value: {}", response.getOperationUid());

        return response;

    }

}
