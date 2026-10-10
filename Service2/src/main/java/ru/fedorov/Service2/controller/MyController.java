package ru.fedorov.Service2.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ru.fedorov.Service2.exception.UnsupportedCodeException;
import ru.fedorov.Service2.exception.ValidationFailedException;
import ru.fedorov.Service2.model.Codes;
import ru.fedorov.Service2.model.ErrorCodes;
import ru.fedorov.Service2.model.ErrorMessages;
import ru.fedorov.Service2.model.Request;
import ru.fedorov.Service2.model.Response;
import ru.fedorov.Service2.service.ModifyResponseService;
import ru.fedorov.Service2.service.ValidationService;
import ru.fedorov.Service2.util.DateTimeUtil;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;

    private final ModifyResponseService modifySystemTimeResponseService;

    private final ModifyResponseService modifyOperationUidResponseService;

    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") 
                            ModifyResponseService modifySystemTimeResponseService,
                        @Qualifier("ModifyOperationUidResponseService") 
                            ModifyResponseService modifyOperationUidResponseService
    ){
        this.validationService=validationService;
        this.modifySystemTimeResponseService=modifySystemTimeResponseService;
        this.modifyOperationUidResponseService=modifyOperationUidResponseService;
    }

    @PostMapping("/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
        BindingResult bindingResults) {

        log.info("request: {}", request);

        Response response = Response.builder()
                                .uid(request.getUid())
                                .operationUid(request.getOperationUid())
                                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                                .code(Codes.SUCCESS)
                                .errorCode(ErrorCodes.EMPTY)
                                .errorMessage(ErrorMessages.EMPTY)
                                .build();
        log.info("Response created: {}", response);

        try {
            validationService.isValid(bindingResults); 
            
            if ("123".equals(request.getUid())) {
                throw new UnsupportedCodeException("uid = 123 не поддерживается");
            }

        } catch (ValidationFailedException e) {
            log.error("Validation failed: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("Response modified (validation failed): {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            log.error("Unsupported code: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            log.info("Response modified (unsupported code): {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch(Exception ee) {
            log.error("Unknown exception: {}", ee.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("Response modified (unknown exception): {}", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        modifySystemTimeResponseService.modify(response);
        modifyOperationUidResponseService.modify(response);
        log.info("Response final: {}", response);

        try {
            Date sentTime = DateTimeUtil.getCustomFormat().parse(request.getSystemTime());
            long diffMs = System.currentTimeMillis() - sentTime.getTime();
            log.info("Time diff (Service 1 - Service 2): {} ms", diffMs);
        } catch (ParseException e) {
            log.error("Cannot parse systemTime: {}", e.getMessage());
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
