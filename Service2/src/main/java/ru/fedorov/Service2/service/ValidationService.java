package ru.fedorov.Service2.service;

import org.springframework.validation.BindingResult;

import ru.fedorov.Service2.exception.ValidationFailedException;

public interface ValidationService {

    void isValid(BindingResult bindingResult) throws ValidationFailedException;

}
