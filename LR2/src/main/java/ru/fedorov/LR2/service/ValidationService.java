package ru.fedorov.LR2.service;

import org.springframework.validation.BindingResult;

import ru.fedorov.LR2.exception.ValidationFailedException;

public interface ValidationService {

    void isValid(BindingResult bindingResult) throws ValidationFailedException;

}
