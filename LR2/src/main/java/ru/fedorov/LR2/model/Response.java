package ru.fedorov.LR2.model;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Response {

    /**
     * Уникальный идентификатор сообщения
     */
    private String uid;

    /**
     * Уникальный идентификатор операции
     */
    private String operationUid;

    /**
     * Время создания ответа
     */
    private String systemTime;

    /**
     * Рассчитанная годовая премия
     */
    private Double annualBonus;

    /**
     * Код результата обработки
     */
    private Codes code;

    /**
     * Код ошибки
     */
    private ErrorCodes errorCode;

    /**
     * Сообщение об ошибке
     */
    private ErrorMessages errorMessage;


}
