package ru.fedorov.LR2.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import lombok.extern.slf4j.Slf4j;
import ru.fedorov.LR2.exception.ValidationFailedException;

@Slf4j
@Service
public class RequestValidationService implements ValidationService {

    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> 
                log.error("Binding error: {}", error)
            );
            throw new 
                ValidationFailedException(bindingResult.getFieldError().toString());
        }
    }

}
