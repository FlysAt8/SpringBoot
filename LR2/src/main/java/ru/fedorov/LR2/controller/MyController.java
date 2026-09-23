package ru.fedorov.LR2.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ru.fedorov.LR2.exception.UnsupportedCodeException;
import ru.fedorov.LR2.exception.ValidationFailedException;
import ru.fedorov.LR2.model.Request;
import ru.fedorov.LR2.model.Response;
import ru.fedorov.LR2.service.ValidationService;


@RestController
public class MyController {

    private final ValidationService validationService;

    public MyController(ValidationService validationService){
        this.validationService=validationService;
    }

    @PostMapping("/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
        BindingResult bindingResults) { 

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Response response = Response.builder()
                                .uid(request.getUid())
                                .operationUid(request.getOperationUid())
                                .systemTime(simpleDateFormat.format(new Date()))
                                .code("success")
                                .errorCode("")
                                .errorMessage("")
                                .build();

        try {
            validationService.isValid(bindingResults); 
            
            if ("123".equals(request.getUid())) {
                throw new UnsupportedCodeException("uid = 123 не поддерживается");
            }

        } catch (ValidationFailedException e) {
            response.setCode("failed");
            response.setErrorCode("ValidationException");
            response.setErrorMessage("Ошибка валидации");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            response.setCode("failed");
            response.setErrorCode("UnsupportedCodeException");
            response.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch(Exception ee) {
            response.setCode("failed");
            response.setErrorCode("UnknownException");
            response.setErrorMessage("Произошла непредвиденная ошибка");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
