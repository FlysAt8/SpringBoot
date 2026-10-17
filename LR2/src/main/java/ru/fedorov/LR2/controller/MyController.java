package ru.fedorov.LR2.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ru.fedorov.LR2.exception.UnsupportedCodeException;
import ru.fedorov.LR2.exception.ValidationFailedException;
import ru.fedorov.LR2.model.Codes;
import ru.fedorov.LR2.model.ErrorCodes;
import ru.fedorov.LR2.model.ErrorMessages;
import ru.fedorov.LR2.model.Request;
import ru.fedorov.LR2.model.Response;
import ru.fedorov.LR2.service.AnnualBonusService;
import ru.fedorov.LR2.service.ModifyRequestService;
import ru.fedorov.LR2.service.ModifyResponseService;
import ru.fedorov.LR2.service.ValidationService;
import ru.fedorov.LR2.util.DateTimeUtil;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifySystemTimeResponseService;
    private final ModifyRequestService modifySourceRequestService;
    private final AnnualBonusService annualBonusService;

    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService")
                            ModifyResponseService modifySystemTimeResponseService,
                        @Qualifier("ModifySourceRequestService")
                            ModifyRequestService modifySourceRequestService,
                        AnnualBonusService annualBonusService) {
        this.validationService = validationService;
        this.modifySystemTimeResponseService = modifySystemTimeResponseService;
        this.modifySourceRequestService = modifySourceRequestService;
        this.annualBonusService = annualBonusService;
    }

    @PostMapping("/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResults) {

        log.info("request: {}", request);
        request.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));

        Response response = buildSuccessResponse(request);

        try {
            validationService.isValid(bindingResults);

            if ("123".equals(request.getUid())) {
                throw new UnsupportedCodeException("uid = 123 не поддерживается");
            }
        } catch (ValidationFailedException e) {
            log.error("Validation failed: {}", e.getMessage());
            return buildErrorResponse(response, ErrorCodes.VALIDATION_EXCEPTION, ErrorMessages.VALIDATION, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            log.error("Unsupported code: {}", e.getMessage());
            return buildErrorResponse(response, ErrorCodes.UNSUPPORTED_EXCEPTION, ErrorMessages.UNSUPPORTED, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unknown exception: {}", e.getMessage());
            return buildErrorResponse(response, ErrorCodes.UNKNOWN_EXCEPTION, ErrorMessages.UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        enrichResponseWithBonus(response, request);
        modifySystemTimeResponseService.modify(response);
        modifySourceRequestService.modify(request);
        log.info("Response final: {}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private Response buildSuccessResponse(Request request) {
        return Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
    }

    private ResponseEntity<Response> buildErrorResponse(Response response,
                                                        ErrorCodes errorCode,
                                                        ErrorMessages errorMessage,
                                                        HttpStatus status) {
        response.setCode(Codes.FAILED);
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
        log.info("Response modified ({})", response);
        return new ResponseEntity<>(response, status);
    }

    private void enrichResponseWithBonus(Response response, Request request) {
        double annualBonus = annualBonusService.calculate(
                request.getPosition(),
                request.getSalary(),
                request.getBonus(),
                request.getWorkDays()
        );
        response.setAnnualBonus(annualBonus);
        log.info("Calculated annualBonus: {}", annualBonus);
    }

}