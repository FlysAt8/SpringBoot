package ru.fedorov.Service2.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.fedorov.Service2.model.Response;
import ru.fedorov.Service2.util.DateTimeUtil;

import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;

@Slf4j
@Service
@Qualifier("ModifySystemTimeResponseService")
public class ModifySystemTimeResponseService implements ModifyResponseService {

    @Override
    public Response modify(Response response) {
        log.info("Modifying systemTime, old value: {}", response.getSystemTime());
        response.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));
        log.info("Modified systemTime, new value: {}", response.getSystemTime());

        return response;
    }

}
