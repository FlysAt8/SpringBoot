package ru.fedorov.LR2.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {


    /**
     * Уникальный идентификатор сообщения
     */
    @NotBlank(message = "UID не может быть пустым")
    @Size(max = 32)
    private String uid;

    /**
     * Уникальный идентификатор операции
     */
    @NotBlank
    @Size(max = 32)
    private String operationUid;

    /**
     * Имя системы отправителя
     */
    private Systems systemName;

    /**
     * Время создания сообщения
     */
    @NotBlank
    private String systemTime;

    /**
     * Источник сообщения
     */
    private String source;

    /**
     * Должность сотрудника
     */
    private Positions position;

    /**
     * Заработная плата
     */
    private Double salary;

    /**
     * Премиальный коэффициент
     */
    private Double bonus;

    /**
     * Количество отработанных дней
     */
    private Integer workDays;

    /**
     * Идентификатор коммуникации
     */
    @NotNull
    @Min(1)
    @Max(100000)
    private Integer communicationId;

    /**
     * Идентификатор шаблона
     */
    private Integer templateId;

    /**
     * Код продукта
     */
    private Integer productCode;

    /**
     * Код СМС
     */
    private Integer smsCode;

    @Override
    public String toString() {
        return "{" +
               "uid='" + uid + "'" +
               ", operationUid='" + operationUid + "'" +
               ", systemName='" + systemName + "'" +
               ", systemTime='" + systemTime + "'" +
               ", source='" + source + "'" +
               ", position=" + position +
               ", salary=" + salary +
               ", bonus=" + bonus +
               ", workDays=" + workDays +
               ", communicationId=" + communicationId +
               ", templateId=" + templateId +
               ", productCode=" + productCode +
               ", smsCode=" + smsCode + "}";
    }

}
